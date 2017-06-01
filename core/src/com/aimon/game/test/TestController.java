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

public class TestController {

    /*
    Test creation of controller of a model with n ducks and a player with m bullets
     */

    @Test
    public void generateModel() {

        int numberOfDucks = 10;
        int numberOfBullets = 20;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        int controllerNumberOfDuckBodies = controller.getDuckBodies().size();
        int controllerNumberOfBullets = controller.getPlayerController().getPlayerModel().getNumberOfBullets();

        assertEquals(numberOfDucks, controllerNumberOfDuckBodies);
        assertEquals(numberOfBullets, controllerNumberOfBullets);
    }

    @Test
    public void ducksBornAlive() {

        int numberOfDucks = 10;
        int numberOfBullets = 20;

        MainModel model = new MainModel(5,5,numberOfDucks, new PlayerModel("Name", numberOfBullets, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        for (DuckModel duckModel : model.getDucks()) {
            assertTrue(duckModel.isAlive());
        }

    }

    @Test
    public void associateDuckModeltoDuckBody() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 1, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModelInBodyData = (DuckModel) duckBody.getUserData();
        DuckModel duckModelInModel = (DuckModel) model.getDucks().get(0);

        assertSame(duckModelInBodyData, duckModelInModel);

    }

    @Test
    public void killDuck() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 1, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        DuckBody duckBody = controller.getDuckBodies().get(0);
        controller.fireGun(duckBody.getX(), duckBody.getY());

        DuckModel duckModel = (DuckModel) duckBody.getUserData();
        DuckModel.DuckState state =  duckModel.getState();

        assertEquals(state, DuckModel.DuckState.SHOT);

    }

    @Test
    public void duckFallingAndDying()  {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 1, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();

        updateWorld(controller,2);

        controller.fireGun(duckBody.getX(), duckBody.getY());
        updateWorld(controller,  DuckBody.get_TIME_STUNNED());
        assertEquals(DuckModel.DuckState.FALLING, duckModel.getState());

        while(model.getNumberOfDucksOnGround() < 1) {
            controller.update(0.25f);
        }

        assertEquals(duckModel.getState(),DuckModel.DuckState.DEAD);

    }

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
    public void reloadingLogic() {



    }

    @Test
    public void sendDuckUp() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();

        duckBody.setTransform(MainController.getControllerWidth()/2, MainController.getControllerHeight()/2, duckBody.getBody().getAngle());
        duckModel.setPosition(MainController.getControllerWidth()/2, MainController.getControllerHeight()/2);

        float duckHeight = duckModel.getY();
        duckBody.goUp(4);
        assertEquals(duckModel.getObjectiveY(),duckHeight + 4, 0.1f);

        float levelTime = model.getLevelTime();

        while(duckModel.getY() < duckModel.getObjectiveY() - DuckBody.getTHRESHOLD()) {

            controller.update(0.1f);

        }

        float delta = model.getLevelTime() - levelTime;
        float deltaFactor = delta * duckModel.getDepthFactor();

