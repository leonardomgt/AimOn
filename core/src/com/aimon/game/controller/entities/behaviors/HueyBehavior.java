package com.aimon.game.controller.entities.behaviors;

import com.aimon.game.controller.entities.DuckBody;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Leo on 16/05/2017.
 */

public class HueyBehavior extends DuckBehavior {

    public HueyBehavior(DuckBody duck){
        super(duck);
        this.duck.changeVelocity(-4, 0);
    }


    @Override
    public void update(float delta) {

        if (!super.verifyLimits()) {
            int rand = MathUtils.random(0,100);

            if(rand < 8) {
                duck.changeDirection();
            }

            if(rand < 10) {
                duck.goUp(2);
            }
            if(rand > 90) {
               duck.goDown(2);
            }
        }


    }
}