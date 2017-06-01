package com.aimon.game.test;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.PlayerModel;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by joaofurriel on 01/06/17.
 */

public class TestController {

    /*
    Test creation of controller of a model with n ducks and a player with m bullets
     */

    @Test
    public void generateModel() {


        //World world = new World(new Vector2(2,2), false);


        int numberOfDucks = 10;
        int numberOfBullets = 20;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);


        int controllerNumberOfDuckBodies = controller.getDuckBodies().size();
        int controllerNumberOfBullets = controller.getPlayerController().getPlayerModel().getNumberOfBullets();

        assertEquals(numberOfDucks, controllerNumberOfDuckBodies);
        assertEquals(numberOfBullets, controllerNumberOfBullets);

    }


}
