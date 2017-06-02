package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.aimon.game.model.entities.EntityModel;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.aimon.game.view.game.GameScreen.PIXEL_TO_METER;

/**
 * Class responsible for drawing and update the Views
 */

/**
 * Abstract class to hold a sprite with a position and rotation
 **/

public abstract class EntityView {

    /** Sprite - entity view. */

    Sprite sprite;
    
    /** A field with flexible type class.
     *  Can be used as auxiliary by Sub-Classes. */
    protected Object argument;

    /**
     * Instantiates a new entity view.
     *
     * @param game the game
     * @param arg the flexible argument
     */
    EntityView(AimOn game, Object arg) {
        this.argument = arg;
        this.sprite = createSprite(game);
    }

    /**
     * Draw the View to screen.
     *
     * @param batch the batch
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    /**
     * Creates the sprite.
     *
     * @param game the game
     * @return the sprite
     */
    public abstract Sprite createSprite(AimOn game);

    /**
     * Update Entity View.
     *
     * @param model the model
     */
    public void update(EntityModel model) {

        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        sprite.setRotation((float) Math.toDegrees(model.getRotation()));

    }

}
