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
 * Created by joaofurriel on 27/05/17.
 */

public class GameStatusView {

    public static final String BULLET_BOX = "bullet_box.png";
    public static final String AMMO = "ammo.png";
    public static final String AMMO_EMPTY = "ammo_empty.png";
    public static final String MISSED_SHOT = "missed.png";
    public static final String ALIVE_DUCK = "alive_duck.png";
    public static final String DEAD_DUCK = "dewey_shot.png";


    private int playerBullets;
    private int gunBullets;
    private int playerScore;
    private int numberOfAliveDucks;
    private int missedShots;
    private int killedDucks;

    private BitmapFont font;
    private PlayerModel model;
    private AimOn game;

    private Sprite playerBulletsSprite;
    private Sprite gunBulletsSprite;
    private Sprite gunBulletEmptySprite;
    private Sprite playerScoreSprite;
    private Sprite numberOfDucksSprite;
    private Sprite missedShotsSprite;
    private Sprite killedDucksSprite;


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

    private void loadAssets() {
        this.game.getAssetManager().load(ALIVE_DUCK, Texture.class);
        this.game.getAssetManager().load(DEAD_DUCK, Texture.class);
        this.game.getAssetManager().load(BULLET_BOX, Texture.class);
        this.game.getAssetManager().load(AMMO, Texture.class);
        this.game.getAssetManager().load(AMMO_EMPTY, Texture.class);
        this.game.getAssetManager().load(MISSED_SHOT, Texture.class);

        this.game.getAssetManager().finishLoading();

    }

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

    private Sprite createSprite(Texture texture, float scale, float x, float y) {

        Sprite sprite = new Sprite(texture);
        sprite.setScale(scale);
        sprite.setCenter(x/PIXEL_TO_METER,y/PIXEL_TO_METER);
        return sprite;

    }

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

    private void drawFont(CharSequence str, SpriteBatch batch, float x, float y) {

        font.draw(batch, " x ", (x-0.3f) / PIXEL_TO_METER, y / PIXEL_TO_METER);
        font.draw(batch, str, x / PIXEL_TO_METER, y / PIXEL_TO_METER);

    }


    public void update(EntityModel model) {
        this.numberOfAliveDucks = game.getGameScreen().getModel().getNumberOfAliveDucks();
        this.killedDucks = ((PlayerModel) model).getKilledDucks();
        this.missedShots = ((PlayerModel) model).getMissedShots();

    }

    public void setPlayerBulletsString(int playerBullets) {
        this.playerBullets = playerBullets;
    }

    public void setGunBullets(int gunBullets) {
        this.gunBullets = gunBullets;
    }

    public void spendBullet() {
        this.gunBullets--;
    }

}
