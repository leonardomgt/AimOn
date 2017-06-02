package com.aimon.game.controller;

import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.controller.entities.GroundBody;
import com.aimon.game.controller.entities.GunController;
import com.aimon.game.controller.entities.PlayerController;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.EntityModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
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

// TODO: Auto-generated Javadoc
/**
 * Created by joaofurriel on 28/04/17.
 */

public class MainController {

    /** The Constant FIELD_WIDTH. */
    private static final float FIELD_WIDTH = 25;
    
    /** The Constant GROUND_HEIGHT. */
    //private static final float FIELD_HEIGHT = FIELD_WIDTH*((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
    private static final float GROUND_HEIGHT = 1f;
    
    /** The field height. */
    private static float FIELD_HEIGHT;


    /** The world. */
    private final World world;
    
    /** The accumulator. */
    private float accumulator;

    /** The model. */
    private MainModel model;
    
    /** The duck bodies. */
    private List<DuckBody> duckBodies;
    
    /** The player controller. */
    private PlayerController playerController;

    /**
     * Instantiates a new main controller.
     *
     * @param model the model
     * @param ratio the ratio
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

    /*public World getWorld() {
        return world;
    }*/

    /**
     * Update.
     *
     * @param delta the delta
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
     * Fire gun.
     *
     * @param x the x
     * @param y the y
     * @return true, if successful
     */
    public boolean fireGun(float x, float y) {

        if (this.playerController.fireGun()) {

            boolean goodShot = false;
            for (DuckBody duck : duckBodies) {
                DuckModel model = (DuckModel) duck.getModel();

                if (duck.isInRange(x, y) && model.isAlive()) {

                    model.kill();
                    this.model.decreaseNumberOfDucks();
                    goodShot = true;
                }
                else {
                    this.frightenDuck(x, y, duck);
                }

            }
            this.playerController.goodShot(goodShot);
            return true;
        }
        return false;
    }

    /**
     * Reload gun.
     *
     * @return the int
     */
    public int reloadGun() {
        return this.playerController.reloadGun();
    }

    /**
     * Frighten duck.
     *
     * @param x the x
     * @param y the y
     * @param duckBody the duck body
     */
    private void frightenDuck(float x, float y, DuckBody duckBody) {

        if(distance(x,y,duckBody.getX(), duckBody.getY()) < DuckModel.getFrightenDistance()) {
            duckBody.setFrighten(x);
        }

    }

    /**
     * Distance.
     *
     * @param x1 the x 1
     * @param y1 the y 1
     * @param x2 the x 2
     * @param y2 the y 2
     * @return the float
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
