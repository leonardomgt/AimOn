package com.aimon.game.model.entities;

import com.aimon.game.model.MainModel;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class DuckModel extends EntityModel{

    public enum DuckType {LOUIE, HUEY, DEWEY};
    public enum Life{DEAD, ALIVE};

    private DuckType type;

    private Life life;

    public DuckModel(float x, float y, float rotation, DuckType type) {

        super(x,y,rotation);
        this.type = type;
        this.life = Life.ALIVE;

    }

    public DuckType getType() {
        return type;
    }

    public void kill() {

        this.life = Life.DEAD;

    }

    public boolean isAlive() {
        return this.life == Life.ALIVE;
    }

}
