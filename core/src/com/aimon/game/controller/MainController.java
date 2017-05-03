package com.aimon.game.controller;

import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.controller.entities.GroundBody;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.EntityModel;
import com.badlogic.gdx.math.MathUtils;
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


    private final short CATEGORY_GROUND = 0x0002;

    private boolean tmpFlag = true;

    private static final int FIELD_HEIGHT = 100;

    private static final int FIELD_WIDTH = 200;

    private final World world;

    private float accumulator;

    private MainModel model;
    private List<DuckBody> duckBodies;

    public MainController(MainModel model) {

        this.model = model;
        this.world = new World(new Vector2(0,0), true);
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
        }

        for (DuckBody duck : duckBodies) {
            DuckModel model = (DuckModel) duck.getModel();
            if(model.isAlive()) {
                duck.updateDuckState();

                int rand = MathUtils.random(0,100);

                    if(rand < 8) {
                        duck.changeDirection();
                    }

                    if(rand < 10) {
                        duck.goUp(1);
                    }
                    if(rand > 90) {
                        duck.goDown(1);
                    }

            }

            if (tmpFlag) {
                //duck.goUp(3.0f);
                this.tmpFlag = false;
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
}
