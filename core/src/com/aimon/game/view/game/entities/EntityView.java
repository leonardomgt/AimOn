package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.aimon.game.view.game.GameScreen.PIXEL_TO_METER;

/**
 * Created by joaofurriel on 28/04/17.
 */

/**
 * Abstract class to hold a sprite with a position and rotation
 **/

public abstract class EntityView {

    /**
     * Sprite - entity view
     */

    Sprite sprite;

    EntityView(AimOn game) {
        this.sprite = createSprite(game);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public abstract Sprite createSprite(AimOn game);

    /*
    public void update(EntityModel model) {



    }
    */


}