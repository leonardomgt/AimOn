package com.aimon.game.controller.entities;

import com.aimon.game.model.entities.PlayerModel;

/**
 * Player Controller. Operates on the player model, updating the player and his gun status upon the main controller inputs
 */

public class PlayerController {

    /** The player model object used to store player data. */
    private PlayerModel playerModel;
    
    /** The gun controller object used to store player gun data. */
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
     * Fire gun. This method will try to fire player gun. If it is possible will check if this was the last bullet. If so, will mark the player has
     * out of bullets
     *
     * @return true, if shot was fired, false otherwise
     */
    public boolean fireGun() {

        if (this.gunController.fire()) {
            if (this.playerModel.getGun().getNumberOfBullets() == 0 && this.playerModel.getNumberOfBullets() == 0) {
                this.playerModel.setOutOfBullets(true);
            }
            return true;

        }
        return false;

    }

    /**
     * Reload gun. This method will try to reload the gun. First, it calculates the minimum between the free spaces on the gun round
     * and the bullets the player owns and will pass this number to the reload method of the gun.
     *
     * It also withdraws and returns the bullets reloaded by the gun
     *
     * @return the number of bullets reloaded by the gun
     */
    public int reloadGun() {

        int bulletsToReload = Math.min(this.playerModel.getNumberOfBullets(), this.gunController.getModel().getEmptySpaces());

        return this.playerModel.withdrawBullets(this.gunController.reload(bulletsToReload));

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
     * Update Score. This method updates the player killed ducks and score or increases the missed shots depending on the goodness of the shot
     *
     * @param numberOfKilledDucks number of Killed ducks with a gun shot
     */
    public void updateScore(int numberOfKilledDucks) {

        if (numberOfKilledDucks>0) {
            this.playerModel.increaseDucksKilled(numberOfKilledDucks);
            this.playerModel.increaseScore(numberOfKilledDucks);
        }

        else
            this.playerModel.increaseMissedShots();

    }

}
