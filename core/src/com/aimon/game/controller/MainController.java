package com.aimon.game.controller;

import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.List;

/**
 * Created by joaofurriel on 28/04/17.
 */

public class MainController {

    public static final int FIELD_HEIGHT = 200;

    public static final int FIELD_WIDTH = 400;

    private final World world;

    private float accumulator;

    private MainModel model;

    public MainController(MainModel model) {

        this.model = model;

        this.world = new World(new Vector2(0,-10), true);

        List<DuckModel> ducks = model.getDucks();
        for (DuckModel duck : ducks) {

            switch (duck.getType()) {
                case LOUIE:
            }

        }

    }

    public void updateAimLocation(float x, float y){
        model.getAim().setPosition(x, y);
    }

}
