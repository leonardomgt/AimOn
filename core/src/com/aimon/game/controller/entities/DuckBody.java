package com.aimon.game.controller.entities;

import com.aimon.game.model.entities.DuckModel;
import com.badlogic.gdx.physics.box2d.World;

import static com.aimon.game.view.game.GameScreen.PIXEL_TO_METER;

/**
 * Created by joaofurriel on 29/04/17.
 */

public class DuckBody extends EntityBody{

    private static final float THRESHOLD = 0.1f;
    private final short DUCK_CATEGORY = 0x0001;
    private final short IGNORE_DUCKS = ~DUCK_CATEGORY;


    private float mass;
    private float width;
    private float height;
    private float lastImpulse = 0;

    public DuckBody(World world, DuckModel model) {
        super(world,model);
        float density = 0.2f, friction = 0.4f, restitution = .2f;
        int width = 114/3, height = 153/4;

        createFixture(body, new float[]{
                0,0, width,0, width,height, 0,height
        }, width, height, density, friction, restitution, DUCK_CATEGORY, IGNORE_DUCKS);

        this.mass = (float)114/3 * PIXEL_TO_METER * (float)153/4 * PIXEL_TO_METER * density;
        this.width = 114/3 * PIXEL_TO_METER;
        this.width = 153/4 * PIXEL_TO_METER;

        this.body.setLinearVelocity(-2,0);
        model.setDirection(DuckModel.DuckDirection.LEFT);

    }

    public void updateDuckState() {

        DuckModel dm = (DuckModel) this.model;
        switch (dm.getState()) {
            case GO_UP:
                if(dm.getY() > dm.getObjectiveY() + THRESHOLD) {
                    dm.setState(DuckModel.DuckState.FLOAT_DOWN);
                    this.applyVerticalForceToCenter(-3f);
                }
                break;

            case GO_DOWN:
                if(dm.getY() < dm.getObjectiveY() - THRESHOLD) {
                    dm.setState(DuckModel.DuckState.FLOAT_UP);
                    this.applyVerticalForceToCenter(3f);
                    this.setRotation(0);
                }
                break;
            case FLOAT_UP:
                if(dm.getY() > dm.getObjectiveY() + THRESHOLD) {
                    dm.setState(DuckModel.DuckState.FLOAT_DOWN);
                    this.applyVerticalForceToCenter(-3f);
                }
                break;
            case FLOAT_DOWN:
                if(dm.getY() < dm.getObjectiveY() - THRESHOLD) {
                    dm.setState(DuckModel.DuckState.FLOAT_UP);
                    this.applyVerticalForceToCenter(3f);
                }
                break;

        }

    }

    public void changeDirection() {

        DuckModel dm = (DuckModel) this.model;
        if(dm.getState() != DuckModel.DuckState.GO_DOWN && dm.getState() != DuckModel.DuckState.GO_UP) {
            this.body.setLinearVelocity(-this.body.getLinearVelocity().x, this.body.getLinearVelocity().y);
            if(this.body.getLinearVelocity().x == 0) {
                this.body.setLinearVelocity(1,this.body.getLinearVelocity().y);
            }
            if(this.body.getLinearVelocity().x < 0) {
                dm.setDirection(DuckModel.DuckDirection.LEFT);
            }
            else if(this.body.getLinearVelocity().x > 0) {
                dm.setDirection(DuckModel.DuckDirection.RIGHT);
            }
        }
    }

    public void goUp(float height) {

        DuckModel dm = (DuckModel) this.model;

        if(dm.getState() != DuckModel.DuckState.GO_UP && dm.getState() != DuckModel.DuckState.GO_DOWN) {
            dm.setState(DuckModel.DuckState.GO_UP);
            this.applyVerticalForceToCenter(8f);
            dm.setObjectiveY(dm.getY() + height);
        }

    }

    public void goDown(float height) {

        DuckModel dm = (DuckModel) this.model;
        if(dm.getState() != DuckModel.DuckState.GO_DOWN && dm.getState() != DuckModel.DuckState.GO_UP) {
            dm.setState(DuckModel.DuckState.GO_DOWN);
            if(((DuckModel) this.model).getDirection() == DuckModel.DuckDirection.RIGHT) {
                this.setRotation(-90);
            }
            else {
                this.setRotation(90);
            }

            this.applyVerticalForceToCenter(-10f);
            dm.setObjectiveY(dm.getY() - height);
        }

    }



}
