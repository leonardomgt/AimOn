package com.aimon.game.model.entities;

import static com.aimon.game.view.game.GameScreen.PIXEL_TO_METER;


/**
 * Created by joaofurriel on 29/04/17.
 */

public class GroundModel extends EntityModel {

    private static GroundModel instance;

    private GroundModel() {

        super(0, 0, 0);

    }

    public static GroundModel getInstance() {

        return GroundModel.instance == null ? new GroundModel() : GroundModel.instance;
    }


}






