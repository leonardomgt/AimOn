package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.aimon.game.model.entities.DuckModel;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// TODO: Auto-generated Javadoc
/**
 * Created by joaofurriel on 28/04/17.
 */

public class DuckView extends EntityView {

    /** The Constant DEWEY_SPRITE_RIGHT. */
    private static final String DEWEY_SPRITE_RIGHT = "dewey_right.png";
    
    /** The Constant DEWEY_SPRITE_LEFT. */
    private static final String DEWEY_SPRITE_LEFT = "dewey_left.png";
    
    /** The Constant HUEY_SPRITE_RIGHT. */
    private static final String HUEY_SPRITE_RIGHT = "huey_right.png";
    
    /** The Constant HUEY_SPRITE_LEFT. */
    private static final String HUEY_SPRITE_LEFT = "huey_left.png";
    
    /** The Constant LOUIE_SPRITE_RIGHT. */
    private static final String LOUIE_SPRITE_RIGHT = "louie_right.png";
    
    /** The Constant LOUIE_SPRITE_LEFT. */
    private static final String LOUIE_SPRITE_LEFT = "louie_left.png";

    /** The Constant DEWEY_DEAD. */
    public static final String DEWEY_DEAD = "dewey_dead.png";
    
    /** The Constant DEWEY_SHOT. */
    public static final String DEWEY_SHOT = "dewey_shot.png";
    
    /** The Constant HUEY_DEAD. */
    public static final String HUEY_DEAD = "huey_dead.png";
    
    /** The Constant HUEY_SHOT. */
    public static final String HUEY_SHOT = "huey_shot.png";
    
    /** The Constant LOUIE_DEAD. */
    public static final String LOUIE_DEAD = "louie_dead.png";
    
    /** The Constant LOUIE_SHOT. */
    public static final String LOUIE_SHOT = "louie_shot.png";

    /** The state time. */
    private float stateTime = 0;

    /**
     * Instantiates a new duck view.
     *
     * @param game the game
     * @param type the type
     */
    public DuckView(AimOn game, DuckModel.DuckType type) {
        super(game, type);
    }

    /** The animation right. */
    private Animation<TextureRegion> animationRight;
    
    /** The animation left. */
    private Animation<TextureRegion> animationLeft;
    
    /** The animation dead. */
    private Animation<TextureRegion> animationDead;
    
    /** The animation shot. */
    private Animation<TextureRegion> animationShot;
    
    /** The texture shot. */
    private Texture textureShot;
    
    /** The texture dead. */
    private Texture textureDead;


    /* (non-Javadoc)
     * @see view.game.entities.EntityView#createSprite(AimOn)
     */
    public Sprite createSprite(AimOn game) {

        loadAssets(game);


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

    /**
     * Load assets.
     *
     * @param game the game
     */
    private void loadAssets(AimOn game) {

        switch ((DuckModel.DuckType)argument){
            case DEWEY:
                game.getAssetManager().load(DEWEY_SPRITE_RIGHT, Texture.class);
                game.getAssetManager().load(DEWEY_SPRITE_LEFT, Texture.class);

                game.getAssetManager().load(DEWEY_DEAD, Texture.class);
                game.getAssetManager().load(DEWEY_SHOT, Texture.class);

                break;

            case LOUIE:
                game.getAssetManager().load(LOUIE_SPRITE_RIGHT, Texture.class);
                game.getAssetManager().load(LOUIE_SPRITE_LEFT, Texture.class);

                game.getAssetManager().load(LOUIE_DEAD, Texture.class);
                game.getAssetManager().load(LOUIE_SHOT, Texture.class);

                break;

            case HUEY:
                game.getAssetManager().load(HUEY_SPRITE_RIGHT, Texture.class);
                game.getAssetManager().load(HUEY_SPRITE_LEFT, Texture.class);

                game.getAssetManager().load(HUEY_DEAD, Texture.class);
                game.getAssetManager().load(HUEY_SHOT, Texture.class);

                break;
        }

        game.getAssetManager().finishLoading();

    }

    /* (non-Javadoc)
     * @see view.game.entities.EntityView#draw(SpriteBatch)
     */
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }


    /**
     * Update.
     *
     * @param model the model
     * @param delta the delta
     * @param numberOfDucks the number of ducks
     */
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
