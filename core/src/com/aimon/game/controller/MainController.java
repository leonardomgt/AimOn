package com.aimon.game.controller;

import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.controller.entities.GroundBody;
import com.aimon.game.controller.entities.PlayerController;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.EntityModel;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * MainController class. Used to update the game model and its entities upon updates of the View (GameScreen)
 */

public class MainController {

    /** FIELD_WIDTH. Used to control the action width of the ducks. */
    private static final float FIELD_WIDTH = 25;

    /** The Constant GROUND_HEIGHT. Used to control the action width of the ducks. */
    private static float FIELD_HEIGHT;
    
    /** The Constant GROUND_HEIGHT. Used to build the ground body */
    private static final float GROUND_HEIGHT = 1f;

    /** The world. Used to hold the bodies and apply physics */
    private final World world;
    
    /** The accumulator. Variable to control the world steps*/
    private float accumulator;

    /** The model. The model of the game, used to store the game data. The M of the MVC*/
    private MainModel model;
    
    /** The duck bodies. A list with all the duck bodies living in the world */
    private List<DuckBody> duckBodies;
    
    /** The player controller. A specific controller to manage the player*/
    private PlayerController playerController;

    /**
     * Instantiates a new main controller. This method also creates a contact listener between the ground and the duck bodies which when triggered will
     * increasing the number of
     * ducks dead on the ground, setting the duck as dead
     *
     * Creates also the duck bodies and the player controller instance as long as call the singleton ground body
     *
     * @param model the model of the game
     * @param ratio the ratio between the screen height and the screen width
     */
    public MainController(MainModel model, float ratio) {

        MainController.FIELD_HEIGHT = FIELD_WIDTH * ratio;
        this.model = model;
        this.world = new World(new Vector2(0,0), true);
        List<DuckModel> ducks = model.getDucks();
        duckBodies = new ArrayList<DuckBody>();
        this.playerController = new PlayerController(this.model.getPlayerModel());

        this.world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();
                DuckModel duckModel = bodyA.getUserData() instanceof  DuckModel ? (DuckModel) bodyA.getUserData() : (DuckModel) bodyB.getUserData();
                duckModel.setState(DuckModel.DuckState.DEAD);
                MainController.this.model.increaseNumberOfDucksOnGround();


            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });

        for (DuckModel duck : ducks) {
            duckBodies.add(new DuckBody(world, duck));
        }
        GroundBody.getInstance(this.world, model.getGround());

    }

    /**
     * Update the controller. The is the main method of the class. It is responsible for updating all the status of the game based on the time that has
     * passed since the last update (delta parameter)
     *
     * @param delta time since the last update
     */
    public void update(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        for (DuckBody duck : duckBodies) {
            DuckModel model = (DuckModel) duck.getModel();

            if (model.getState() != DuckModel.DuckState.DEAD) {

                if(model.isAlive()){
                    duck.getBehavior().update(delta);

                }

                duck.updateDuckState(delta);

            }

            else {

                duck.changeVelocity(0,0);
                duck.setRotation(90);

            }

        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {

            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());
        }

        this.model.setLevelTime(this.model.getLevelTime() + delta);
        this.playerController.getGunController().updateStatus(delta);
        this.model.updateState();

    }

    /**
     * Gets the controller width.
     *
     * @return the controller width
     */
    public static float getControllerWidth() {
        return MainController.FIELD_WIDTH;
    }

    /**
     * Gets the controller height.
     *
     * @return the controller height
     */
    public static float getControllerHeight() {
        return MainController.FIELD_HEIGHT;
    }

    /**
     * Gets the controller ground height.
     *
     * @return the controller ground height
     */
    public static float getControllerGroundHeight() {
        return MainController.GROUND_HEIGHT;
    }

    /**
     * Update aim location.
     *
     * @param x the x
     * @param y the y
     */
    public void updateAimLocation(float x, float y){
        model.getAim().setPosition(x, y);
    }

    /**
     * Fire gun. This method will fire try to fire the player gun. If it is possible will also check if the player has killed ducks if they are alive
     * This method will update the model, decreasing the total number of ducks by the number of killed ducks.
     *
     * It also checks if the shot was near of ducks, frighting them
     *
     * At last, it will tell the controller if the shot was good (if it killed any ducks) or not.
     *
     * @param x the x position of the shot
     * @param y the y position of the shot
     * @return true, if gun was fired, false otherwise
     */
    public boolean fireGun(float x, float y) {

        if (this.playerController.fireGun()) {

            int numberOfKilledDucks = 0;
            for (DuckBody duck : duckBodies) {
                DuckModel model = (DuckModel) duck.getModel();

                if (duck.isInRange(x, y) && model.isAlive()) {

                    model.kill();
                    this.model.decreaseNumberOfAliveDucks();
                    numberOfKilledDucks++;
                }
                else {
                    this.frightenDuck(x, y, duck);
                }

            }
            this.playerController.updateScore(numberOfKilledDucks);
            return true;
        }
        return false;
    }

    /**
     * Reload gun. Call the reloadGun method of the player controller
     *
     * @return the number of bullets reloaded
     */
    public int reloadGun() {
        return this.playerController.reloadGun();
    }

    /**
     * Frighten duck. This method will frighten the duck passed on the parameter, setting that duck frighten boolean to true
     *
     * @param x the x of the shot
     * @param y the y of the shot
     * @param duckBody the duck body
     */
    private void frightenDuck(float x, float y, DuckBody duckBody) {

        if(distance(x,y,duckBody.getX(), duckBody.getY()) < DuckModel.getFrightenDistance()) {
            duckBody.setFrighten(x);
        }

    }

    /**
     * Distance. Calculates the cartesian distance between two points on the plane
     *
     * @param x1 the x position of the first point
     * @param y1 the y position of the first point
     * @param x2 the x position of the second point
     * @param y2 the y position of the second point
     * @return the distance between the points
     */
    private static float distance(float x1, float y1, float x2, float y2) {

        return (float) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1,2));
    }

    /**
     * Gets the duck bodies.
     *
     * @return the duck bodies
     */
    public List<DuckBody> getDuckBodies() {
        return duckBodies;
    }

    /**
     * Gets the player controller.
     *
     * @return the player controller
     */
    public PlayerController getPlayerController() {
        return playerController;
    }
}
