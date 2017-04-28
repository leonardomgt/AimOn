package com.aimon.game.model.entities;

/**
 * Created by joaofurriel on 28/04/17.
 */

public abstract class EntityModel {

    private float x, y, rotation;

    public EntityModel(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setPosition(float x, float y) {

        this.x = x;
        this.y = y;

    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
