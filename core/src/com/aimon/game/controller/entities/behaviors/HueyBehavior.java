package com.aimon.game.controller.entities.behaviors;

import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.model.entities.DuckModel;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Leo on 16/05/2017.
 */

public class HueyBehavior extends DuckBehavior {

    public HueyBehavior(DuckBody duck){
        super(duck);
        ((DuckModel) this.duck.getModel()).setNormalVelocity(DuckModel.HUEY_NORMAL_VELOCITY);
        ((DuckModel) this.duck.getModel()).setFrigthenVelocity(DuckModel.HUEY_NORMAL_VELOCITY * DuckModel.HUEY_FRIGHTEN_FACTOR);
        ((DuckModel) this.duck.getModel()).setDirection(DuckModel.DuckDirection.RIGHT);
        this.duck.changeVelocity(DuckModel.HUEY_NORMAL_VELOCITY, -2);
    }


    @Override
    public void update(float delta) {

        if (!super.verifyLimits() && !((DuckModel) this.duck.getModel()).isFrightened()) {
            int rand = MathUtils.random(0,200);

            if(rand < 2) {
                duck.changeDirection();
            }

            if(rand < 10) {
                duck.goUp(2);
            }
            if(rand > 190) {
               duck.goDown(2);
            }
        }


    }
}
