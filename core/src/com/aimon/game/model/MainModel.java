package com.aimon.game.model;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.GroundModel;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class MainModel {

    private List<DuckModel> ducks;
    private GroundModel ground;

    public MainModel(int numberOfDucks) {

        this.ducks = new ArrayList<DuckModel>();

        for (int i = 0; i < numberOfDucks; i++) {

            DuckModel.DuckType type = DuckModel.DuckType.DEWEY;
            int t = random.nextInt(2);
            switch (t) {
                case 0:
                    type = DuckModel.DuckType.DEWEY;
                    break;
                case 1:
                    type = DuckModel.DuckType.LOUIE;
                    break;
                case 2:
                    type = DuckModel.DuckType.HUEY;
                    break;
            }

            /*
            DuckModel currentDuck = new DuckModel(random.nextFloat() * MainController.FIELD_WIDTH,
                    random.nextFloat() * MainController.FIELD_HEIGHT,
                    (float) Math.toRadians(random.nextFloat() * 360),
                    type);
                    */
            DuckModel currentDuck = new DuckModel(10,10,0,type);

            this.ducks.add(currentDuck);
            this.ground = GroundModel.getInstance();

        }

    }

    public List<DuckModel> getDucks() {
        return this.ducks;
    }
    public GroundModel getGround() { return this.ground;}
}
