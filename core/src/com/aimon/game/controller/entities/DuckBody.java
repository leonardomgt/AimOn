package com.aimon.game.controller.entities;

import com.aimon.game.model.entities.DuckModel;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by joaofurriel on 29/04/17.
 */

public class DuckBody extends EntityBody{

    public DuckBody(World world, DuckModel model) {
        super(world,model);

        float density = 0.2f, friction = 0.4f, restitution = .2f;
        int width = 114/3, height = 153/4;

        createFixture(body, new float[]{
                0,0, width,0, width,height, 0,height
        }, width, height, density, friction, restitution);

    }

    public void fly() {

        this.body.applyForceToCenter(0,0.98f,true);

    }
}
