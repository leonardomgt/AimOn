package com.aimon.game.model.entities;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class DuckModel extends EntityModel implements Comparable<DuckModel>{

    public enum DuckType {

        LOUIE, HUEY, DEWEY;

        private String name;

        static {
            LOUIE.name = "louie";
            HUEY.name = "huey";
            DEWEY.name = "dewey";
        }

        public String getName() {
            return name;
        }
    }

    private static final float FRIGHTEN_DISTANCE = 5;
    private static final float FRIGHTEN_TIME = 0.5f;
    private static final float FRIGHTEN_VELOCITY_FACTOR = 2.5f;

    public enum DuckDirection {RIGHT, LEFT};
    public enum DuckState{GO_UP, GO_DOWN, FLOAT_UP, FLOAT_DOWN, FALLING, SHOT, DEAD}

    public enum Life{DEAD, ALIVE};
    private DuckType type;
    private Life life;
    private DuckDirection direction;
    private float objectiveY;
    protected DuckState state;
    private float depthFactor = 1;
    private float depth;

    private float lifeTime = 0;
    private float deadMoment;

    private float normalVelocity;
    private float frightenVelocity;

    private boolean frightened = false;
    private float frightenedMoment = 0;


    public DuckModel(float x, float y, float rotation, DuckType type, float depth) {

        super(x,y,rotation);
        this.type = type;
        this.life = Life.ALIVE;
        this.direction = DuckDirection.RIGHT;
        this.objectiveY = y;
        this.state = DuckState.FLOAT_UP;
        this.depthFactor = 10/depth;
        this.depth = depth;

    }

    public void updateLifeTime(float delta) {
        this.lifeTime += delta;
    }

    public DuckType getType() {
        return type;
    }

    public void kill() {

        if(this.isAlive()) {
            this.life = Life.DEAD;
            this.setState(DuckState.SHOT);
            this.deadMoment = this.lifeTime;
            this.setRotation(0);

        }

    }

    public boolean isAlive() {
        return this.state != DuckState.FALLING && this.state != DuckState.SHOT && this.state != DuckState.DEAD;
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
        objectiveY = objectiveY >= 3f ? objectiveY : 3f;
        this.objectiveY = objectiveY;
    }

    public DuckState getState() {
        return state;
    }

    public void setState(DuckState state) {
        this.state = state;
    }

    public Life getLife() {
        return life;
    }

    public float getDepthFactor() {
        return depthFactor;
    }

    public float getDepth() {
        return depth;
    }

    @Override
    public int compareTo(DuckModel duckModel) {
        return (int)(duckModel.depth * 1000 - this.depth * 1000);
    }

    public float getLifeTime() {
        return lifeTime;
    }

    public float getDeadMoment() {
        return deadMoment;
    }

    public float getNormalVelocity() {
        return normalVelocity;
    }

    public void setNormalVelocity(float normalVelocity) {
        this.normalVelocity = normalVelocity;
    }

    public float getFrightenVelocity() {
        return frightenVelocity;
    }

    public void setFrightenVelocity(float frightenVelocity) {
        this.frightenVelocity = frightenVelocity;
    }

    public boolean isFrightened() {
        return frightened;
    }

    public void setFrightened(boolean frightened) {
        this.frightened = frightened;
    }

    public float getFrightenedMoment() {
        return frightenedMoment;
    }

    public void setFrightenedMoment(float frightenedMoment) {
        this.frightenedMoment = frightenedMoment;
    }

    public static float getFrightenDistance() {
        return FRIGHTEN_DISTANCE;
    }

    public static float getFrightenTime() {
        return FRIGHTEN_TIME;
    }

    public static float getFrightenVelocityFactor() {
        return FRIGHTEN_VELOCITY_FACTOR;
    }
}
