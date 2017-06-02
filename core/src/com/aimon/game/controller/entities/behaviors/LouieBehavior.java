package com.aimon.game.controller.entities.behaviors;

import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.model.entities.DuckModel;
import com.badlogic.gdx.math.MathUtils;


/**
 *  LouieBehavior. Implementation of abstract class DuckBehaviour for Louie
 */

public class LouieBehavior extends DuckBehavior {

    /**
     * Instantiates a new louie behavior.
     *
     * @param duck the duck
     */
    public LouieBehavior(DuckBody duck){
        super(duck);
        ((DuckModel) this.duck.getModel()).setNormalVelocity(DuckModel.LOUIE_NORMAL_VELOCITY);
        ((DuckModel) this.duck.getModel()).setFrigthenVelocity(DuckModel.LOUIE_NORMAL_VELOCITY * DuckModel.LOUIE_FRIGHTEN_FACTOR);
        ((DuckModel) this.duck.getModel()).setDirection(DuckModel.DuckDirection.RIGHT);
        this.duck.changeVelocity(DuckModel.LOUIE_NORMAL_VELOCITY, -2);
    }


    /**
     * Update. Update the duck, changing direction and go up and down
     */
    @Override
    public void update(float delta) {

        if (!super.verifyLimits() && !((DuckModel) this.duck.getModel()).isFrightened()) {

            int rand = MathUtils.random(0,200);

            if(rand < 2) {
               duck.changeDirection();
            }

            if(rand < 10) {
                duck.goUp(5);
            }
            if(rand > 190) {
               duck.goDown(5);
            }

        }

    }
}
