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
import com.aimon.game.model.entities.PlayerModel;

import org.junit.Test;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

// TODO: Auto-generated Javadoc
/**
 * Created by joaofurriel on 01/06/17.
 */

public class TestController {

    /*
    Test creation of controller of a model with n ducks and a player with m bullets
     */

    /**
     * Generate model.
     */
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

    /**
     * Ducks born alive.
     */
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

    /**
     * Associate duck model to duck body.
     */
    @Test
    public void associateDuckModelToDuckBody() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 1, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);

        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModelInBodyData = (DuckModel) duckBody.getUserData();
        DuckModel duckModelInModel = (DuckModel) model.getDucks().get(0);

        assertSame(duckModelInBodyData, duckModelInModel);

    }

    /**
     * Kill duck.
     */
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

    /**
     * Duck falling and dying.
     */
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

    /**
     * Gun empty round.
     */
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

    /**
     * Reloading logic.
     */
    @Test
    public void reloadingLogic() {



    }

    /**
     * Send duck up.
     */
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

    /**
     * Send duck down.
     */
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

    /**
     * Change direction.
     */
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

    /**
     * Frighten duck change velocity.
     */
    @Test
    public void frightenDuckChangeVelocity() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();

        duckBody.setTransform(MainController.getControllerWidth()/2, MainController.getControllerHeight(), duckBody.getBody().getAngle());
        duckModel.setPosition(MainController.getControllerWidth()/2, MainController.getControllerHeight());

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

    /**
     * Duck frighten time.
     */
    @Test
    public void duckFrightenTime() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();

        duckBody.setTransform(MainController.getControllerWidth()/2, MainController.getControllerHeight(), duckBody.getBody().getAngle());
        duckModel.setPosition(MainController.getControllerWidth()/2, MainController.getControllerHeight());

        updateWorld(controller, 2f);
        assertTrue(!duckModel.isFrightened());
        controller.fireGun(duckModel.getX() + DuckModel.getFrightenDistance()/2, duckModel.getY());
        assertTrue(duckModel.isFrightened());
        updateWorld(controller, DuckModel.getFrightenTime());
        assertTrue(!duckModel.isFrightened());

    }

    /**
     * Frighten duck from front.
     */
    @Test
    public void frightenDuckFromFront() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();

        duckBody.setTransform(MainController.getControllerWidth()/2, MainController.getControllerHeight(), duckBody.getBody().getAngle());
        duckModel.setPosition(MainController.getControllerWidth()/2, MainController.getControllerHeight());

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

    /**
     * Frighten duck from behind.
     */
    @Test
    public void frightenDuckFromBehind() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();

        duckBody.setTransform(MainController.getControllerWidth()/2, MainController.getControllerHeight(), duckBody.getBody().getAngle());
        duckModel.setPosition(MainController.getControllerWidth()/2, MainController.getControllerHeight());

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

    /**
     * Frighten duck already frightened.
     */
    @Test
    public void frightenDuckAlreadyFrightened() {

        MainModel model = new MainModel(5,5,1, new PlayerModel("Name", 0, 0f,0f),1, .5859375f);
        MainController controller = new MainController(model, .5859375f);
        DuckBody duckBody = controller.getDuckBodies().get(0);
        DuckModel duckModel = (DuckModel) duckBody.getUserData();

        duckBody.setTransform(MainController.getControllerWidth()/2, MainController.getControllerHeight(), duckBody.getBody().getAngle());
        duckModel.setPosition(MainController.getControllerWidth()/2, MainController.getControllerHeight());

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



    /**
     * Update world.
     *
     * @param controller the controller
     * @param max the max
     */
    private static void updateWorld(MainController controller, float max) {
        float delta = 0;
        while(delta <= max){
            controller.update(0.2f);
            delta+=0.2;
        }
    }

    /**
     * Next level.
     *
     * @throws InterruptedException the interrupted exception
     */
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

            updateWorld(controller, model.getPlayerModel().getGun().getShotDelay());

            System.out.println(model.getNumberOfAliveDucks());

        }


        assertEquals(0, model.getNumberOfAliveDucks());

        while(model.getNumberOfDucksOnGround() != numberOfDucks){
            updateWorld(controller, 0.5f);
        }

        assertEquals(MainModel.LevelState.NEXT_LEVEL, model.getLevelState());
    }

    /**
     * Game over.
     */
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
    
    /**
     * Reload ammo test.
     */
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

    /**
     * Reload while reloading.
     */
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

    /**
     * Reload while firing.
     */
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

    /**
     * Reload empty player ammo.
     */
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

    /**
     * Duck different behaviors.
     */
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

    /**
     * Duck different movements.
     */
    @Test(timeout = 300)
    public void duckDifferentMovements() {

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

    }

    /**
     * Aim location.
     */
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
    /*@Test
    public void fineForFiveSeconds() {
        long start = System.nanoTime();
        long end = start + TimeUnit.SECONDS.toNanos(5);
        while (System.nanoTime() < end) {
            assertNotEquals(MathUtils.random(1, 9), 10);
        }
    }*/


}
