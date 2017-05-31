package com.aimon.game.view.game.inputprocessors;

import com.aimon.game.controller.MainController;
import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Leo on 30/05/2017.
 */

public abstract class GameInputProcessor extends InputAdapter{


    GameScreen gameScreen;

    protected Sound shotSoundEffect;
    protected Sound reloadBulletSoundEffect;
    protected Sound slideGunSoundEffect;
    protected Sound emptyGunSoundEffect;

    private static final String SHOT = "shotgun.wav";
    private static final String RELOAD_BULLET = "load_bullet.wav";
    private static final String SLIDE_GUN = "slide_gun.wav";
    private static final String EMPTY_GUN = "empty_gun.wav";

    protected GameInputProcessor(GameScreen gameScreen) {

        this.gameScreen = gameScreen;

        shotSoundEffect = null;
        reloadBulletSoundEffect = null;
        slideGunSoundEffect = null;
        emptyGunSoundEffect = null;
    }


    public abstract void updateAim();

    public void loadAssets() {

        this.gameScreen.game.getAssetManager().load(SHOT, Sound.class);
        this.gameScreen.game.getAssetManager().load(RELOAD_BULLET, Sound.class);
        this.gameScreen.game.getAssetManager().load(SLIDE_GUN, Sound.class);
        this.gameScreen.game.getAssetManager().load(EMPTY_GUN, Sound.class);

        this.gameScreen.game.getAssetManager().finishLoading();

        this.shotSoundEffect = this.gameScreen.game.getAssetManager().get(SHOT);
        this.reloadBulletSoundEffect = this.gameScreen.game.getAssetManager().get(RELOAD_BULLET);
        this.slideGunSoundEffect = this.gameScreen.game.getAssetManager().get(SLIDE_GUN);
        this.emptyGunSoundEffect = this.gameScreen.game.getAssetManager().get(EMPTY_GUN);
    }

    public void changeZoom() {

        float PIXEL_TO_METER = gameScreen.PIXEL_TO_METER;
        OrthographicCamera camera = gameScreen.getCamera();
        Vector3 aimPosition = gameScreen.getAimPosition();

        if(camera.zoom == 1) {

            camera.position.set(aimPosition.x /PIXEL_TO_METER, aimPosition.y /PIXEL_TO_METER,0);
            camera.zoom = 0.5f;

            Vector2 aimPositionScreen = new Vector2(-camera.zoom*(Gdx.input.getX() - Gdx.graphics.getWidth()/2f), -camera.zoom*(Gdx.graphics.getHeight()/2f - Gdx.input.getY()));

            camera.translate(aimPositionScreen.x, aimPositionScreen.y);

        }

        else {
            camera.zoom = 1;
            camera.position.set(MainController.getControllerWidth()  / PIXEL_TO_METER / 2f, camera.viewportHeight / 2f, 0);


        }

        camera.update();


    }

    public void changeZoomScroll(int amount) {

        float PIXEL_TO_METER = gameScreen.PIXEL_TO_METER;
        OrthographicCamera camera = gameScreen.getCamera();
        Vector3 aimPosition = gameScreen.getAimPosition();
        float zoom = camera.zoom;
        if(camera.zoom >= 1 && amount == 1) return;
        if(camera.zoom <= 0.1f && amount == -1) return;

        camera.position.set(aimPosition.x /PIXEL_TO_METER, aimPosition.y /PIXEL_TO_METER,0);

        zoom += amount*0.02f;
        camera.zoom = zoom;

        Vector2 aimPositionScreen = new Vector2(-camera.zoom*(Gdx.input.getX() - Gdx.graphics.getWidth()/2f), -camera.zoom*(Gdx.graphics.getHeight()/2f - Gdx.input.getY()));

        camera.translate(aimPositionScreen.x, aimPositionScreen.y);

        // show only background area

        if((camera.position.x + camera.zoom*Gdx.graphics.getWidth()/2f) > MainController.getControllerWidth()/PIXEL_TO_METER){
            camera.translate(MainController.getControllerWidth()/PIXEL_TO_METER - (camera.position.x + camera.zoom*Gdx.graphics.getWidth()/2f),0);
        }
        if((camera.position.x - camera.zoom*Gdx.graphics.getWidth()/2f) < 0){
            camera.translate( - (camera.position.x - camera.zoom*Gdx.graphics.getWidth()/2f),0);
        }
        if((camera.position.y + camera.zoom*Gdx.graphics.getHeight()/2f) > MainController.getControllerHeight()/PIXEL_TO_METER){
            camera.translate(0, MainController.getControllerHeight()/PIXEL_TO_METER - (camera.position.y + camera.zoom*Gdx.graphics.getHeight()/2f));
        }
        if((camera.position.y - camera.zoom*Gdx.graphics.getHeight()/2f) < 0){
            camera.translate(0, - (camera.position.y - camera.zoom*Gdx.graphics.getHeight()/2f));
        }

        camera.update();


    }

    public void shot() {

        if (gameScreen.getController().fireGun(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y)){
            shotSoundEffect.play();
            gameScreen.getGameStatusView().spendBullet();
        }

        else if(gameScreen.getModel().getPlayerModel().getGun().getNumberOfBullets() == 0)
            emptyGunSoundEffect.play();

    }

    public void reloadGun() {

        class PlaySoundInThread extends Thread{

            private int times;
            private long duration;
            private int oldBulletsBox;
            private int oldBulletsGun;


            public PlaySoundInThread(int times, long duration, int oldBulletsBox, int oldBulletsGun) {

                this.oldBulletsBox = oldBulletsBox;
                this.times = times;
                this.duration = duration;
                this.oldBulletsGun = oldBulletsGun;

            }

            @Override
            public void run() {
                for (int i = 0; i < times; i++) {

                    try {
                        reloadBulletSoundEffect.play();
                        gameScreen.getGameStatusView().setPlayerBulletsString(--oldBulletsBox);
                        gameScreen.getGameStatusView().setGunBullets(++oldBulletsGun);
                        Thread.sleep(this.duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                slideGunSoundEffect.play();
            }
        }

        int oldBulletsBox = gameScreen.getModel().getPlayerModel().getNumberOfBullets();
        int oldBulletsGun = gameScreen.getModel().getPlayerModel().getGun().getNumberOfBullets();
        int numberOfBulletsToReload = gameScreen.getController().reloadGun();

        if(numberOfBulletsToReload > 0) {

            long delay = (long)(gameScreen.getModel().getPlayerModel().getGun().getReloadBulletDelay() * 1000) ;

            Thread reloadSoundThread = new Thread(new PlaySoundInThread(numberOfBulletsToReload, delay, oldBulletsBox, oldBulletsGun));
            reloadSoundThread.start();

        }

    }
}
