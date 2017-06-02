package com.aimon.game.model.entities;

/**
 * EntityModel. Abstract class use to hold data of the entities
 */

public abstract class EntityModel {

    /** The rotation. */
    private float x, y, rotation;

    /**
     * Instantiates a new entity model.
     *
     * @param x the x
     * @param y the y
     * @param rotation the rotation
     */
    public EntityModel(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the rotation.
     *
     * @return the rotation
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the position.
     *
     * @param x the x
     * @param y the y
     */
    public void setPosition(float x, float y) {

        this.x = x;
        this.y = y;

    }

    /**
     * Sets the rotation.
     *
     * @param rotation the new rotation
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
