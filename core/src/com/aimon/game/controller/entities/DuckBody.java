package com.aimon.game.controller.entities;

import com.aimon.game.controller.MainController;
import com.aimon.game.controller.entities.behaviors.DeweyBehavior;
import com.aimon.game.controller.entities.behaviors.DuckBehavior;
import com.aimon.game.controller.entities.behaviors.HueyBehavior;
import com.aimon.game.controller.entities.behaviors.LouieBehavior;
import com.aimon.game.model.entities.DuckModel;
import com.badlogic.gdx.physics.box2d.World;

import static com.aimon.game.view.game.GameScreen.PIXEL_TO_METER;

/**
 * DuckBody. A duck body class that holds a body related to a duck model
 */

public class DuckBody extends EntityBody{

    /** The Constant THRESHOLD in which the duck floats */
    private static final float THRESHOLD = 0.1f;
    
    /** The Constant DUCK_MASS. */
    private static final float DUCK_MASS = 0.05f;
    
    /** The duck category. */
    private final short DUCK_CATEGORY = 0x0001;
    
    /** The ignore ducks. Used to avoid collisions among ducks */
    private final short IGNORE_DUCKS = ~DUCK_CATEGORY;
    
    /** The Constant TIME_STUNNED. The time that the body is stunned after being shot*/
    private static final float TIME_STUNNED = 0.5f;

    /** The behavior. An interface used to set the duck behaviour  */
    private DuckBehavior behavior;

    /** The width. */
    private float width;
    
    /** The height. */
    private float height;

    /**
     * Instantiates a new duck body.
     *
     * @param world the world
     * @param model the model
     */
    public DuckBody(World world, DuckModel model) {
        super(world,model);

        this.width = 114/3 * PIXEL_TO_METER * model.getDepthFactor();
        this.height = 38 * PIXEL_TO_METER * model.getDepthFactor();

        float density = DUCK_MASS / (float)Math.pow(this.width * this.height, 1.5f);

        float friction = 0.4f, restitution = .2f;
        int widthPixels = Math.round(this.width/PIXEL_TO_METER);
        int heightPixels = Math.round(this.height/PIXEL_TO_METER);

        createFixture(body, new float[]{
                0,0, widthPixels,0, widthPixels,heightPixels, 0,heightPixels
        }, widthPixels , heightPixels, density, friction, restitution, DUCK_CATEGORY, IGNORE_DUCKS);


        switch (model.getType()){
            case DEWEY:
                this.behavior = new DeweyBehavior(this);
                break;
            case HUEY:
                this.behavior = new HueyBehavior(this);
                break;
            case LOUIE:
                this.behavior = new LouieBehavior(this);
                break;
        }

    }

    /**
     * Update duck state. Increase lifetime, make sure that the duck is always in the world and switches among the state.
     *
     * If the duck is shot he will remain stunned for a while (SHOT state), then will fall (FALLING) and collide with the ground and die (DEAD)
     *
     * @param delta the delta
     */
    public void updateDuckState(float delta) {

        DuckModel dm = (DuckModel) this.model;
        dm.updateLifeTime(delta);

        if(dm.isFrightened()) {
            updateFrightenState();
        }

        switch (dm.getState()) {
            case GO_UP:
                if((dm.getY() > dm.getObjectiveY() + THRESHOLD) || (dm.getY() >= MainController.getControllerHeight() - this.height/2)) {
                    dm.setState(DuckModel.DuckState.FLOAT_DOWN);
                    this.applyVerticalForceToCenter(-4f);
                }
                break;

            case GO_DOWN:
                if(((DuckModel) this.model).getDirection() == DuckModel.DuckDirection.RIGHT) {
                    this.setRotation(-30);
                }
                else {
                    this.setRotation(30);
                }
                if((dm.getY() < dm.getObjectiveY() - THRESHOLD) || (dm.getY() <= MainController.getControllerGroundHeight() + this.height*+1)) {
                    dm.setState(DuckModel.DuckState.FLOAT_UP);
                    this.applyVerticalForceToCenter(4f);
                    this.setRotation(0);
                }
                break;
            case FLOAT_UP:
                if(dm.getY() > dm.getObjectiveY() + THRESHOLD) {
                    dm.setState(DuckModel.DuckState.FLOAT_DOWN);
                    this.applyVerticalForceToCenter(-4f);
                }
                break;
            case FLOAT_DOWN:
                if(dm.getY() < dm.getObjectiveY() - THRESHOLD) {
                    dm.setState(DuckModel.DuckState.FLOAT_UP);
                    this.applyVerticalForceToCenter(4f);
                }
                break;

            case SHOT:
                this.applyForceToCenter(0,0);

                if (dm.getLifeTime() - dm.getDeadMoment() >= TIME_STUNNED) {
                    dm.setState(DuckModel.DuckState.FALLING);
                }
                this.setRotation(0);
                break;

            case FALLING:
                this.applyForceToCenter(0,-20f);
                break;

        }
    }



