package com.aimon.game.model.entities;

// TODO: Auto-generated Javadoc
/**
 * Created by joaofurriel on 28/04/17.
 */

public class DuckModel extends EntityModel implements Comparable<DuckModel>{

    /**
     * The Enum DuckType.
     */
    public enum DuckType {

        /** The louie. */
        LOUIE, /** The huey. */
 HUEY, /** The dewey. */
 DEWEY;

        /** The name. */
        private String name;

        static {
            LOUIE.name = "louie";
            HUEY.name = "huey";
            DEWEY.name = "dewey";
        }

        /**
         * Gets the name.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }
    }

    /** The Constant FRIGHTEN_DISTANCE. */
    private static final float FRIGHTEN_DISTANCE = 5;
    
    /** The Constant FRIGHTEN_TIME. */
    private static final float FRIGHTEN_TIME = 0.5f;

    /** The Constant DEWEY_NORMAL_VELOCITY. */
    public static final float DEWEY_NORMAL_VELOCITY = 2f;
    
    /** The Constant HUEY_NORMAL_VELOCITY. */
    public static final float HUEY_NORMAL_VELOCITY = 3f;
    
    /** The Constant LOUIE_NORMAL_VELOCITY. */
    public static final float LOUIE_NORMAL_VELOCITY = 4f;

    /** The Constant DEWEY_FRIGHTEN_FACTOR. */
    public static final float DEWEY_FRIGHTEN_FACTOR = 2f;
    
    /** The Constant HUEY_FRIGHTEN_FACTOR. */
    public static final float HUEY_FRIGHTEN_FACTOR = 3f;
    
    /** The Constant LOUIE_FRIGHTEN_FACTOR. */
    public static final float LOUIE_FRIGHTEN_FACTOR = 4f;

    /**
     * The Enum DuckDirection.
     */
    public enum DuckDirection {/** The right. */
RIGHT, /** The left. */
 LEFT};
    
    /**
     * The Enum DuckState.
     */
    public enum DuckState{
/** The go up. */
GO_UP, 
 /** The go down. */
 GO_DOWN, 
 /** The float up. */
 FLOAT_UP, 
 /** The float down. */
 FLOAT_DOWN, 
 /** The falling. */
 FALLING, 
 /** The shot. */
 SHOT, 
 /** The dead. */
 DEAD}

    /**
     * The Enum Life.
     */
    public enum Life{/** The dead. */
DEAD, /** The alive. */
 ALIVE};
    
    /** The type. */
    private DuckType type;
    
    /** The life. */
    private Life life;
    
    /** The direction. */
    private DuckDirection direction;
    
    /** The objective Y. */
    private float objectiveY;
    
    /** The state. */
    protected DuckState state;
    
    /** The depth factor. */
    private float depthFactor = 1;
    
    /** The depth. */
    private float depth;

    /** The life time. */
    private float lifeTime = 0;
    
    /** The dead moment. */
    private float deadMoment;

    /** The frightened. */
    private boolean frightened = false;
    
    /** The frightened moment. */
    private float frightenedMoment = 0;

    /** The normal velocity. */
    private float normalVelocity;
    
    /** The frigthen velocity. */
    private float frigthenVelocity;

    /** The frightened factor. */
    private float frightenedFactor;


    /**
     * Instantiates a new duck model.
     *
     * @param x the x
     * @param y the y
     * @param rotation the rotation
     * @param type the type
     * @param depth the depth
     * @param frightenedFactor the frightened factor
     */
    public DuckModel(float x, float y, float rotation, DuckType type, float depth, float frightenedFactor) {

        super(x,y,rotation);
        this.type = type;
        this.life = Life.ALIVE;
        this.direction = DuckDirection.RIGHT;
        this.objectiveY = y;
        this.state = DuckState.FLOAT_UP;
        this.depthFactor = 10/depth;
        this.depth = depth;
        this.frightenedFactor = frightenedFactor;

    }

