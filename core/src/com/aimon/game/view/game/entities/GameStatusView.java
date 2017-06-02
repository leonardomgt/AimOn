package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.EntityModel;
import com.aimon.game.model.entities.PlayerModel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.aimon.game.view.game.GameScreen.PIXEL_TO_METER;

/**
 * Class that records the Game Status and show it to screen
 */

public class GameStatusView {

    /** The path to BULLET BOX image asset. */
    public static final String BULLET_BOX = "bullet_box.png";

    /** The path to AMMO image asset. */
    public static final String AMMO = "ammo.png";

    /** The path to NO AMMO image asset. */
    public static final String AMMO_EMPTY = "ammo_empty.png";

    /** The path to MISSED SHOT image asset. */
    public static final String MISSED_SHOT = "missed.png";

    /** The path to ALIVE DUCK image asset. */
    public static final String ALIVE_DUCK = "alive_duck.png";

    /** The path to DEAD DUCK image asset. */
    public static final String DEAD_DUCK = "dewey_shot.png";


    /** The number of player bullets. */
    private int playerBullets;
    
    /** The gun remaining bullets. */
    private int gunBullets;
    
    /** The number of alive ducks. */
    private int numberOfAliveDucks;
    
    /** The number of missed shots. */
    private int missedShots;
    
    /** The number of killed ducks. */
    private int killedDucks;

    /** The font. */
    private BitmapFont font;
    
    /** The player model. */
    private PlayerModel model;
    
    /** The AimOn game. */
    private AimOn game;

    /** The player bullets sprite. */
    private Sprite playerBulletsSprite;
    
    /** The gun bullets sprite. */
    private Sprite gunBulletsSprite;
    
    /** The gun bullet empty sprite. */
    private Sprite gunBulletEmptySprite;
    
    /** The player score sprite. */
    private Sprite playerScoreSprite;
    
    /** The number of ducks sprite. */
    private Sprite numberOfDucksSprite;
    
    /** The missed shots sprite. */
    private Sprite missedShotsSprite;
    
    /** The killed ducks sprite. */
    private Sprite killedDucksSprite;


    /**
     * Instantiates a new game status view.
     *
     * @param game the game
     * @param mainModel the main model
     */
    public GameStatusView(AimOn game, MainModel mainModel) {
        this.game = game;
        loadAssets();

        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.playerBullets = mainModel.getPlayerModel().getNumberOfBullets();
        this.gunBullets = mainModel.getPlayerModel().getGun().getNumberOfBullets();
        this.numberOfAliveDucks = mainModel.getNumberOfAliveDucks();
        this.model = mainModel.getPlayerModel();
        this.createSprites();
        this.killedDucks = model.getKilledDucks();
        this.missedShots = model.getMissedShots();


    }

    /**
     * Load assets needed for Game Status view.
     */
    private void loadAssets() {
        this.game.getAssetManager().load(ALIVE_DUCK, Texture.class);
        this.game.getAssetManager().load(DEAD_DUCK, Texture.class);
        this.game.getAssetManager().load(BULLET_BOX, Texture.class);
        this.game.getAssetManager().load(AMMO, Texture.class);
        this.game.getAssetManager().load(AMMO_EMPTY, Texture.class);
        this.game.getAssetManager().load(MISSED_SHOT, Texture.class);

        this.game.getAssetManager().finishLoading();

    }

    /**
     * Creates the sprites.
     */
    private void createSprites() {

        //empty ammo
        Texture texture = game.getAssetManager().get(AMMO_EMPTY);
        this.gunBulletEmptySprite = createSprite(texture, 1.7f*0.3f, 1,1);

        //ammo
        texture = game.getAssetManager().get(AMMO);
        this.gunBulletsSprite = createSprite(texture,0.3f, 1, 1);

        //bullet Box
        texture = game.getAssetManager().get(BULLET_BOX);
        this.playerBulletsSprite = createSprite(texture,0.1f, model.getX(), model.getY()-1f);

        //number of ducks
        texture = game.getAssetManager().get(ALIVE_DUCK);
        this.numberOfDucksSprite = createSprite(texture, 1f, model.getX(), model.getY()-2f);

        texture = game.getAssetManager().get(DEAD_DUCK);
        this.killedDucksSprite = createSprite(texture,1, model.getX(), model.getY()-3f);

        texture = game.getAssetManager().get(MISSED_SHOT);
        this.missedShotsSprite = createSprite(texture, 0.2f, model.getX(), model.getY()-4f);


    }