        assertEquals(deltaFactor, 1.75, 0.15f);

    }

    @Test
    public void sendDuckDown() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();

        duckBody.setTransform(MainController.getControllerWidth()/2, MainController.getControllerHeight(), duckBody.getBody().getAngle());
        duckModel.setPosition(MainController.getControllerWidth()/2, MainController.getControllerHeight());

        float duckHeight = duckModel.getY();
        duckBody.goDown(4);
        assertEquals(duckModel.getObjectiveY(),duckHeight - 4, 0.1f);

        float levelTime = model.getLevelTime();

        while(duckModel.getY() > duckModel.getObjectiveY() + DuckBody.getTHRESHOLD()) {

            controller.update(0.1f);

        }

        float delta = model.getLevelTime() - levelTime;
        float deltaFactor = delta * duckModel.getDepthFactor();

        System.out.println("Delta Factor:" + deltaFactor);

        assertEquals(deltaFactor, 1.45, 0.15f);

    }

    @Test
    public void changeDirection() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();

        updateWorld(controller, 2f);

        DuckModel.DuckDirection duckOldDirection = duckModel.getDirection();
        float xOldPosition = duckModel.getX();

        if (duckOldDirection == DuckModel.DuckDirection.RIGHT) {
            assertTrue(duckBody.getBody().getLinearVelocity().x > 0);
        }

        else {
            assertTrue(duckBody.getBody().getLinearVelocity().x < 0);
        }

        duckBody.changeDirection();
        DuckModel.DuckDirection newDirection = duckModel.getDirection();
        assertTrue(duckOldDirection != duckModel.getDirection());

        controller.update(0.1f);
        float xNewPosition = duckModel.getX();


        if (newDirection == DuckModel.DuckDirection.RIGHT) {
            assertTrue(duckBody.getBody().getLinearVelocity().x > 0);
            assertTrue(xNewPosition > xOldPosition);

        }

        else {
            assertTrue(duckBody.getBody().getLinearVelocity().x < 0);
            assertTrue(xNewPosition < xOldPosition);
        }


    }

    @Test
    public void frightenDuckChangeVelocity() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();
        updateWorld(controller, 2f);
        assertTrue(!duckModel.isFrightened());

        float oldVelocity = duckBody.getBody().getLinearVelocity().x;
        DuckModel.DuckDirection oldDirection = duckModel.getDirection();

        if (oldDirection == DuckModel.DuckDirection.RIGHT) {

            controller.fireGun(duckModel.getX() + DuckModel.getFrightenDistance()/2, duckModel.getY());

        }

        else {

            controller.fireGun(duckModel.getX() - DuckModel.getFrightenDistance()/2, duckModel.getY());

        }

        float newVelocity = duckBody.getBody().getLinearVelocity().x;


        assertTrue(duckModel.isFrightened());
        assertEquals(newVelocity, -oldVelocity * duckModel.getFrightenedFactor(), 0.1f);

        updateWorld(controller, DuckModel.getFrightenTime());
        assertTrue(!duckModel.isFrightened());

    }

    @Test
    public void duckFrightenTime() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();
        updateWorld(controller, 2f);
        assertTrue(!duckModel.isFrightened());
        controller.fireGun(duckModel.getX() + DuckModel.getFrightenDistance()/2, duckModel.getY());
        assertTrue(duckModel.isFrightened());
        updateWorld(controller, DuckModel.getFrightenTime());
        assertTrue(!duckModel.isFrightened());

    }

    @Test
    public void frightenDuckFromFront() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();
        updateWorld(controller, 2f);
        assertTrue(!duckModel.isFrightened());

        DuckModel.DuckDirection oldDirection = duckModel.getDirection();

        if (oldDirection == DuckModel.DuckDirection.RIGHT) {

            controller.fireGun(duckModel.getX() + DuckModel.getFrightenDistance()/2, duckModel.getY());

        }

        else {

            controller.fireGun(duckModel.getX() - DuckModel.getFrightenDistance()/2, duckModel.getY());

        }

        DuckModel.DuckDirection newDirection = duckModel.getDirection();

        assertTrue(duckModel.isFrightened());
        assertTrue(oldDirection != newDirection);

    }

    @Test
    public void frightenDuckFromBehind() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();
        updateWorld(controller, 2f);
        assertTrue(!duckModel.isFrightened());

        DuckModel.DuckDirection oldDirection = duckModel.getDirection();

        if (oldDirection == DuckModel.DuckDirection.RIGHT) {

            controller.fireGun(duckModel.getX() - DuckModel.getFrightenDistance()/2, duckModel.getY());

        }

        else {

            controller.fireGun(duckModel.getX() + DuckModel.getFrightenDistance()/2, duckModel.getY());

        }

        DuckModel.DuckDirection newDirection = duckModel.getDirection();

        assertTrue(duckModel.isFrightened());
        assertTrue(oldDirection == newDirection);

    }

    @Test
    public void frightenDuckAlreadyFrightened() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();
        updateWorld(controller, 2f);
        assertTrue(!duckModel.isFrightened());

        DuckModel.DuckDirection oldDirection = duckModel.getDirection();
        float oldVelocity = duckBody.getBody().getLinearVelocity().x;

        if (oldDirection == DuckModel.DuckDirection.RIGHT) {

            controller.fireGun(duckModel.getX() + DuckModel.getFrightenDistance()/2, duckModel.getY());

        }

        else {

            controller.fireGun(duckModel.getX() - DuckModel.getFrightenDistance()/2, duckModel.getY());

        }

        float newVelocity = duckBody.getBody().getLinearVelocity().x;
        assertEquals(newVelocity, -oldVelocity * duckModel.getFrightenedFactor(), 0.1f);
        updateWorld(controller, DuckModel.getFrightenTime()/3);

        if (oldDirection == DuckModel.DuckDirection.RIGHT) {

            controller.fireGun(duckModel.getX() - DuckModel.getFrightenDistance()/2, duckModel.getY());

        }

        else {

            controller.fireGun(duckModel.getX() + DuckModel.getFrightenDistance()/2, duckModel.getY());

        }

        float newVelocity2 = duckBody.getBody().getLinearVelocity().x;
        assertEquals(newVelocity, newVelocity2, 0.0);

        updateWorld(controller, DuckModel.getFrightenTime());
        assertTrue(!duckModel.isFrightened());

    }



    private static void updateWorld(MainController controller, float max) {
        float delta = 0;
        while(delta <= max){
            controller.update(0.2f);
            delta+=0.2;
        }
    }


}
