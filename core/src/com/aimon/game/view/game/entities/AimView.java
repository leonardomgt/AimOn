package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Leo on 29/04/2017.
 */

public class AimView extends EntityView {

    public AimView(AimOn game) {
        super(game, null);

    }


    public Sprite createSprite(AimOn game) {

        Texture texture = game.getAssetManager().get(GameScreen.AIM_IMAGE, Texture.class);

        return new Sprite(texture, texture.getWidth(), texture.getHeight());


    }
}
