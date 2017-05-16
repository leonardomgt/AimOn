package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Leo on 29/04/2017.
 */

public class AimView extends EntityView {

    public AimView(AimOn game) {
        super(game, null);
        sprite.setScale(0.2f, 0.2f);
    }


    public Sprite createSprite(AimOn game) {

        Texture texture = game.getAssetManager().get(GameScreen.AIM_IMAGE, Texture.class);

        return new Sprite(texture, texture.getWidth(), texture.getHeight());

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
