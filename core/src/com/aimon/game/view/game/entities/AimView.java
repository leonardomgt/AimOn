package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.aimon.game.model.entities.EntityModel;
import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// TODO: Auto-generated Javadoc
/**
 * Created by Leo on 29/04/2017.
 */

public class AimView extends EntityView {

    /** The aim zoom. */
    float aimZoom = 1;
    
    /** The initial zoom. */
    float INITIAL_ZOOM = .1f;

    /** The Constant AIM_IMAGE. */
    public static final String AIM_IMAGE = "arm-cross.png";

    /**
     * Instantiates a new aim view.
     *
     * @param game the game
     */
    public AimView(AimOn game) {
        super(game, null);
        sprite.setScale(INITIAL_ZOOM, INITIAL_ZOOM);
    }


    /* (non-Javadoc)
     * @see view.game.entities.EntityView#createSprite(AimOn)
     */
    public Sprite createSprite(AimOn game) {

        game.getAssetManager().load(AIM_IMAGE, Texture.class);
        game.getAssetManager().finishLoading();


        Texture texture = game.getAssetManager().get(AIM_IMAGE, Texture.class);
        return new Sprite(texture, texture.getWidth(), texture.getHeight());

    }

    /* (non-Javadoc)
     * @see view.game.entities.EntityView#draw(SpriteBatch)
     */
    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    /* (non-Javadoc)
     * @see view.game.entities.EntityView#update(EntityModel)
     */
    @Override
    public void update(EntityModel model) {
        sprite.setScale(aimZoom*INITIAL_ZOOM, aimZoom*INITIAL_ZOOM);

        super.update(model);

    }

    /**
     * Sets the aim zoom.
     *
     * @param aimZoom the new aim zoom
     */
    public void setAimZoom(float aimZoom) {
        this.aimZoom = aimZoom;
    }
}
