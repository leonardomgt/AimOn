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
 * Ground Body. Singleton Class responsible to create a body for the ground
 */

public class GroundBody extends EntityBody {

    /** The singleton. */
    private static GroundBody instance;
    
    /** The ground category. Used in collisions*/
    private final short GROUND_CATEGORY = 0x0002;
    
    /** The collide everything. Used to put the ground colliding with every other category of bodies  */
    private final short COLLIDE_EVERYTHING = 0xFF;


    /**
     * Gets the single instance of GroundBody.
     *
     * @param world the world
     * @param model the model
     * @return single instance of GroundBody
     */
    public static GroundBody getInstance(World world, GroundModel model) {

        return GroundBody.instance == null ? new GroundBody(world, model) : GroundBody.instance;
    }

    /**
     * Instantiates a new ground body which is a single edge which spans all the world width.
     *
     * @param world the world
     * @param groundModel the ground model
     */
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
