package com.aimon.game.model;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.entities.AimModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.GroundModel;
import com.aimon.game.model.entities.GunModel;
import com.aimon.game.model.entities.PlayerModel;
import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class MainModel {

    public static final float DUCKS_MIN_DEPTH = 10f;
    public static final float DUCKS_MAX_DEPTH = 15f;
    private static final int BONUS_TIME = 15;

    public enum LevelState {RUNNING, GAME_OVER, NEXT_LEVEL}

    private List<DuckModel> ducks;
    private AimModel aim;
    private GroundModel ground;
    private final int numberOfDucks;
    private int numberOfAliveDucks;
    private int numberOfDucksOnGround;
    private PlayerModel playerModel;
    private LevelState levelState = LevelState.RUNNING;
    private int level;
    private boolean bonus = false;
    private float levelTime = 0;

    public MainModel(float aimX, float aimY, int numberOfDucks, PlayerModel playerModel, int level) {

        this.aim = new AimModel(aimX,aimY);
        this.numberOfDucks = numberOfDucks;
        this.ducks = new ArrayList<DuckModel>();
        this.playerModel = playerModel;
        this.numberOfAliveDucks = numberOfDucks;
        this.numberOfDucksOnGround = 0;
        this.level = level;
        bonus = level == 0 ? true : false;

        for (int i = 0; i < numberOfDucks; i++) {

            DuckModel.DuckType type;
            int t = random.nextInt(3);
            switch (t) {
                case 0:
                    type = DuckModel.DuckType.DEWEY;
                    break;
                case 1:
                    type = DuckModel.DuckType.LOUIE;
                    break;
                default:
                    type = DuckModel.DuckType.HUEY;
                    break;

            }
            t = random.nextInt(3);
            float x;
            float y;
            switch (t) {
                case 0: //nasce à esquerda
                    x = MathUtils.random(0.0f, MainController.getControllerWidth()/2 - GameScreen.VIEWPORT_WIDTH/2 - 1);
                    y = MathUtils.random(MainController.getControllerGroundHeight() + 5, MainController.getControllerHeight());
                    break;
                case 1: //nasce à direita
                    x = MathUtils.random(MainController.getControllerWidth()/2 + GameScreen.VIEWPORT_WIDTH/2 + 1, MainController.getControllerWidth());
                    y = MathUtils.random(MainController.getControllerGroundHeight() + 5, MainController.getControllerHeight());
                    break;
                default: //nasce em cima
                    x = MathUtils.random(0.0f, MainController.getControllerWidth());
                    y = MathUtils.random(GameScreen.VIEWPORT_HEIGHT + 1, MainController.getControllerHeight());
                    break;
            }

            DuckModel currentDuck = new DuckModel(x,y, 0, type, MathUtils.random(DUCKS_MIN_DEPTH,DUCKS_MAX_DEPTH));


            this.ducks.add(currentDuck);
            this.ground = GroundModel.getInstance();

        }
        Collections.sort(this.ducks);

    }

    public int getNumberOfDucks() {
        return numberOfDucks;
    }

    public int getNumberOfAliveDucks() {
        return numberOfAliveDucks;
    }

    public List<DuckModel> getDucks() {
        return this.ducks;
    }

    public AimModel getAim() { return aim; }

    public GroundModel getGround() { return this.ground;}

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public void decreaseNumberOfDucks() {
        this.numberOfAliveDucks--;
    }

    public void increaseNumberOfDucksOnGround() {

        this.numberOfDucksOnGround++;

    }

    public void updateState() {

        if (bonus && (this.levelTime > BONUS_TIME)) {
            this.levelState = LevelState.NEXT_LEVEL;
        }

        else if (this.numberOfDucksOnGround == this.numberOfDucks) {
            this.levelState = LevelState.NEXT_LEVEL;
        }
        else if (this.playerModel.isOutOfBullets() && this.getNumberOfAliveDucks() > 0) {
            this.levelState = LevelState.GAME_OVER;
        }

    }

    public LevelState getLevelState() {
        return levelState;
    }

    public int getLevel() {
        return level;
    }

    public float getLevelTime() {
        return levelTime;
    }

    public void setLevelTime(float levelTime) {
        this.levelTime = levelTime;
    }

    public boolean isBonus() {
        return bonus;
    }

    public static int getBonusTime() {
        return BONUS_TIME;
    }
}
