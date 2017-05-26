package com.aimon.game.controller.entities.behaviors;

import com.aimon.game.controller.MainController;
import com.aimon.game.controller.entities.DuckBody;

/**
 * Created by Leo on 03/05/2017.
 */

public abstract class DuckBehavior {

    protected DuckBody duck;

    public DuckBehavior(DuckBody duck) {
        this.duck = duck;
    }

    public abstract void update(float delta);

    protected boolean verifyLimits() {

        if (duck.getY() >= MainController.getControllerHeight()- duck.getHeight()/2) {
            duck.goDown(1);
            return true;
        }
        else if(duck.getY() <= MainController.getControllerGroundHeight() + duck.getHeight() + 1) {
            duck.goUp(1);
            return true;
        }

        else if(duck.getX() < 0) {
            duck.getBody().setTransform(MainController.getControllerWidth(), duck.getY(), duck.getBody().getAngle());
            return true;
        }
        else if(duck.getX() > MainController.getControllerWidth()) {
            duck.getBody().setTransform(0, duck.getY(), duck.getBody().getAngle());
            return true;
        }

        return false;
    }
}
