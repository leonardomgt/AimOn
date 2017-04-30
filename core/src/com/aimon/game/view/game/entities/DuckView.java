package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
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

    private Animation<TextureRegion> animation;

    public Sprite createSprite(AimOn game) {

        Texture texture = game.getAssetManager().get("dewey.png", Texture.class);
        TextureRegion[][] region = TextureRegion.split(texture, texture.getWidth()/3, texture.getHeight() / 4);
        TextureRegion[] frames = new TextureRegion[12];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(region[i], 0, frames, 3*i, 3);
        }
        System.arraycopy(region[3], 0, frames, 9, 2);

        this.animation = new Animation<TextureRegion>(.05f, frames);
        return new Sprite(animation.getKeyFrame(0));

    }

    public void draw(SpriteBatch batch) {

        super.draw(batch);
    }

    @Override
    public void update(EntityModel model, float delta) {

        this.stateTime += delta;

        TextureRegion tr = animation.getKeyFrame(stateTime, true);
        if(tr != null) {
            sprite.setRegion(tr);
        }

        super.update(model, delta);

    }

}
