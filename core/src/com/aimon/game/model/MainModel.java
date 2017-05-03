package com.aimon.game.model;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.entities.AimModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.GroundModel;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.aimon.game.view.game.GameScreen.VIEWPORT_WIDTH;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class MainModel {

    private List<DuckModel> ducks;
    private AimModel aim;
    private GroundModel ground;

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


            DuckModel currentDuck = new DuckModel(MathUtils.random(0,15),
                    MathUtils.random(0,10),
                    0,
                    DuckModel.DuckType.DEWEY);


            //DuckModel currentDuck = new DuckModel(18,7,0,type);

            this.ducks.add(currentDuck);
            this.ground = GroundModel.getInstance();

        }

    }

    public List<DuckModel> getDucks() {
        return this.ducks;
    }

    public AimModel getAim() { return aim; }
    public GroundModel getGround() { return this.ground;}
}
