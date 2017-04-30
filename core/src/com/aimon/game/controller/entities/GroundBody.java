package com.aimon.game.controller.entities;

import com.aimon.game.model.entities.GroundModel;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by joaofurriel on 29/04/17.
 */

public class GroundBody extends EntityBody {

    private static GroundBody instance;

    public static GroundBody getInstance(World world, GroundModel model) {

        return GroundBody.instance == null ? new GroundBody(world, model) : GroundBody.instance;
    }

    private GroundBody(World world, GroundModel groundModel) {

        super(world,groundModel);
        this.body.setType(BodyDef.BodyType.StaticBody);
        createFixture(body, new float[]{0,0, 2896,0, 2896,115, 0,115}, 2896 , 115 , 0.0f, 0.0f,0.0f);

    }
}
