package com.aimon.game.model.entities;

/**
 * Created by joaofurriel on 25/05/17.
 */

public class GunModel extends EntityModel {

    public enum GunState {IDLE, FIRING, RELOADING};

    private int capacity;
    private int numberOfShots;
    private float shotDelay;
    private float lastShotMoment = 0;
    private float reloadBulletDelay;
    private float lastReloadMoment = 0;
    private float gunLifeTime = 0;

    private int reloadedBullets = 0;

    private GunState state = GunModel.GunState.IDLE;


    public GunModel(int capacity, float shotDelay, float reloadBulletDelay,  int numberOfShots) {
        super(0,0,0f);
        this.capacity = capacity;
        this.shotDelay = shotDelay;
        this.numberOfShots = numberOfShots;
        this.reloadBulletDelay = reloadBulletDelay;

    }

    public void setNumberOfShots(int numberOfShots) {
        this.numberOfShots = numberOfShots;
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

    public int getNumberOfShots() {
        return numberOfShots;
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
}
