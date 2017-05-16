package com.aimon.game.model;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.entities.AimModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.GroundModel;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class MainModel {

    private List<DuckModel> ducks;
    private AimModel aim;
    private GroundModel ground;
    private int numberOfDucks;

    public MainModel(float aimX, float aimY, int numberOfDucks) {

        this.aim = new AimModel(aimX,aimY);
        this.numberOfDucks = numberOfDucks;
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


            DuckModel currentDuck = new DuckModel(20,
                    10,
                    0,
                    DuckModel.DuckType.DEWEY, MathUtils.random(5.0f,50.0f));


            //DuckModel currentDuck = new DuckModel(18,7,0,type);

            this.ducks.add(currentDuck);
            this.ground = GroundModel.getInstance();

        }
        Collections.sort(this.ducks);

    }

    public int getNumberOfDucks() {
        return numberOfDucks;
    }

    public List<DuckModel> getDucks() {
        return this.ducks;
    }

    public AimModel getAim() { return aim; }
    public GroundModel getGround() { return this.ground;}
}
