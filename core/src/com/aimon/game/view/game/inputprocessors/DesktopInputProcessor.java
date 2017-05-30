package com.aimon.game.view.game.inputprocessors;

import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;


/**
 * Created by Leo on 23/05/2017.
 */

public class DesktopInputProcessor extends GameInputProcessor {


    public DesktopInputProcessor(GameScreen gameScreen) {
        super(gameScreen);
        System.out.println("DESKTOP");

    }

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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (button){
            case Input.Buttons.LEFT:
                shot();

                return true;

            case Input.Buttons.RIGHT:
                this.gameScreen.changeZoom();
                return true;

            default:
        }

        return false;
    }

    public void updateAim() {

        gameScreen.getAimPosition().set(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameScreen.getCamera().unproject(gameScreen.getAimPosition());

        gameScreen.getAimPosition().set(gameScreen.getAimPosition().x * gameScreen.PIXEL_TO_METER, gameScreen.getAimPosition().y*gameScreen.PIXEL_TO_METER, 0);
        gameScreen.getController().updateAimLocation(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y);

    }



}
