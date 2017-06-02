package com.aimon.game.controller.entities;

import com.aimon.game.model.entities.GunModel;

/**
 * Gun Controller. Operates on the gun logic and updates the gun model, upon the inputs of the player controller
 */

public class GunController {

    /** The gunModel object that holds the status of the gun  */
    private GunModel model;

    /**
     * Instantiates a new gun controller.
     *
     * @param model the model
     */
    public GunController(GunModel model) {
        this.model = model;
    }

    /**
     * Fire. This method will fire the gun in certain conditions. The gun is in the idle state, which means that is ready to be fired and has bullets.
     * If the gun is fired, the moment of the shot is set to the current moment on the gun lifetime and the status is set to FIRING
     *
     * @return true, if gun was fired, false otherwise
     */
    public boolean fire() {

        if (model.getState() == GunModel.GunState.IDLE && model.getNumberOfBullets() > 0) {

            this.model.setLastShotMoment(this.model.getGunLifeTime());
            this.model.setNumberOfBullets(this.model.getNumberOfBullets()-1);
            this.model.setState(GunModel.GunState.FIRING);
            return true;

        }
        return false;
    }

    /**
     * Reload. This method will reload the gun. It receives the bullets that the player is trying to reload and if the gun is on IDLE status and the number
     * of bullets the player controller is greater than 0 will reload the gun
     *
     * It will reload the minimum between the empty spaces of the gun and the number of bullets the player wants to reload
     * It will mark the reloaded moment the current instant in the gun lifetime
     *
     * @param numberOfBullets the number of bullets to reload
     * @return the int
     */
    public int reload(int numberOfBullets) {

        if (model.getState() == GunModel.GunState.IDLE && numberOfBullets > 0) {

            this.model.setReloadedBullets(numberOfBullets);
            this.model.setNumberOfBullets(this.model.getNumberOfBullets() + numberOfBullets);
            this.model.setLastReloadMoment(this.model.getGunLifeTime());
            this.model.setState(GunModel.GunState.RELOADING);
            return this.model.getReloadedBullets();
        }

        return 0;

    }

    /**
     * Update status. Update the gun status. If it's FIRING, it will "wait" until the fire delay passes, if it is RELOADING, it will wait until
     * the RELOADING time passes. In both cases sets the gun back to "IDLE" state
     *
     * @param time the time
     */
    public void updateStatus(float time) {

        this.model.setGunLifeTime(this.model.getGunLifeTime() + time);

        switch (this.model.getState()) {
            case FIRING:

                float elapsedTimeShot = this.model.getGunLifeTime() - this.model.getLastShotMoment();
                if (elapsedTimeShot >= this.model.getShotDelay()){
                    this.model.setState(GunModel.GunState.IDLE);
                }
                break;
            case RELOADING:
                float elapsedTimeReload = this.model.getGunLifeTime() - this.model.getLastReloadMoment();
                if (elapsedTimeReload >= this.model.getReloadedBullets() * this.model.getReloadBulletDelay() + this.model.getSlideDelay()){
                    this.model.setState(GunModel.GunState.IDLE);
                }
                break;
        }

    }

    /**
     * Gets the gun model.
     *
     * @return the gun model
     */
    public GunModel getModel() {
        return model;
    }
}
