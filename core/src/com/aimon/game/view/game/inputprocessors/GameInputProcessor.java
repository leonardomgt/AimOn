package com.aimon.game.view.game.inputprocessors;

import com.aimon.game.controller.MainController;
import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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
 * Handles all the user inputs.
 */

public abstract class GameInputProcessor extends InputAdapter{


    /** The game screen. */
    GameScreen gameScreen;

    /** The shot sound effect. */
    protected Sound shotSoundEffect;
    
    /** The reloading bullet sound effect. */
    protected Sound reloadBulletSoundEffect;
    
    /** The slide after reload sound effect. */
    protected Sound slideGunSoundEffect;
    
    /** The trigger pulled with empty gun sound effect. */
    protected Sound emptyGunSoundEffect;

    /** Flag indicating if game sound is On/Off. */
    private boolean soundOn = true;

    /** The path to SHOT sound asset. */
    private static final String SHOT = "shotgun.wav";
    
    /** The path to RELOADING BULLET sound asset. */
    private static final String RELOAD_BULLET = "load_bullet.wav";

    /** The path to SLIDE sound asset. */
    private static final String SLIDE_GUN = "slide_gun.wav";

    /** The path to EMPTY GUN sound asset. */
    private static final String EMPTY_GUN = "empty_gun.wav";

    /** The current aim position. */
    private Vector3 currentAimPosition;

    /**
     * Instantiates a new game input processor.
     *
     * @param gameScreen the game screen
     */
    protected GameInputProcessor(GameScreen gameScreen) {

        this.gameScreen = gameScreen;

        shotSoundEffect = null;
        reloadBulletSoundEffect = null;
        slideGunSoundEffect = null;
        emptyGunSoundEffect = null;

        soundOn = gameScreen.game.isSoundOn();

        currentAimPosition = gameScreen.getInitialAimPosition();
    }


    /**
     * Update aim position.
     */
    public abstract void updateAim();

    /**
     * Load assets needed for Game Input Processor elements.
     */
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

    /**
     * Change camera zoom.
     */
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

    /**
     * Change camera zoom using scroll.
     *
     * @param zoom the zoom
     */
    public void changeZoomScroll(float zoom) {



        float PIXEL_TO_METER = gameScreen.PIXEL_TO_METER;
        OrthographicCamera camera = gameScreen.getCamera();
        Vector3 aimPosition = gameScreen.getAimPosition();

        camera.position.set(aimPosition.x /PIXEL_TO_METER, aimPosition.y /PIXEL_TO_METER,0);

        float oldZoom = camera.zoom;
        camera.zoom = zoom;

        Vector2 aimPositionScreen = new Vector2(-(camera.zoom/oldZoom)*(aimPosition.x - currentAimPosition.x)/PIXEL_TO_METER, -(camera.zoom/oldZoom)*(aimPosition.y - currentAimPosition.y)/PIXEL_TO_METER);

        camera.translate(aimPositionScreen.x, aimPositionScreen.y);

        // show only background area

        float maxX = MainController.getControllerWidth()/PIXEL_TO_METER/2f + Gdx.graphics.getWidth()/2f;
        float maxY = Gdx.graphics.getHeight();
        float minX = MainController.getControllerWidth()/PIXEL_TO_METER/2f - Gdx.graphics.getWidth()/2f;
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

        currentAimPosition.set(camera.position.x*PIXEL_TO_METER, camera.position.y*PIXEL_TO_METER, 0);

        camera.update();


    }

    /**
     * Pull the trigger.
     */
    public void shot() {

        if (gameScreen.getController().fireGun(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y)){
            if(soundOn) shotSoundEffect.play();
            gameScreen.getGameStatusView().spendBullet();
        }

        else if(gameScreen.getModel().getPlayerModel().getGun().getNumberOfBullets() == 0)
            if(soundOn) emptyGunSoundEffect.play();

    }

    /**
     * Reload gun.
     *
     * Creates a new Thread to play the sound effects of reloading the gun
     */
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
                        gameScreen.getGameStatusView().setPlayerBullets(--oldBulletsBox);
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

    /**
     * Initialize UI elements:
     *
     * Button Home - Back to Main-Menu.
     *
     */
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


    /**
     * Sets the sound flag.
     *
     * @param sound true if sound is ON, else false
     */
    public void setSound(boolean sound) {
        this.soundOn = sound;
    }
}
