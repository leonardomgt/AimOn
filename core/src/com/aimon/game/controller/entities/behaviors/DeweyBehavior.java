package com.aimon.game.controller.entities.behaviors;

import com.aimon.game.controller.MainController;
import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.model.entities.DuckModel;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Leo on 03/05/2017.
 */

public class DeweyBehavior extends DuckBehavior {

    public DeweyBehavior(DuckBody duck){
        super(duck);
        ((DuckModel) this.duck.getModel()).setNormalVelocity(DuckModel.DEWEY_NORMAL_VELOCITY);
        ((DuckModel) this.duck.getModel()).setFrigthenVelocity(DuckModel.DEWEY_NORMAL_VELOCITY * DuckModel.DEWEY_FRIGHTEN_FACTOR);
        ((DuckModel) this.duck.getModel()).setDirection(DuckModel.DuckDirection.RIGHT);
        this.duck.changeVelocity(DuckModel.DEWEY_NORMAL_VELOCITY, -2);
    }


    @Override
    public void update(float delta) {

        if (!super.verifyLimits() && !((DuckModel) this.duck.getModel()).isFrightened()) {

            int rand = MathUtils.random(0,200);

            if(rand < 2) {
                duck.changeDirection();
            }

            if(rand < 10) {
                duck.goUp(1);
            }
            if(rand > 190) {
                duck.goDown(1);
            }

        }

    }
}
