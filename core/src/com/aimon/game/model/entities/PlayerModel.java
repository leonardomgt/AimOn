package com.aimon.game.model.entities;

import com.aimon.game.view.game.GameScreen;

// TODO: Auto-generated Javadoc
/**
 * Created by joaofurriel on 27/05/17.
 */

public class PlayerModel  extends EntityModel {

    /** The name. */
    private String name;
    
    /** The score. */
    private int score = 0;
    
    /** The gun. */
    private GunModel gun;
    
    /** The number of bullets. */
    private int numberOfBullets;
    
    /** The killed ducks. */
    private int killedDucks;
    
    /** The missed shots. */
    private int missedShots;

    /** The out of bullets. */
    private boolean outOfBullets = false;

    /**
     * Instantiates a new player model.
     *
     * @param name the name
     * @param numberOfBullets the number of bullets
     * @param x the x
     * @param y the y
     */
    public PlayerModel(String name, int numberOfBullets, float x, float y) {

        super(x,y,0f);
        this.name = name;
        this.numberOfBullets = numberOfBullets;
        this.killedDucks = 0;
        this.missedShots = 0;
        this.reset();

    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the gun.
     *
     * @return the gun
     */
    public GunModel getGun() {
        return gun;
    }

    /**
     * Gets the number of bullets.
     *
     * @return the number of bullets
     */
    public int getNumberOfBullets() {
        return numberOfBullets;
    }

    /**
     * Sets the number of bullets.
     *
     * @param numberOfBullets the new number of bullets
     */
    public void setNumberOfBullets(int numberOfBullets) {
        this.numberOfBullets = numberOfBullets;
    }

    /**
     * Withdraw bullets.
     *
     * @param numberOfBullets the number of bullets
     */
    public void withdrawBullets(int numberOfBullets) {
        this.numberOfBullets -= numberOfBullets;
    }

    /**
     * Reset.
     */
    public void reset() {
        this.gun = new GunModel();
        this.killedDucks = 0;
        this.missedShots = 0;
        this.outOfBullets = false;
    }

    /**
     * Increase ducks killed.
     */
    public void increaseDucksKilled() {
        this.killedDucks++;
    }

    /**
     * Increase missed shots.
     */
    public void increaseMissedShots() {
        this.missedShots++;
    }

    /**
     * Gets the killed ducks.
     *
     * @return the killed ducks
     */
    public int getKilledDucks() {
        return killedDucks;
    }

    /**
     * Gets the missed shots.
     *
     * @return the missed shots
     */
    public int getMissedShots() {
        return missedShots;
    }

    /**
     * Checks if is out of bullets.
     *
     * @return true, if is out of bullets
     */
    public boolean isOutOfBullets() {
        return outOfBullets;
    }

    /**
     * Sets the out of bullets.
     *
     * @param outOfBullets the new out of bullets
     */
    public void setOutOfBullets(boolean outOfBullets) {
        this.outOfBullets = outOfBullets;
    }

    /**
     * Increase score.
     */
    public void increaseScore(){
        this.score++;
    }


}
