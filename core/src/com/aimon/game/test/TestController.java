package com.aimon.game.test;

import com.aimon.game.controller.MainController;
import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.GunModel;
import com.aimon.game.model.entities.PlayerModel;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.Test;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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

        //Test number of ducks and bullets created
        assertEquals(numberOfDucks, controllerNumberOfDuckBodies);
        assertEquals(numberOfBullets, controllerNumberOfBullets);

    }

    @Test
    public void nextLevel() throws InterruptedException {

        int numberOfDucks = 6;
        int numberOfBullets = 20;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        assertEquals(numberOfDucks, model.getNumberOfAliveDucks());

        updateWorld(controller, 2f);

        for(DuckBody duck : controller.getDuckBodies()){

            controller.fireGun(duck.getX(), duck.getY());

            updateWorld(controller, model.getPlayerModel().getGun().getShotDelay()+0.1f);

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
    public void reloadLogicAmmo(){

        int numberOfDucks = 6;
        int numberOfBullets = 12;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        updateWorld(controller, 2f);

        assertEquals(0, controller.reloadGun());

        int shotsFired = MathUtils.random(1, 5);
        for (int i = 0; i < shotsFired; i++) {
            controller.fireGun(15, 15);

            updateWorld(controller, model.getPlayerModel().getGun().getShotDelay());
        }

        assertEquals(shotsFired, 6 - model.getPlayerModel().getGun().getNumberOfBullets());

    }

    @Test
    public void reloadEmptyPlayerAmmo(){
        int numberOfDucks = 6;
        int numberOfBullets = 0;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        updateWorld(controller, 2f);

        controller.fireGun(15, 15);

        updateWorld(controller, model.getPlayerModel().getGun().getShotDelay());

        assertEquals(0, controller.reloadGun());
    }

    @Test
    public void duckDifferentBehaviors(){

        int numberOfDucks = 20;
        int numberOfBullets = 12;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        updateWorld(controller, 2f);

        byte ducksBehavior = 0x0;
        for (DuckBody duck : controller.getDuckBodies()){

            switch (((DuckModel)duck.getModel()).getType()){
                case DEWEY:
                    ducksBehavior |= 0x1; break;
                case LOUIE:
                    ducksBehavior |= 0x2; break;
                case HUEY:
                    ducksBehavior |= 0x4; break;
            }

            if(ducksBehavior == 0x7) break;
        }

        assertEquals(0x7, ducksBehavior);

    }

    @Test(timeout = 300)
    public void duckDifferentMovements() {

        long time = System.currentTimeMillis();

        int numberOfDucks = 1;
        int numberOfBullets = 12;

        MainModel model = new MainModel(5, 5, numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f, 0f), 1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        updateWorld(controller, 2f);

        byte ducksMovement = 0x0;

        while (ducksMovement != 0x3) {

            switch (((DuckModel) controller.getDuckBodies().get(0).getModel()).getState()) {
                case GO_DOWN:
                    ducksMovement |= 0x1;
                    break;
                case GO_UP:
                    ducksMovement |= 0x2;
                    break;
            }

            controller.update(0.1f);
        }
        System.out.println(System.currentTimeMillis() - time);

    }




    private static void updateWorld(MainController controller, float max) {
        float delta = 0;
        while(delta <= max){
            controller.update(0.1f);
            delta+=0.1;
        }
    }

    /*@Test
    public void fineForFiveSeconds() {
        long start = System.nanoTime();
        long end = start + TimeUnit.SECONDS.toNanos(5);
        while (System.nanoTime() < end) {
            assertNotEquals(MathUtils.random(1, 9), 10);
        }
    }*/


}
