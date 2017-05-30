package com.aimon.game.view.game.inputprocessors;

import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Leo on 30/05/2017.
 */

public class AndroidInputProcessor extends GameInputProcessor {

    private Vector3 touchedPosition;
    private Vector3 currentAimPos;


    public AndroidInputProcessor(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void updateAim() {


    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        //if (!dragging) return false;
        //gameScreen.getAimPosition().set(screenX*1f, screenY*1f,0);
       //

       /* Vector3 newAimPos = new Vector3(Gdx.input.getX() - touchedPosition.x, Gdx.input.getY() - touchedPosition.y, 0);
        gameScreen.getCamera().unproject(newAimPos);

        newAimPos.set(newAimPos.x*gameScreen.PIXEL_TO_METER, newAimPos.y*gameScreen.PIXEL_TO_METER, 0);

        gameScreen.getAimPosition().set(gameScreen.getAimPosition().x + newAimPos.x, gameScreen.getAimPosition().y + newAimPos.y, 0);
        gameScreen.getController().updateAimLocation(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y);*/


        gameScreen.getAimPosition().set(currentAimPos.x + (Gdx.input.getX() - touchedPosition.x), Gdx.graphics.getHeight()- currentAimPos.y + Gdx.input.getY() - touchedPosition.y, 0);
        gameScreen.getCamera().unproject(gameScreen.getAimPosition());

        gameScreen.getAimPosition().set(gameScreen.getAimPosition().x * gameScreen.PIXEL_TO_METER, gameScreen.getAimPosition().y*gameScreen.PIXEL_TO_METER, 0);
        gameScreen.getController().updateAimLocation(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){

        touchedPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        currentAimPos = new Vector3(gameScreen.getAimPosition().x,gameScreen.getAimPosition().y,0) ;
        currentAimPos.set(currentAimPos.x / gameScreen.PIXEL_TO_METER, currentAimPos.y/gameScreen.PIXEL_TO_METER, 0);
        gameScreen.getCamera().project(currentAimPos);

        return true;
    }


}
