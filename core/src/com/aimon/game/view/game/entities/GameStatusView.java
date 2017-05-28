package com.aimon.game.view.game.entities;

import com.aimon.game.AimOn;
import com.aimon.game.model.entities.EntityModel;
import com.aimon.game.model.entities.PlayerModel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import static com.aimon.game.view.game.GameScreen.ALIVE_DUCK;
import static com.aimon.game.view.game.GameScreen.AMMO;
import static com.aimon.game.view.game.GameScreen.AMMO_EMPTY;
import static com.aimon.game.view.game.GameScreen.BULLET_BOX;
import static com.aimon.game.view.game.GameScreen.DEWEY_SHOT;
import static com.aimon.game.view.game.GameScreen.MISSED_SHOT;
import static com.aimon.game.view.game.GameScreen.PIXEL_TO_METER;

/**
 * Created by joaofurriel on 27/05/17.
 */

public class GameStatusView {

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


    public GameStatusView(AimOn game, PlayerModel player) {
        this.game = game;
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.playerBullets = player.getNumberOfBullets();
        this.gunBullets = player.getGun().getNumberOfBullets();
        this.numberOfAliveDucks = game.getMainModel().getNumberOfAliveDucks();
        this.model = player;
        this.createSprites();
        this.killedDucks = model.getKilledDucks();
        this.missedShots = model.getMissedShots();

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

        texture = game.getAssetManager().get(DEWEY_SHOT);
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



    }

    private void drawFont(CharSequence str, SpriteBatch batch, float x, float y) {

        font.draw(batch, " x ", (x-0.3f) / PIXEL_TO_METER, y / PIXEL_TO_METER);
        font.draw(batch, str, x / PIXEL_TO_METER, y / PIXEL_TO_METER);

    }


    public void update(EntityModel model) {
        this.numberOfAliveDucks = game.getMainModel().getNumberOfAliveDucks();
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
