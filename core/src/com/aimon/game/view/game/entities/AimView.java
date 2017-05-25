package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.aimon.game.model.entities.EntityModel;
import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Leo on 29/04/2017.
 */

public class AimView extends EntityView {

    float aimZoom = 1;
    float INITIAL_ZOOM = .1f;

    public AimView(AimOn game) {
        super(game, null);
        sprite.setScale(INITIAL_ZOOM, INITIAL_ZOOM);
    }


    public Sprite createSprite(AimOn game) {

        Texture texture = game.getAssetManager().get(GameScreen.AIM_IMAGE, Texture.class);

        return new Sprite(texture, texture.getWidth(), texture.getHeight());

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void update(EntityModel model) {
        sprite.setScale(aimZoom*INITIAL_ZOOM, aimZoom*INITIAL_ZOOM);

        super.update(model);

    }

    public void setAimZoom(float aimZoom) {
        this.aimZoom = aimZoom;
    }
}
