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

// TODO: Auto-generated Javadoc
/**
 * Created by joaofurriel on 28/04/17.
 */

public class MainModel {

    /** The Constant DUCKS_MIN_DEPTH. */
    public static final float DUCKS_MIN_DEPTH = 7.5f;
    
    /** The Constant DUCKS_MAX_DEPTH. */
    public static final float DUCKS_MAX_DEPTH = 15f;
    
    /** The Constant BONUS_TIME. */
    private static final int BONUS_TIME = 15;

    /**
     * The Enum LevelState.
     */
    public enum LevelState {/** The running. */
RUNNING, /** The game over. */
 GAME_OVER, /** The next level. */
 NEXT_LEVEL}

    /** The ducks. */
    private List<DuckModel> ducks;
    
    /** The aim. */
    private AimModel aim;
    
    /** The ground. */
    private GroundModel ground;
    
    /** The number of ducks. */
    private final int numberOfDucks;
    
    /** The number of alive ducks. */
    private int numberOfAliveDucks;
    
    /** The number of ducks on ground. */
    private int numberOfDucksOnGround;
    
    /** The player model. */
    private PlayerModel playerModel;
    
    /** The level state. */
    private LevelState levelState = LevelState.RUNNING;
    
    /** The level. */
    private int level;
    
    /** The bonus. */
    private boolean bonus = false;
    
    /** The level time. */
    private float levelTime = 0;

    /**
     * Instantiates a new main model.
     *
     * @param aimX the aim X
     * @param aimY the aim Y
     * @param numberOfDucks the number of ducks
     * @param playerModel the player model
     * @param level the level
     * @param ratio the ratio
     */
    public MainModel(float aimX, float aimY, int numberOfDucks, PlayerModel playerModel, int level, float ratio) {

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
            float frightenedFactor;
            switch (t) {
                case 0:
                    type = DuckModel.DuckType.DEWEY;
                    frightenedFactor = DuckModel.DEWEY_FRIGHTEN_FACTOR;
                    break;
                case 1:
                    type = DuckModel.DuckType.LOUIE;
                    frightenedFactor = DuckModel.LOUIE_FRIGHTEN_FACTOR;
                    break;
                default:
                    type = DuckModel.DuckType.HUEY;
                    frightenedFactor = DuckModel.HUEY_FRIGHTEN_FACTOR;
                    break;

            }
            t = random.nextInt(3);
            float fieldHeight = MainController.getControllerWidth() * ratio;
            float x;
            float y = MathUtils.random(MainController.getControllerGroundHeight() + 5f, fieldHeight);
            switch (t) {
                case 0: //nasce à esquerda
                    x = 0f;
                    break;
                case 1: //nasce à direita
                    x = MainController.getControllerWidth();
                    break;
                default:
                    x = MathUtils.random(0, MainController.getControllerWidth());
                    y = fieldHeight;
            }

            DuckModel currentDuck = new DuckModel(x,y, 0, type, MathUtils.random(DUCKS_MIN_DEPTH,DUCKS_MAX_DEPTH), frightenedFactor);


            this.ducks.add(currentDuck);
            this.ground = GroundModel.getInstance();

        }
        Collections.sort(this.ducks);

    }

    /**
     * Gets the number of ducks.
     *
     * @return the number of ducks
     */
    public int getNumberOfDucks() {
        return numberOfDucks;
    }

    /**
     * Gets the number of alive ducks.
     *
     * @return the number of alive ducks
     */
    public int getNumberOfAliveDucks() {
        return numberOfAliveDucks;
    }

    /**
     * Gets the ducks.
     *
     * @return the ducks
     */
    public List<DuckModel> getDucks() {
        return this.ducks;
    }

    /**
     * Gets the aim.
     *
     * @return the aim
     */
    public AimModel getAim() { return aim; }

    /**
     * Gets the ground.
     *
     * @return the ground
     */
    public GroundModel getGround() { return this.ground;}

    /**
     * Gets the player model.
     *
     * @return the player model
     */
    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    /**
     * Decrease number of ducks.
     */
    public void decreaseNumberOfDucks() {
        this.numberOfAliveDucks--;
    }

    /**
     * Increase number of ducks on ground.
     */
    public void increaseNumberOfDucksOnGround() {

        this.numberOfDucksOnGround++;

    }

    /**
     * Update state.
     */
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

    /**
     * Gets the level state.
     *
     * @return the level state
     */
    public LevelState getLevelState() {
        return levelState;
    }

    /**
     * Gets the level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the level time.
     *
     * @return the level time
     */
    public float getLevelTime() {
        return levelTime;
    }

    /**
     * Sets the level time.
     *
     * @param levelTime the new level time
     */
    public void setLevelTime(float levelTime) {
        this.levelTime = levelTime;
    }

    /**
     * Checks if is bonus.
     *
     * @return true, if is bonus
     */
    public boolean isBonus() {
        return bonus;
    }

    /**
     * Gets the bonus time.
     *
     * @return the bonus time
     */
    public static int getBonusTime() {
        return BONUS_TIME;
    }

    /**
     * Gets the number of ducks on ground.
     *
     * @return the number of ducks on ground
     */
    public int getNumberOfDucksOnGround() {
        return numberOfDucksOnGround;
    }
}
