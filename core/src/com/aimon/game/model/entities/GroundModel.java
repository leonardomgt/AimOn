package com.aimon.game.model.entities;


/**
 * Ground Model. Used to hold data of the ground.
 */

public class GroundModel extends EntityModel {

    /** The instance. */
    private static GroundModel instance;

    /**
     * Instantiates a new ground model.
     */
    private GroundModel() {

        super(0, 0, 0);

    }

    /**
     * Gets the single instance of GroundModel.
     *
     * @return single instance of GroundModel
     */
    public static GroundModel getInstance() {

        return GroundModel.instance == null ? new GroundModel() : GroundModel.instance;
    }


}






