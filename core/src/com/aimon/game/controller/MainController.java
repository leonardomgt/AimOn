package com.aimon.game.controller;

import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.controller.entities.GroundBody;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.EntityModel;
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

/**
 * Created by joaofurriel on 28/04/17.
 */

public class MainController {

    //TODO: Temp
    private boolean tmpFlag = false;
    private float totalTimeTmp = 0;


    /*private static final int FIELD_HEIGHT = 18;

    private static final int FIELD_WIDTH = 32;*/

    private static final int FIELD_HEIGHT = 30;

    private static final int FIELD_WIDTH = 50;

    private final World world;

    private float accumulator;

    private MainModel model;
    private List<DuckBody> duckBodies;

    public MainController(MainModel model) {

        this.model = model;
        this.world = new World(new Vector2(0,0), true);
        List<DuckModel> ducks = model.getDucks();
        duckBodies = new ArrayList<DuckBody>();

        this.world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();

                char body = bodyA.getUserData() instanceof  DuckModel ? 'a' : 'b';
                DuckModel duckModel;
                Body duckBody;

                if (body == 'a') {
                    duckModel = (DuckModel) bodyA.getUserData();
                    duckBody = bodyA;

                }
                else {
                    duckModel = (DuckModel) bodyB.getUserData();
                    duckBody = bodyB;
                }

                duckModel.setState(DuckModel.DuckState.DEAD);


            }

            @Override
            public void endContact(Contact contact) {}

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

    public World getWorld() {
        return world;
    }

    public void update(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        totalTimeTmp += delta;

        for (DuckBody duck : duckBodies) {
            DuckModel model = (DuckModel) duck.getModel();

            if (model.getState() != DuckModel.DuckState.DEAD) {

                duck.updateDuckState(delta);

                if(model.isAlive()){
                    duck.getBehavior().update(delta);
                }

            }

            else {

                duck.changeVelocity(0,0);
                duck.setRotation(-90);

            }

        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {

            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());
        }


    }

    public static int getControllerWidth() {
        return MainController.FIELD_WIDTH;
    }

    public static int getControllerHeight() {
        return MainController.FIELD_HEIGHT;
    }

    public void updateAimLocation(float x, float y){
        model.getAim().setPosition(x, y);
    }

    public void shotFired(float x, float y) {

        for (DuckBody duck : duckBodies) {
            DuckModel model = (DuckModel) duck.getModel();

            if (duck.isInRange(x, y)) {
                model.kill();
            }
        }
    }
}
