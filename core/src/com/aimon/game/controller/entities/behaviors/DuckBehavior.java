package com.aimon.game.controller.entities.behaviors;

import com.aimon.game.controller.MainController;
import com.aimon.game.controller.entities.DuckBody;

/**
 * Abstract class used to create duck strategies that ducks will follow to move around
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
     * @param delta time since the last duck update
     */
    public abstract void update(float delta);

    /**
     * Verify limits. Check if the ducks is outside the limits and put them back in
     *
     * @return true, if they are out of limits, false otherwise
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
            duck.changeDirection();
            duck.setTransform(duck.getX() + 0.01f, duck.getY(), 0);
            return true;
        }
        else if(duck.getX() > MainController.getControllerWidth()) {
            duck.changeDirection();
            duck.setTransform(duck.getX() - 0.01f, duck.getY(), 0);

            return true;
        }

        return false;
    }
}
