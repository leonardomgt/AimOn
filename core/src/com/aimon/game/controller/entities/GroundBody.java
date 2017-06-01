package com.aimon.game.controller.entities;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.entities.GroundModel;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.aimon.game.view.game.GameScreen.PIXEL_TO_METER;

/**
 * Created by joaofurriel on 29/04/17.
 */

public class GroundBody extends EntityBody {

    private static GroundBody instance;
    private final short GROUND_CATEGORY = 0x0002;
    private final short COLLIDE_EVERYTHING = 0xFF;


    public static GroundBody getInstance(World world, GroundModel model) {

        return GroundBody.instance == null ? new GroundBody(world, model) : GroundBody.instance;
    }

    private GroundBody(World world, GroundModel groundModel) {

        super(world,groundModel);
        this.body.setType(BodyDef.BodyType.StaticBody);

        float groundWidth = MainController.getControllerWidth() +10 ;
        float groundHeight = MainController.getControllerGroundHeight();

        EdgeShape groundShape = new EdgeShape();
        groundShape.set(-5.0f, groundHeight, groundWidth, groundHeight);
        FixtureDef groundFixture = new FixtureDef();

        groundFixture.shape = groundShape;

        groundFixture.density = 0.0f;
        groundFixture.friction = 5.0f;
        groundFixture.restitution = 0.0f;
        groundFixture.filter.categoryBits = GROUND_CATEGORY;
        groundFixture.filter.maskBits = COLLIDE_EVERYTHING;

        this.body.createFixture(groundFixture);

    }
}
