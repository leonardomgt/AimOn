package com.aimon.game.controller.entities;

import com.aimon.game.model.entities.EntityModel;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.aimon.game.view.game.GameScreen.PIXEL_TO_METER;

// TODO: Auto-generated Javadoc
/**
 * Created by joaofurriel on 29/04/17.
 */

public abstract class EntityBody {

    /** The body. */
    protected final Body body;
    
    /** The model. */
    protected EntityModel model;

    /**
     * Instantiates a new entity body.
     *
     * @param world the world
     * @param model the model
     */
    EntityBody(World world, EntityModel model) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(model.getX(), model.getY());
        bodyDef.angle = model.getRotation();

        body = world.createBody(bodyDef);
        body.setUserData(model);

        this.model = model;

    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public float getX() {
        return this.body.getPosition().x;
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public float getY() {
        return this.body.getPosition().y;
    }

    /**
     * Sets the transform.
     *
     * @param x the x
     * @param y the y
     * @param angle the angle
     */
    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    /**
     * Apply force to center.
     *
     * @param forceX the force X
     * @param forceY the force Y
     */
    public void applyForceToCenter(float forceX, float forceY) {
        body.setLinearVelocity(0,0);
        body.applyForceToCenter(forceX, forceY, true);
    }

    /**
     * Apply vertical force to center.
     *
     * @param force the force
     */
    public void applyVerticalForceToCenter(float force) {
        body.setLinearVelocity(this.body.getLinearVelocity().x,0);
        body.applyForceToCenter(0, force, true);
    }

    /**
     * Sets the rotation.
     *
     * @param degrees the new rotation
     */
    public void setRotation(float degrees) {
        this.model.setRotation(degrees * MathUtils.PI/180);
        this.body.setTransform(this.body.getPosition().x, this.body.getPosition().y, this.model.getRotation());
    }

    /**
     * Gets the user data.
     *
     * @return the user data
     */
    public Object getUserData() {
        return body.getUserData();
    }

    /**
     * Creates the fixture.
     *
     * @param body the body
     * @param vertexes the vertexes
     * @param width the width
     * @param height the height
     * @param density the density
     * @param friction the friction
     * @param restitution the restitution
     * @param category the category
     * @param ignoreCategories the ignore categories
     */
    final void createFixture(Body body, float[] vertexes, int width, int height, float density, float friction, float restitution, short category, short ignoreCategories) {
        // Transform pixels into meters, center and invert the y-coordinate
        for (int i = 0; i < vertexes.length; i++) {

            if (i % 2 == 0) vertexes[i] -= width / 2;   // center the vertex x-coordinate
            if (i % 2 != 0) vertexes[i] -= height / 2;  // center the vertex y-coordinate

            if (i % 2 != 0) vertexes[i] *= -1;          // invert the y-coordinate

            vertexes[i] *= PIXEL_TO_METER;              // scale from pixel to meter
        }

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertexes);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = ignoreCategories;

        body.createFixture(fixtureDef);

        polygon.dispose();
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    public Body getBody() {
        return body;
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public EntityModel getModel() {
        return model;
    }
}


