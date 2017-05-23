package com.aimon.game.view.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Leo on 23/05/2017.
 */

public class GameInputProcessor extends InputAdapter {

    GameScreen gameScreen;

    public GameInputProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        switch (button){
            case Input.Buttons.LEFT:
                this.gameScreen.getController().shotFired(this.gameScreen.getAimPosition().x, this.gameScreen.getAimPosition().y);
                return true;

            case Input.Buttons.RIGHT:
                this.gameScreen.changeZoom();
                return true;

            default:
        }

        return false;
    }
}
