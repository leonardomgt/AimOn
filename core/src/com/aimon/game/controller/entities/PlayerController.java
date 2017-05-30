package com.aimon.game.controller.entities;

import com.aimon.game.model.entities.PlayerModel;

/**
 * Created by joaofurriel on 27/05/17.
 */

public class PlayerController {

    private PlayerModel playerModel;
    private GunController gunController;

    public PlayerController(PlayerModel playerModel) {
        this.playerModel = playerModel;
        this.gunController = new GunController(this.playerModel.getGun());
    }

    public boolean fireGun() {
        return this.gunController.fire();
    }

    public int reloadGun() {

        int reloadedBullets = Math.min(this.playerModel.getNumberOfBullets(), this.gunController.getModel().getEmptySpaces());

        if (reloadedBullets > 0) {
            this.playerModel.withdrawBullets(reloadedBullets);
            this.gunController.reload(reloadedBullets);
            return reloadedBullets;
        }

        return  0;
    }

    public GunController getGunController() {
        return gunController;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public void goodShot(boolean good) {

        if (good)
            this.playerModel.increaseDucksKilled();

        else
            this.playerModel.increaseMissedShots();

    }

}