    /**
     * Creates the sprite.
     *
     * @param texture the texture
     * @param scale the scale
     * @param x the x
     * @param y the y
     * @return the sprite
     */
    private Sprite createSprite(Texture texture, float scale, float x, float y) {

        Sprite sprite = new Sprite(texture);
        sprite.setScale(scale);
        sprite.setCenter(x/PIXEL_TO_METER,y/PIXEL_TO_METER);
        return sprite;

    }

    /**
     * Draw Game Status on Screen.
     *
     * @param batch the batch
     */
    public void draw(SpriteBatch batch) {

        //gun bullets
        for (int i = 1; i <= this.model.getGun().getCapacity(); i++) {

            this.gunBulletEmptySprite.setCenter((model.getX() + 1 - 0.2f*i) / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
            this.gunBulletEmptySprite.draw(batch);

        }

        for (int i = 1; i <= this.gunBullets; i++) {

            this.gunBulletsSprite.setCenter((model.getX() + 1 - 0.2f*i) / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
            this.gunBulletsSprite.draw(batch);
        }

        //player bullets

        drawFont(Integer.toString(this.playerBullets), batch, model.getX() + 0.5f, model.getY() - 1f);
        this.playerBulletsSprite.draw(batch);

        //number of alive ducks

        drawFont(Integer.toString(this.numberOfAliveDucks), batch, model.getX() + 0.5f, model.getY() - 2f);
        this.numberOfDucksSprite.draw(batch);

        drawFont(Integer.toString(this.killedDucks), batch, model.getX() + 0.5f, model.getY() - 3f);
        this.killedDucksSprite.draw(batch);

        drawFont(Integer.toString(this.missedShots), batch, model.getX() + 0.5f, model.getY() - 4f);
        this.missedShotsSprite.draw(batch);



        font.draw(batch, game.getGameScreen().getModel().isBonus() ? "Bonus" : "Level: " +  game.getGameScreen().getModel().getLevel(), (model.getX() - 0.2f) / PIXEL_TO_METER, (model.getY() - 4.7f) / PIXEL_TO_METER);

        if(game.getGameScreen().getModel().isBonus()) {
            font.draw(batch, Integer.toString(MainModel.getBonusTime() - (int) game.getGameScreen().getModel().getLevelTime()), (model.getX() - 0.2f) / PIXEL_TO_METER, (model.getY() - 5.7f) / PIXEL_TO_METER);
        }





    }

    /**
     * Draw text to Screen.
     *
     * @param str the string to draw
     * @param batch the batch
     * @param x the x position
     * @param y the y position
     */
    private void drawFont(CharSequence str, SpriteBatch batch, float x, float y) {

        font.draw(batch, " x ", (x-0.3f) / PIXEL_TO_METER, y / PIXEL_TO_METER);
        font.draw(batch, str, x / PIXEL_TO_METER, y / PIXEL_TO_METER);

    }


    /**
     * Update Game Status.
     *
     * @param model the model
     */
    public void update(EntityModel model) {
        this.numberOfAliveDucks = game.getGameScreen().getModel().getNumberOfAliveDucks();
        this.killedDucks = ((PlayerModel) model).getKilledDucks();
        this.missedShots = ((PlayerModel) model).getMissedShots();

    }

    /**
     * Sets the player bullets.
     *
     * @param playerBullets the new player bullets
     */
    public void setPlayerBullets(int playerBullets) {
        this.playerBullets = playerBullets;
    }

    /**
     * Sets the gun bullets.
     *
     * @param gunBullets the new gun bullets
     */
    public void setGunBullets(int gunBullets) {
        this.gunBullets = gunBullets;
    }

    /**
     * Spend bullet. Decrements gun bullets.
     */
    public void spendBullet() {
        this.gunBullets--;
    }

}
