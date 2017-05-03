package com.aimon.game.model.entities;

import com.aimon.game.model.MainModel;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class DuckModel extends EntityModel{

    public enum DuckDirection {UP, RIGHT, LEFT, DOWN};

    public enum DuckState{GO_UP, GO_DOWN, FLOAT_UP, FLOAT_DOWN, DEAD}

    public enum DuckType {LOUIE, HUEY, DEWEY};
    public enum Life{DEAD, ALIVE};
    private DuckType type;
    private Life life;
    private DuckDirection direction;
    private float objectiveY;
    protected DuckState state;


    public DuckModel(float x, float y, float rotation, DuckType type) {

        super(x,y,rotation);
        this.type = type;
        this.life = Life.ALIVE;
        this.direction = DuckDirection.RIGHT;
        this.objectiveY = y;
        this.state = DuckState.FLOAT_UP;

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

    public DuckDirection getDirection() {
        return direction;
    }

    public void setDirection(DuckDirection direction) {
        this.direction = direction;
    }

    public float getObjectiveY() {
        return objectiveY;
    }

    public void setObjectiveY(float objectiveY) {
        objectiveY = objectiveY >= 2f ? objectiveY : 2f;
        this.objectiveY = objectiveY;
    }

    public DuckState getState() {
        return state;
    }

    public void setState(DuckState state) {
        this.state = state;
    }
}
