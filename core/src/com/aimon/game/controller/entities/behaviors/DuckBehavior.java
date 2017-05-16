package com.aimon.game.controller.entities.behaviors;

import com.aimon.game.controller.entities.DuckBody;

/**
 * Created by Leo on 03/05/2017.
 */

public interface DuckBehavior {

    void update(float delta);
    void setDuck(DuckBody duck);
}
