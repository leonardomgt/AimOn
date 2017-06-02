package com.aimon.game.controller.entities;

import com.aimon.game.model.entities.PlayerModel;

// TODO: Auto-generated Javadoc
/**
 * Created by joaofurriel on 27/05/17.
 */

public class PlayerController {

    /** The player model. */
    private PlayerModel playerModel;
    
    /** The gun controller. */
    private GunController gunController;

    /**
     * Instantiates a new player controller.
     *
     * @param playerModel the player model
     */
    public PlayerController(PlayerModel playerModel) {
        this.playerModel = playerModel;
        this.gunController = new GunController(this.playerModel.getGun());
    }

    /**
     * Fire gun.
     *
     * @return true, if successful
     */
    public boolean fireGun() {

        if (this.playerModel.getGun().getNumberOfBullets() == 1) {
            if(this.playerModel.getNumberOfBullets() == 0) {
                this.playerModel.setOutOfBullets(true);
            }
        }

        return this.gunController.fire();
    }

    /**
     * Reload gun.
     *
     * @return the int
     */
    public int reloadGun() {

        int reloadedBullets = Math.min(this.playerModel.getNumberOfBullets(), this.gunController.getModel().getEmptySpaces());

        if (reloadedBullets > 0) {
            this.playerModel.withdrawBullets(reloadedBullets);
            return this.gunController.reload(reloadedBullets);
        }

        return  0;
    }

    /**
     * Gets the gun controller.
     *
     * @return the gun controller
     */
    public GunController getGunController() {
        return gunController;
    }

    /**
     * Gets the player model.
     *
     * @return the player model
     */
    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    /**
     * Good shot.
     *
     * @param good the good
     */
    public void goodShot(boolean good) {

        if (good) {
            this.playerModel.increaseDucksKilled();
            this.playerModel.increaseScore();
        }

        else
            this.playerModel.increaseMissedShots();

    }



}
