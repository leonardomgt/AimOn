package com.aimon.game.controller.entities;

import com.aimon.game.model.entities.GunModel;

// TODO: Auto-generated Javadoc
/**
 * Created by joaofurriel on 25/05/17.
 */

public class GunController {

    /** The model. */
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
     * Fire.
     *
     * @return true, if successful
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
     * Reload.
     *
     * @param numberOfBullets the number of bullets
     * @return the int
     */
    public int reload(int numberOfBullets) {

        if (model.getState() == GunModel.GunState.IDLE /*&& this.model.getEmptySpaces() > 0*/) {

            this.model.setReloadedBullets(Math.min(this.model.getEmptySpaces(), numberOfBullets));

            this.model.setNumberOfBullets(this.model.getNumberOfBullets() + numberOfBullets);
            this.model.setLastReloadMoment(this.model.getGunLifeTime());
            this.model.setState(GunModel.GunState.RELOADING);
            return this.model.getReloadedBullets();
        }

        return 0;

    }

    /**
     * Update status.
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
     * Gets the model.
     *
     * @return the model
     */
    public GunModel getModel() {
        return model;
    }
}
