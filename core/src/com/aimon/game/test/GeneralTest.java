package com.aimon.game.test;

import com.aimon.game.controller.MainController;
import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.PlayerModel;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by joaofurriel on 01/06/17.
 */

public class GeneralTest {

    /*
    Test creation of controller of a model with n ducks and a player with m bullets
     */

    public static void updateWorld(MainController controller, float max) {
        float delta = 0;
        while(delta <= max){
            controller.update(0.2f);
            delta+=0.2;
        }
    }

    @Test
    public void generateModel() {

        int numberOfDucks = 10;
        int numberOfBullets = 20;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        int controllerNumberOfDuckBodies = controller.getDuckBodies().size();
        int controllerNumberOfBullets = controller.getPlayerController().getPlayerModel().getNumberOfBullets();

        //Test number of ducks and bullets created
        assertEquals(numberOfDucks, controllerNumberOfDuckBodies);
        assertEquals(numberOfBullets, controllerNumberOfBullets);
    }



    @Test
    public void associateDuckModelToDuckBody() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 1, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModelInBodyData = (DuckModel) duckBody.getUserData();
        DuckModel duckModelInModel = (DuckModel) model.getDucks().get(0);

        assertSame(duckModelInBodyData, duckModelInModel);

    }

    @Test
    public void nextLevel() throws InterruptedException {

        int numberOfDucks = 6;
        int numberOfBullets = 20;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        assertEquals(numberOfDucks, model.getNumberOfAliveDucks());

        updateWorld(controller, 2f);
        System.out.println(model.getNumberOfAliveDucks());


        for(DuckBody duck : controller.getDuckBodies()){

            controller.fireGun(duck.getX(), duck.getY());

            updateWorld(controller, model.getPlayerModel().getGun().getShotDelay()+0.5f);

        }

        assertEquals(0, model.getNumberOfAliveDucks());

        while(model.getNumberOfDucksOnGround() != numberOfDucks){
            updateWorld(controller, 0.5f);
        }

        assertEquals(MainModel.LevelState.NEXT_LEVEL, model.getLevelState());
    }

    @Test
    public void gameOver(){

        int numberOfDucks = 6;
        int numberOfBullets = 12;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        updateWorld(controller, 2f);

        for (int i = 0; i < numberOfBullets + 6; i++) {

            if(i % 6 == 0 && i != 0){
                controller.reloadGun();
                updateWorld(controller, 6*model.getPlayerModel().getGun().getReloadBulletDelay()+ model.getPlayerModel().getGun().getSlideDelay());
            }

            controller.fireGun(15, 15);

            updateWorld(controller, model.getPlayerModel().getGun().getShotDelay());

        }

        assertTrue(model.getNumberOfAliveDucks() > 0);
        assertEquals(0, model.getPlayerModel().getNumberOfBullets() + model.getPlayerModel().getGun().getNumberOfBullets());
        assertEquals(MainModel.LevelState.GAME_OVER, model.getLevelState());

    }
    

    @Test
    public void aimLocation(){

        int numberOfDucks = 1;
        int numberOfBullets = 12;

        MainModel model = new MainModel(5, 5, numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f, 0f), 1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        updateWorld(controller, 2f);

        controller.updateAimLocation(20, 5);

        assertEquals(20, model.getAim().getX(), 0);
        assertEquals(5, model.getAim().getY(), 0);

    }

}
