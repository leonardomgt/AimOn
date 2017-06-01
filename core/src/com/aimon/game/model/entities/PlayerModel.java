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

    private boolean outOfBullets = false;

    public PlayerModel(String name, int numberOfBullets, float x, float y) {

        super(x,y,0f);
        this.name = name;
        this.numberOfBullets = numberOfBullets;
        this.killedDucks = 0;
        this.missedShots = 0;
        this.reset();

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

    public void setNumberOfBullets(int numberOfBullets) {
        this.numberOfBullets = numberOfBullets;
    }

    public void withdrawBullets(int numberOfBullets) {
        this.numberOfBullets -= numberOfBullets;
    }

    public void reset() {
        this.gun = new GunModel();
        this.killedDucks = 0;
        this.missedShots = 0;
        this.outOfBullets = false;
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

    public boolean isOutOfBullets() {
        return outOfBullets;
    }

    public void setOutOfBullets(boolean outOfBullets) {
        this.outOfBullets = outOfBullets;
    }

    public void increaseScore(){
        this.score++;
    }


}
