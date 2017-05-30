package com.aimon.game.model.entities;

import com.aimon.game.view.game.GameScreen;

/**
 * Created by joaofurriel on 27/05/17.
 */

public class PlayerModel  extends EntityModel {

    private String name;
    private int score = 0;
    private GunModel gun;
    private int numberOfBullets;
    private int killedDucks;
    private int missedShots;

    public PlayerModel(String name, int numberOfBullets) {

        super(GameScreen.VIEWPORT_WIDTH-0.5f,GameScreen.VIEWPORT_HEIGHT-0.5f,0f);
        this.name = name;
        this.numberOfBullets = numberOfBullets;
        this.gun = new GunModel(6,.5f,.5f);
        this.killedDucks = 0;
        this.missedShots = 0;

    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public GunModel getGun() {
        return gun;
    }

    public int getNumberOfBullets() {
        return numberOfBullets;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNumberOfBullets(int numberOfBullets) {
        this.numberOfBullets = numberOfBullets;
    }

    public void withdrawBullets(int numberOfBullets) {
        this.numberOfBullets -= numberOfBullets;
    }

    public void setGun(GunModel gun) {
        this.gun = gun;
    }

    public void increaseDucksKilled() {
        this.killedDucks++;
    }

    public void increaseMissedShots() {
        this.missedShots++;
    }

    public int getKilledDucks() {
        return killedDucks;
    }

    public int getMissedShots() {
        return missedShots;
    }
}