    /**
     * Change direction. Changes horizontal direction of the duck
     */
    public void changeDirection() {

        DuckModel dm = (DuckModel) this.model;

        this.body.setLinearVelocity(-this.body.getLinearVelocity().x, this.body.getLinearVelocity().y);
        /*if(this.body.getLinearVelocity().x == 0) {
            this.body.setLinearVelocity(1,this.body.getLinearVelocity().y);
        }*/
        if(this.body.getLinearVelocity().x < 0) {
            dm.setDirection(DuckModel.DuckDirection.LEFT);
        }
        else if(this.body.getLinearVelocity().x > 0) {
            dm.setDirection(DuckModel.DuckDirection.RIGHT);
        }

    }



    /**
     * Go up. Send the duck up a given amount of meters
     *
     * @param height the height
     */
    public void goUp(float height) {

        DuckModel dm = (DuckModel) this.model;

        if(dm.getState() != DuckModel.DuckState.GO_UP && dm.getState() != DuckModel.DuckState.GO_DOWN && dm.getState() != DuckModel.DuckState.FALLING) {
            dm.setState(DuckModel.DuckState.GO_UP);
            this.applyVerticalForceToCenter(8f);
            dm.setObjectiveY(dm.getY() + height);
        }

    }

    /**
     * Go down. Send the duck down a given amount of meters
     *
     * @param height the height
     */
    public void goDown(float height) {

        DuckModel dm = (DuckModel) this.model;
        if(dm.getState() != DuckModel.DuckState.GO_DOWN && dm.getState() != DuckModel.DuckState.GO_UP && dm.getState() != DuckModel.DuckState.FALLING) {
            dm.setState(DuckModel.DuckState.GO_DOWN);

            this.applyVerticalForceToCenter(-10f);
            dm.setObjectiveY(dm.getY() - height);
        }

    }


    /**
     * Gets the behavior.
     *
     * @return the behavior of the duck
     */
    public DuckBehavior getBehavior() {
        return behavior;
    }

    /**
     * Change velocity.
     *
     * @param x the x velocity
     * @param y the y velovity
     */
    public void changeVelocity(float x, float y){

        float depthFactor = ((DuckModel) model).getDepthFactor();
        this.body.setLinearVelocity(x, y);
        this.body.setLinearVelocity(this.body.getLinearVelocity().x*depthFactor, this.body.getLinearVelocity().y*depthFactor);

    }


    /**
     * Checks if a position is in the range of the duck
     *
     * @param x the x of the position
     * @param y the y of the position
     * @return true, if is in range
     */
    public boolean isInRange(float x, float y) {

        if(x > this.getX() - this.width/2f && x < this.getX() + this.width/2f){

            if(y > this.getY() - this.height/2f && y < this.getY() + this.height/2f){

                return true;
            }
        }

        return false;
    }

    /**
     * Set Frighten. Frightens the duck, causing the duck to change direction if it was flying towards the shot position and increasing the speed for
     * a while (class static field)
     *
     * @param x x position of the shot
     */
    public void setFrighten(float x) {

        DuckModel duckModel = (DuckModel)this.model;

        if ((duckModel.getX() < x && duckModel.getDirection() == DuckModel.DuckDirection.RIGHT) || (duckModel.getX() > x && duckModel.getDirection() == DuckModel.DuckDirection.LEFT) ) {
            this.changeDirection();
        }

        if(!duckModel.isFrightened()) {

            if (duckModel.getDirection() == DuckModel.DuckDirection.RIGHT) {
                this.changeVelocity(duckModel.getFrigthenVelocity(), body.getLinearVelocity().y);
            }
            else {
                this.changeVelocity(-duckModel.getFrigthenVelocity(), body.getLinearVelocity().y);
            }

        }

        duckModel.setFrightened(true);
        duckModel.setFrightenedMoment(duckModel.getLifeTime());

    }

    /**
     * Update frighten state.
     */
    private void updateFrightenState() {

        DuckModel dm = (DuckModel) this.model;
        if (dm.getLifeTime() - dm.getFrightenedMoment() > DuckModel.getFrightenTime()) {

            if (dm.getDirection() == DuckModel.DuckDirection.RIGHT) {
                this.changeVelocity(dm.getNormalVelocity(), body.getLinearVelocity().y);
            }
            else {
                this.changeVelocity(-dm.getNormalVelocity(), body.getLinearVelocity().y);
            }

            dm.setFrightened(false);

        }
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * Gets the  time stunned.
     *
     * @return the  time stunned
     */
    public static float get_TIME_STUNNED() {
        return TIME_STUNNED;
    }

    /**
     * Gets the threshold.
     *
     * @return the threshold
     */
    public static float getTHRESHOLD() {
        return THRESHOLD;
    }
}