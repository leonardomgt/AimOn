package com.aimon.game.model.entities;

/**
 * Created by joaofurriel on 25/05/17.
 */

public class GunModel extends EntityModel {


    public enum GunState {IDLE, FIRING, RELOADING};

    private int capacity;
    private int numberOfBullets;
    private float shotDelay;
    private float lastShotMoment = 0;
    private float reloadBulletDelay;
    private float slideDelay;
    private float lastReloadMoment = 0;
    private float gunLifeTime = 0;

    private int reloadedBullets = 0;

    private GunState state = GunModel.GunState.IDLE;


    public GunModel() {
        super(0,0,0f);
        this.capacity = 6;
        this.shotDelay = .5f;
        this.numberOfBullets = capacity;
        this.reloadBulletDelay = .5f;
        this.slideDelay = 1.6f;

    }

    public void setNumberOfBullets(int numberOfBullets) {
        this.numberOfBullets = numberOfBullets;
    }

    public void setState(GunState state) {
        this.state = state;
    }

    public void setLastShotMoment(float lastShotMoment) {
        this.lastShotMoment = lastShotMoment;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getShotDelay() {
        return shotDelay;
    }

    public int getNumberOfBullets() {
        return numberOfBullets;
    }

    public GunState getState() {
        return state;
    }

    public float getLastShotMoment() {
        return lastShotMoment;
    }

    public float getLastReloadMoment() {
        return lastReloadMoment;
    }

    public void setLastReloadMoment(float lastReloadingMoment) {
        this.lastReloadMoment = lastReloadingMoment;
    }

    public float getReloadBulletDelay() {
        return reloadBulletDelay;
    }

    public float getGunLifeTime() {
        return gunLifeTime;
    }

    public void setGunLifeTime(float gunLifeTime) {
        this.gunLifeTime = gunLifeTime;
    }

    public int getReloadedBullets() {
        return reloadedBullets;
    }

    public void setReloadedBullets(int reloadedBullets) {
        this.reloadedBullets = reloadedBullets;
    }

    public int getEmptySpaces() {
        return this.capacity - this.numberOfBullets;
    }

    public float getSlideDelay() {
        return slideDelay;
    }
}
