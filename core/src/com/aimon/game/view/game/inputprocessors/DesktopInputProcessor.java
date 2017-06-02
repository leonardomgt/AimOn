package com.aimon.game.view.game.inputprocessors;

import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;


// TODO: Auto-generated Javadoc
/**
 * Created by Leo on 23/05/2017.
 */

public class DesktopInputProcessor extends GameInputProcessor {


    /**
     * Instantiates a new desktop input processor.
     *
     * @param gameScreen the game screen
     */
    public DesktopInputProcessor(GameScreen gameScreen) {
        super(gameScreen);
    }

    /**
     * Key down.
     *
     * @param keycode the keycode
     * @return true, if successful
     */
    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.R:
                reloadGun();
                break;
            case Input.Keys.B:
                this.gameScreen.game.setMenuScreen();
                break;
            default:
        }

        return super.keyDown(keycode);
    }

    /**
     * Touch down.
     *
     * @param screenX the screen X
     * @param screenY the screen Y
     * @param pointer the pointer
     * @param button the button
     * @return true, if successful
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (button){
            case Input.Buttons.LEFT:
                shot();

                return true;

            case Input.Buttons.RIGHT:
                changeZoom();
                return true;

            default:
        }

        return false;
    }

    /**
     * Scrolled.
     *
     * @param amount the amount
     * @return true, if successful
     */
    @Override
    public boolean scrolled(int amount){

        if(gameScreen.getCamera().zoom >= 1 && amount == 1) return true;
        if(gameScreen.getCamera().zoom <= 0.5f && amount == -1) return true;

        changeZoomScroll(gameScreen.getCamera().zoom + amount*0.04f);
        return true;
    }

    /* (non-Javadoc)
     * @see view.game.inputprocessors.GameInputProcessor#updateAim()
     */
    public void updateAim() {

      /*  if(Gdx.input.getX() > gameScreen.getController().getControllerWidth()){
            gameScreen.getAimPosition().set(gameScreen.getController().getControllerWidth(), gameScreen.getAimPosition().y, 0);
        }
        if(gameScreen.getAimPosition().x < 0){
            gameScreen.getAimPosition().set(0, gameScreen.getAimPosition().y, 0);
        }
        if(gameScreen.getAimPosition().y > gameScreen.getController().getControllerHeight()){
            gameScreen.getAimPosition().set(gameScreen.getAimPosition().x, gameScreen.getController().getControllerHeight(), 0);
        }
        if(gameScreen.getAimPosition().y < 0){
            gameScreen.getAimPosition().set(gameScreen.getAimPosition().x, 0, 0);
        }*/

        projectAimToCamera(gameScreen.getAimPosition());

        // Buttons listener
        Gdx.input.setCursorPosition((int)gameScreen.getAimPosition().x, (int)gameScreen.getAimPosition().y);


        gameScreen.getCamera().unproject( gameScreen.getAimPosition());
        gameScreen.getAimPosition().set(gameScreen.getAimPosition().x * gameScreen.PIXEL_TO_METER, gameScreen.getAimPosition().y*gameScreen.PIXEL_TO_METER, 0);

        gameScreen.getController().updateAimLocation(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y);

    }




}