    /**
     * Update life time.
     *
     * @param delta the delta
     */
    public void updateLifeTime(float delta) {
        this.lifeTime += delta;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public DuckType getType() {
        return type;
    }

    /**
     * Kill.
     */
    public void kill() {

        if(this.isAlive()) {
            this.life = Life.DEAD;
            this.setState(DuckState.SHOT);
            this.deadMoment = this.lifeTime;
            this.setRotation(0);

        }

    }

    /**
     * Checks if is alive.
     *
     * @return true, if is alive
     */
    public boolean isAlive() {
        return this.state != DuckState.FALLING && this.state != DuckState.SHOT && this.state != DuckState.DEAD;
    }

    /**
     * Gets the direction.
     *
     * @return the direction
     */
    public DuckDirection getDirection() {
        return direction;
    }

    /**
     * Sets the direction.
     *
     * @param direction the new direction
     */
    public void setDirection(DuckDirection direction) {
        this.direction = direction;
    }

    /**
     * Gets the objective Y.
     *
     * @return the objective Y
     */
    public float getObjectiveY() {
        return objectiveY;
    }

    /**
     * Sets the objective Y.
     *
     * @param objectiveY the new objective Y
     */
    public void setObjectiveY(float objectiveY) {
        objectiveY = objectiveY >= 3f ? objectiveY : 3f;
        this.objectiveY = objectiveY;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public DuckState getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state the new state
     */
    public void setState(DuckState state) {
        this.state = state;
    }

    /**
     * Gets the life.
     *
     * @return the life
     */
    public Life getLife() {
        return life;
    }

    /**
     * Gets the depth factor.
     *
     * @return the depth factor
     */
    public float getDepthFactor() {
        return depthFactor;
    }

    /**
     * Gets the depth.
     *
     * @return the depth
     */
    public float getDepth() {
        return depth;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(DuckModel duckModel) {
        return (int)(duckModel.depth * 1000 - this.depth * 1000);
    }

    /**
     * Gets the life time.
     *
     * @return the life time
     */
    public float getLifeTime() {
        return lifeTime;
    }

    /**
     * Gets the dead moment.
     *
     * @return the dead moment
     */
    public float getDeadMoment() {
        return deadMoment;
    }


    /**
     * Checks if is frightened.
     *
     * @return true, if is frightened
     */
    public boolean isFrightened() {
        return frightened;
    }

    /**
     * Sets the frightened.
     *
     * @param frightened the new frightened
     */
    public void setFrightened(boolean frightened) {
        this.frightened = frightened;
    }

    /**
     * Gets the frightened moment.
     *
     * @return the frightened moment
     */
    public float getFrightenedMoment() {
        return frightenedMoment;
    }

    /**
     * Sets the frightened moment.
     *
     * @param frightenedMoment the new frightened moment
     */
    public void setFrightenedMoment(float frightenedMoment) {
        this.frightenedMoment = frightenedMoment;
    }

    /**
     * Gets the frighten distance.
     *
     * @return the frighten distance
     */
    public static float getFrightenDistance() {
        return FRIGHTEN_DISTANCE;
    }

    /**
     * Gets the frighten time.
     *
     * @return the frighten time
     */
    public static float getFrightenTime() {
        return FRIGHTEN_TIME;
    }


    /**
     * Gets the normal velocity.
     *
     * @return the normal velocity
     */
    public float getNormalVelocity() {
        return normalVelocity;
    }

    /**
     * Sets the normal velocity.
     *
     * @param normalVelocity the new normal velocity
     */
    public void setNormalVelocity(float normalVelocity) {
        this.normalVelocity = normalVelocity;
    }

    /**
     * Gets the frigthen velocity.
     *
     * @return the frigthen velocity
     */
    public float getFrigthenVelocity() {
        return frigthenVelocity;
    }

    /**
     * Sets the frigthen velocity.
     *
     * @param frigthenVelocity the new frigthen velocity
     */
    public void setFrigthenVelocity(float frigthenVelocity) {
        this.frigthenVelocity = frigthenVelocity;
    }

    /**
     * Gets the frightened factor.
     *
     * @return the frightened factor
     */
    public float getFrightenedFactor() {
        return frightenedFactor;
    }
}
