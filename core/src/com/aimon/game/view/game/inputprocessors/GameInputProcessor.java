package com.aimon.game.view.game.inputprocessors;

import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;

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

    public void shot() {

        if (gameScreen.getController().fireGun(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y)){
            shotSoundEffect.play();
            gameScreen.getGameStatusView().spendBullet();
        }

        else if(gameScreen.getModel().getPlayerModel().getGun().getNumberOfBullets() == 0)
            emptyGunSoundEffect.play();



        float acx = Gdx.input.getAccelerometerX();
        float acy = Gdx.input.getAccelerometerY();
        float acz = Gdx.input.getAccelerometerZ();

        System.out.println(acx + acy + acz);
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
