package com.aimon.game.test;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.GunModel;
import com.aimon.game.model.entities.PlayerModel;
import com.badlogic.gdx.math.MathUtils;

import org.junit.Test;

import static com.aimon.game.test.GeneralTest.updateWorld;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class GunTest {

    @Test
    public void gunEmptyRound() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        while(model.getPlayerModel().getGun().getNumberOfBullets() > 0) {

            assertTrue(controller.fireGun(1,1));
            updateWorld(controller, model.getPlayerModel().getGun().getShotDelay());
        }

        assertTrue(!controller.fireGun(1,1));

    }

    @Test
    public void reloadAmmoTest(){

        int numberOfDucks = 6;
        int numberOfBullets = 12;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        updateWorld(controller, 2f);

        assertEquals(0, controller.reloadGun());
        updateWorld(controller, 6*model.getPlayerModel().getGun().getReloadBulletDelay()+ model.getPlayerModel().getGun().getSlideDelay());

        int shotsFired = MathUtils.random(1, 5);
        for (int i = 0; i < shotsFired; i++) {
            controller.fireGun(15, 15);

            updateWorld(controller, model.getPlayerModel().getGun().getShotDelay());
        }

        assertEquals(shotsFired, 6 - model.getPlayerModel().getGun().getNumberOfBullets());

    }

    @Test
    public void reloadWhileReloading(){
        int numberOfDucks = 6;
        int numberOfBullets = 12;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        updateWorld(controller, 2f);

        controller.fireGun(15, 15);

        updateWorld(controller, model.getPlayerModel().getGun().getShotDelay()+0.1f);

        controller.reloadGun();

        controller.update(0.1f);

        assertEquals(0, controller.reloadGun());

        assertEquals(GunModel.GunState.RELOADING,model.getPlayerModel().getGun().getState());


    }

    @Test
    public void reloadWhileFiring(){
        int numberOfDucks = 6;
        int numberOfBullets = 12;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        updateWorld(controller, 2f);

        controller.fireGun(15, 15);

        controller.update(0.01f);

        assertEquals(GunModel.GunState.FIRING,model.getPlayerModel().getGun().getState());

        assertEquals(0, controller.reloadGun());

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
}
