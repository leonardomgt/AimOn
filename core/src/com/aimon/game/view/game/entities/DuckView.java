package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.EntityModel;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class DuckView extends EntityView{

    private float stateTime;

    public DuckView(AimOn game) {
        super(game);
    }

    private Animation<TextureRegion> animation_right;
    private Animation<TextureRegion> animation_left;

    public Sprite createSprite(AimOn game) {

        Texture textureRight = game.getAssetManager().get("dewey_right.png", Texture.class);
        //Texture textureRight = game.getAssetManager().get(model.geType().getName() + "_right.png", Texture.class);
        TextureRegion[][] region = TextureRegion.split(textureRight, textureRight.getWidth()/3, textureRight.getHeight());
        TextureRegion[] framesRight = new TextureRegion[3];
        System.arraycopy(region[0], 0, framesRight, 0, 3);
        this.animation_right = new Animation<TextureRegion>(0.1f, framesRight);

        Texture textureLeft = game.getAssetManager().get("dewey_left.png", Texture.class);
        TextureRegion[][] regionLeft = TextureRegion.split(textureLeft, textureLeft.getWidth()/3, textureLeft.getHeight());
        TextureRegion[] framesLeft = new TextureRegion[3];
        System.arraycopy(regionLeft[0], 0, framesLeft, 0, 3);
        this.animation_left = new Animation<TextureRegion>(0.1f, framesLeft);

        return new Sprite(animation_right.getKeyFrame(0));

    }

    public void draw(SpriteBatch batch) {

        super.draw(batch);
    }


    public void update(DuckModel model, float delta, int numberOfDucks) {

        this.stateTime += delta;
        TextureRegion tr = null;

        if(model.getState() != DuckModel.DuckState.FLOAT_DOWN && model.getState() != DuckModel.DuckState.GO_DOWN) {
            switch (model.getDirection()) {
                case RIGHT:
                    tr = animation_right.getKeyFrame(stateTime/numberOfDucks, true);
                    break;
                case LEFT:
                    tr = animation_left.getKeyFrame(stateTime/numberOfDucks, true);
                    break;
            }
        }
        else {
            switch (model.getDirection()) {
                case RIGHT:
                    tr = animation_right.getKeyFrame(0.3f, false);
                    break;
                case LEFT:
                    tr = animation_left.getKeyFrame(0f, false);
                    break;
            }
        }


        if(tr != null) {
            sprite.setRegion(tr);
        }
        super.update(model);

    }

}
