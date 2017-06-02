package com.aimon.game.model.entities;

// TODO: Auto-generated Javadoc
/**
 * Created by joaofurriel on 25/05/17.
 */

public class GunModel extends EntityModel {


    /**
     * The Enum GunState.
     */
    public enum GunState {/** The idle. */
IDLE, /** The firing. */
 FIRING, /** The reloading. */
 RELOADING};

    /** The capacity. */
    private int capacity;
    
    /** The number of bullets. */
    private int numberOfBullets;
    
    /** The shot delay. */
    private float shotDelay;
    
    /** The last shot moment. */
    private float lastShotMoment = 0;
    
    /** The reload bullet delay. */
    private float reloadBulletDelay;
    
    /** The slide delay. */
    private float slideDelay;
    
    /** The last reload moment. */
    private float lastReloadMoment = 0;
    
    /** The gun life time. */
    private float gunLifeTime = 0;

    /** The reloaded bullets. */
    private int reloadedBullets = 0;

    /** The state. */
    private GunState state = GunModel.GunState.IDLE;


    /**
     * Instantiates a new gun model.
     */
    public GunModel() {
        super(0,0,0f);
        this.capacity = 6;
        this.shotDelay = .5f;
        this.numberOfBullets = capacity;
        this.reloadBulletDelay = .5f;
        this.slideDelay = 1.6f;

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
     * Sets the state.
     *
     * @param state the new state
     */
    public void setState(GunState state) {
        this.state = state;
    }

    /**
     * Sets the last shot moment.
     *
     * @param lastShotMoment the new last shot moment
     */
    public void setLastShotMoment(float lastShotMoment) {
        this.lastShotMoment = lastShotMoment;
    }

    /**
     * Gets the capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the shot delay.
     *
     * @return the shot delay
     */
    public float getShotDelay() {
        return shotDelay;
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
     * Gets the state.
     *
     * @return the state
     */
    public GunState getState() {
        return state;
    }

    /**
     * Gets the last shot moment.
     *
     * @return the last shot moment
     */
    public float getLastShotMoment() {
        return lastShotMoment;
    }

    /**
     * Gets the last reload moment.
     *
     * @return the last reload moment
     */
    public float getLastReloadMoment() {
        return lastReloadMoment;
    }

    /**
     * Sets the last reload moment.
     *
     * @param lastReloadingMoment the new last reload moment
     */
    public void setLastReloadMoment(float lastReloadingMoment) {
        this.lastReloadMoment = lastReloadingMoment;
    }

    /**
     * Gets the reload bullet delay.
     *
     * @return the reload bullet delay
     */
    public float getReloadBulletDelay() {
        return reloadBulletDelay;
    }

    /**
     * Gets the gun life time.
     *
     * @return the gun life time
     */
    public float getGunLifeTime() {
        return gunLifeTime;
    }

    /**
     * Sets the gun life time.
     *
     * @param gunLifeTime the new gun life time
     */
    public void setGunLifeTime(float gunLifeTime) {
        this.gunLifeTime = gunLifeTime;
    }

    /**
     * Gets the reloaded bullets.
     *
     * @return the reloaded bullets
     */
    public int getReloadedBullets() {
        return reloadedBullets;
    }

    /**
     * Sets the reloaded bullets.
     *
     * @param reloadedBullets the new reloaded bullets
     */
    public void setReloadedBullets(int reloadedBullets) {
        this.reloadedBullets = reloadedBullets;
    }

    /**
     * Gets the empty spaces.
     *
     * @return the empty spaces
     */
    public int getEmptySpaces() {
        return this.capacity - this.numberOfBullets;
    }

    /**
     * Gets the slide delay.
     *
     * @return the slide delay
     */
    public float getSlideDelay() {
        return slideDelay;
    }
}
