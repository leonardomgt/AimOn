package com.aimon.game.model;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.entities.AimModel;
import com.aimon.game.model.entities.DuckModel;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class MainModel {

    private List<DuckModel> ducks;
    private AimModel aim;

    public MainModel(float aimX, float aimY, int numberOfDucks) {

        this.aim = new AimModel(aimX,aimY);

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


            DuckModel currentDuck = new DuckModel(random.nextFloat() * MainController.FIELD_WIDTH,
                    random.nextFloat() * MainController.FIELD_HEIGHT,
                    (float) Math.toRadians(random.nextFloat() * 360),
                    type);

            this.ducks.add(currentDuck);


        }

    }

    public List<DuckModel> getDucks() {
        return ducks;
    }

    public AimModel getAim() { return aim; }
}
