package com.aimon.game.controller;

import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.controller.entities.GroundBody;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.EntityModel;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class MainController {

    private static final int FIELD_HEIGHT = 200;

    private static final int FIELD_WIDTH = 400;

    private final World world;

    private float accumulator;

    private List<DuckBody> duckBodies;

    public MainController(MainModel model) {

        this.world = new World(new Vector2(0,-10), true);
        List<DuckModel> ducks = model.getDucks();
        duckBodies = new ArrayList<DuckBody>();
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
            for (DuckBody duck : duckBodies) {

                DuckModel model = (DuckModel) duck.getModel();
                if(model.isAlive()) {
                    duck.fly();
                }

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
}
