package com.aimon.game.controller.entities.behaviors;

import com.aimon.game.controller.entities.DuckBody;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Leo on 03/05/2017.
 */

public class DeweyBehavior implements DuckBehavior {

    DuckBody duck;

    public DeweyBehavior(DuckBody duck){
        this.duck = duck;
    }

    @Override
    public void setDuck(DuckBody duck){
        this.duck = duck;
    }

    @Override
    public void update(float delta) {

        int rand = MathUtils.random(0,100);

        if(rand < 8) {
            duck.changeDirection();
        }

        if(rand < 10) {
            duck.goUp(1);
        }
        if(rand > 90) {
            duck.goDown(1);
        }
    }
}
