package com.aimon.game.controller.entities.behaviors;

import com.aimon.game.controller.MainController;
import com.aimon.game.controller.entities.DuckBody;

// TODO: Auto-generated Javadoc
/**
 * Created by Leo on 03/05/2017.
 */

public abstract class DuckBehavior {

    /** The duck. */
    protected DuckBody duck;

    /**
     * Instantiates a new duck behavior.
     *
     * @param duck the duck
     */
    public DuckBehavior(DuckBody duck) {
        this.duck = duck;
    }

    /**
     * Update.
     *
     * @param delta the delta
     */
    public abstract void update(float delta);

    /**
     * Verify limits.
     *
     * @return true, if successful
     */
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
            //duck.getBody().setTransform(MainController.getControllerWidth(), duck.getY(), duck.getBody().getAngle());
            duck.changeDirection();
            duck.setTransform(duck.getX() + 0.01f, duck.getY(), 0);
            return true;
        }
        else if(duck.getX() > MainController.getControllerWidth()) {
            //duck.getBody().setTransform(0, duck.getY(), duck.getBody().getAngle());
            duck.changeDirection();
            duck.setTransform(duck.getX() - 0.01f, duck.getY(), 0);

            return true;
        }

        return false;
    }
}
