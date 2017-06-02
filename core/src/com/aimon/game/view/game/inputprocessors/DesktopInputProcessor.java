package com.aimon.game.view.game.inputprocessors;

import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

/**
 *  InputProcessor for Desktop case:
 *
 *  This class is only called when the Running Platform is Desktop.
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
     * Handle inputs:
     *
     *   Key R - Reload the gun.
     *   Key B - Back to main-menu.
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
     * Handle mouse click inputs:
     *
     *     Left Button - Shot.
     *     Right Button - Zoom In/Out
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
     * Handle scroll input: Change zoom.
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

        projectAimToCamera(gameScreen.getAimPosition());

        // Buttons listener
        Gdx.input.setCursorPosition((int)gameScreen.getAimPosition().x, (int)gameScreen.getAimPosition().y);


        gameScreen.getCamera().unproject( gameScreen.getAimPosition());
        gameScreen.getAimPosition().set(gameScreen.getAimPosition().x * gameScreen.PIXEL_TO_METER, gameScreen.getAimPosition().y*gameScreen.PIXEL_TO_METER, 0);

        gameScreen.getController().updateAimLocation(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y);

    }

    /**
     * Project aim to camera:
     *
     * Avoid the aim to get out of the screen.
     *
     * @param aimPosition the aim position
     */
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




}
