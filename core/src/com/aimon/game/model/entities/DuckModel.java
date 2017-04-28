package com.aimon.game.model.entities;

import com.aimon.game.model.MainModel;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class DuckModel extends EntityModel{

    public enum DuckType {LOUIE, HUEY, DEWEY};

    private DuckType type;

    public DuckModel(float x, float y, float rotation, DuckType type) {

        super(x,y,rotation);
        this.type = type;

    }

    public DuckType getType() {
        return type;
    }
}
