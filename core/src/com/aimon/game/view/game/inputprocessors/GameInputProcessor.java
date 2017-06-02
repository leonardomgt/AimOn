package com.aimon.game.view.game.inputprocessors;

import com.aimon.game.controller.MainController;
import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Leo on 30/05/2017.
 */

public abstract class GameInputProcessor extends InputAdapter{


    GameScreen gameScreen;

    protected Sound shotSoundEffect;
    protected Sound reloadBulletSoundEffect;
    protected Sound slideGunSoundEffect;
    protected Sound emptyGunSoundEffect;

    private boolean soundOn = true;

    private static final String SHOT = "shotgun.wav";
    private static final String RELOAD_BULLET = "load_bullet.wav";
    private static final String SLIDE_GUN = "slide_gun.wav";
    private static final String EMPTY_GUN = "empty_gun.wav";

    private Vector3 currentAimPosition;

    protected GameInputProcessor(GameScreen gameScreen) {

        this.gameScreen = gameScreen;

        shotSoundEffect = null;
        reloadBulletSoundEffect = null;
        slideGunSoundEffect = null;
        emptyGunSoundEffect = null;

        soundOn = gameScreen.game.isSoundOn();

        currentAimPosition = gameScreen.getInitialAimPosition();
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

    public void changeZoomScroll(float zoom) {

        float PIXEL_TO_METER = gameScreen.PIXEL_TO_METER;
        OrthographicCamera camera = gameScreen.getCamera();
        Vector3 aimPosition = gameScreen.getAimPosition();

        System.out.println("camera.position: " + camera.position);
        camera.position.set(aimPosition.x /PIXEL_TO_METER, aimPosition.y /PIXEL_TO_METER,0);
        System.out.println("camera.position2: " + camera.position);

        float oldZoom = camera.zoom;
        camera.zoom = zoom;

        Vector2 aimPositionScreen = new Vector2(-(camera.zoom/oldZoom)*(aimPosition.x - currentAimPosition.x)/PIXEL_TO_METER, -(camera.zoom/oldZoom)*(aimPosition.y - currentAimPosition.y)/PIXEL_TO_METER);

        camera.translate(aimPositionScreen.x, aimPositionScreen.y);

        System.out.println("camera.position3: " + camera.position);

        currentAimPosition.set(camera.position.x*PIXEL_TO_METER, camera.position.y*PIXEL_TO_METER, 0);
        // show only background area

        float maxX = MainController.getControllerWidth()/PIXEL_TO_METER/2f + camera.viewportWidth/2f;
        float maxY = camera.viewportHeight;
        float minX = MainController.getControllerWidth()/PIXEL_TO_METER/2f - camera.viewportWidth/2f;
        float minY = 0;

        if((camera.position.x + camera.zoom*Gdx.graphics.getWidth()/2f) > maxX){
            camera.translate(maxX - (camera.position.x + camera.zoom*Gdx.graphics.getWidth()/2f),0);
        }
        if((camera.position.x - camera.zoom*Gdx.graphics.getWidth()/2f) < minX){
            camera.translate(minX - (camera.position.x - camera.zoom*Gdx.graphics.getWidth()/2f),0);
        }
        if((camera.position.y + camera.zoom*Gdx.graphics.getHeight()/2f) > maxY){
            camera.translate(0, maxY - (camera.position.y + camera.zoom*Gdx.graphics.getHeight()/2f));
        }
        if((camera.position.y - camera.zoom*Gdx.graphics.getHeight()/2f) < minY){
            camera.translate(0,minY - (camera.position.y - camera.zoom*Gdx.graphics.getHeight()/2f));
        }

        camera.update();


    }

    public void shot() {

        if (gameScreen.getController().fireGun(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y)){
            if(soundOn) shotSoundEffect.play();
            gameScreen.getGameStatusView().spendBullet();
        }

        else if(gameScreen.getModel().getPlayerModel().getGun().getNumberOfBullets() == 0)
            if(soundOn) emptyGunSoundEffect.play();

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
                        if(soundOn) reloadBulletSoundEffect.play();
                        gameScreen.getGameStatusView().setPlayerBulletsString(--oldBulletsBox);
                        gameScreen.getGameStatusView().setGunBullets(++oldBulletsGun);
                        Thread.sleep(this.duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if(soundOn) slideGunSoundEffect.play();
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

    public void initializeUIElements() {

        float deltaToLimits = 16;

        // Home Button
        ImageButton buttonHome;

        buttonHome = new ImageButton(gameScreen.game.getSkin());
        buttonHome.setSize(buttonHome.getHeight()/1.2f,buttonHome.getHeight()/1.2f);

        ImageButton.ImageButtonStyle homeStyle = new ImageButton.ImageButtonStyle(buttonHome.getStyle());
        buttonHome.setStyle(homeStyle);

        buttonHome.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("home.png"))));
        buttonHome.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("home.png"))));
        buttonHome.getStyle().up = buttonHome.getSkin().getDrawable("button-small");
        buttonHome.getStyle().down = buttonHome.getSkin().getDrawable("button-small-down");
        buttonHome.setPosition(deltaToLimits, Gdx.graphics.getHeight()-buttonHome.getHeight() - deltaToLimits);
        buttonHome.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                gameScreen.game.setMenuScreen();
            }
        });
        gameScreen.getGameStage().addActor(buttonHome);

    }

    protected void projectAimToCamera(Vector3 aimPosition) {

        gameScreen.getAimPosition().set(gameScreen.getAimPosition().x / gameScreen.PIXEL_TO_METER, gameScreen.getAimPosition().y/gameScreen.PIXEL_TO_METER, 0);
        gameScreen.getCamera().project( gameScreen.getAimPosition());

        gameScreen.getAimPosition().set(gameScreen.getAimPosition().x, Gdx.graphics.getHeight() - gameScreen.getAimPosition().y -1, 0);


        if(!(aimPosition.x > Gdx.graphics.getWidth() && Gdx.input.getDeltaX() > 0)
        && !(aimPosition.x < 0 && Gdx.input.getDeltaX() < 0)){

            aimPosition.add(Gdx.input.getDeltaX(),0, 0);
        }

        if(!(aimPosition.y > Gdx.graphics.getHeight() && Gdx.input.getDeltaY() > 0)
        && !(aimPosition.y < 0 && Gdx.input.getDeltaY() < 0)){

            aimPosition.add(0,Gdx.input.getDeltaY(), 0);
        }

    }


    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
    }
}
