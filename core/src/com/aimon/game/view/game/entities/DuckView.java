package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.aimon.game.model.entities.DuckModel;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class DuckView extends EntityView {

    private float stateTime = 0;

    public DuckView(AimOn game, DuckModel.DuckType type) {
        super(game, type);
    }

    private Animation<TextureRegion> animationRight;
    private Animation<TextureRegion> animationLeft;
    private Animation<TextureRegion> animationDead;
    private Animation<TextureRegion> animationShot;
    private Texture textureShot;
    private Texture textureDead;





    public Sprite createSprite(AimOn game) {

        Texture textureRight = game.getAssetManager().get(((DuckModel.DuckType) argument).getName() + "_right.png", Texture.class);

        TextureRegion[][] region = TextureRegion.split(textureRight, textureRight.getWidth()/3, textureRight.getHeight());
        TextureRegion[] framesRight = new TextureRegion[3];
        System.arraycopy(region[0], 0, framesRight, 0, 3);
        this.animationRight = new Animation<TextureRegion>(0.1f, framesRight);

        Texture textureLeft = game.getAssetManager().get(((DuckModel.DuckType) argument).getName() + "_left.png", Texture.class);
        TextureRegion[][] regionLeft = TextureRegion.split(textureLeft, textureLeft.getWidth()/3, textureLeft.getHeight());

        TextureRegion[] framesLeft = new TextureRegion[3];
        System.arraycopy(regionLeft[0], 0, framesLeft, 0, 3);
        this.animationLeft = new Animation<TextureRegion>(0.1f, framesLeft);

        textureDead = game.getAssetManager().get(((DuckModel.DuckType) argument).getName() + "_dead.png", Texture.class);
        TextureRegion[][] regionDead = TextureRegion.split(textureDead, textureDead.getWidth(), textureDead.getHeight());
        TextureRegion[] framesDead = new TextureRegion[1];
        System.arraycopy(regionDead[0], 0, framesDead, 0, 1);
        this.animationDead = new Animation<TextureRegion>(0.1f, framesDead);

        textureShot = game.getAssetManager().get(((DuckModel.DuckType) argument).getName() + "_shot.png", Texture.class);
        TextureRegion[][] regionShot = TextureRegion.split(textureShot, textureShot.getWidth(), textureShot.getHeight());
        TextureRegion[] framesShot = new TextureRegion[1];
        System.arraycopy(regionShot[0], 0, framesShot, 0, 1);
        this.animationShot = new Animation<TextureRegion>(0.1f, framesShot);

        return new Sprite(animationRight.getKeyFrame(0));

    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }


    public void update(DuckModel model, float delta, int numberOfDucks) {

        this.stateTime += delta;
        TextureRegion tr = null;

        if (model.isAlive()) {
            if (model.getState() != DuckModel.DuckState.FLOAT_DOWN && model.getState() != DuckModel.DuckState.GO_DOWN) {
                switch (model.getDirection()) {
                    case RIGHT:
                        tr = animationRight.getKeyFrame(stateTime / numberOfDucks, true);
                        break;
                    case LEFT:
                        tr = animationLeft.getKeyFrame(stateTime / numberOfDucks, true);
                        break;
                }
            }
            else {
                switch (model.getDirection()) {
                    case RIGHT:
                        tr = animationRight.getKeyFrame(0.3f, false);
                        break;
                    case LEFT:
                        tr = animationLeft.getKeyFrame(0f, false);
                        break;
                }

            }
        }

        else {

            tr = model.getState() == DuckModel.DuckState.FALLING || model.getState() == DuckModel.DuckState.DEAD ? animationDead.getKeyFrame(0f, true) : animationShot.getKeyFrame(0f, true);

        }

        if (tr != null) {
            sprite.setScale(model.getDepthFactor(), model.getDepthFactor());
            sprite.setRegion(tr);
        }

        super.update(model);

    }

}
