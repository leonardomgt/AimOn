package com.aimon.game.view.game;

import com.aimon.game.AimOn;
import com.aimon.game.controller.MainController;
import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.view.game.entities.AimView;
import com.aimon.game.view.game.entities.DuckView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.List;

import sun.applet.Main;

/**
 * Created by Leo on 18/04/2017.
 */



public class GameScreen extends ScreenAdapter{

    private final AimOn game;
    private final OrthographicCamera camera;

    public final static float PIXEL_TO_METER = 0.7f / (114 / 3f);

    private static final float VIEWPORT_WIDTH = MainController.getControllerWidth();

    public static final String AIM_IMAGE = "arm-target.png";
    public static final String BACKGROUND_GAME_IMAGE = "backgroundGame.jpg";
    public static final String DEWEY_SPRITE = "dewey.png";


    private static float camera_zoom = 1f;


    private final MainController controller;

    private final DuckView duckView;
    private final AimView aimView;

    private final MainModel model;
    private int aimX, aimY;

    public GameScreen(AimOn game, MainModel model, MainController controller) {

        this.game = game;
        this.model = model;
        this.controller = controller;
        this.loadAssets();
        this.duckView = new DuckView(game);
        this.aimView = new AimView(game);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera(camera_zoom *VIEWPORT_WIDTH / PIXEL_TO_METER, camera_zoom *VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        initializeMousePosition();
    }

    private void initializeMousePosition() {


        this.aimX = Gdx.input.getX();
        this.aimY = Gdx.input.getY();

        Gdx.input.setCursorCatched(true);

    }

    private void loadAssets(){

        this.game.getAssetManager().load(AIM_IMAGE, Texture.class);
        this.game.getAssetManager().load(DEWEY_SPRITE, Texture.class);
        this.game.getAssetManager().load(BACKGROUND_GAME_IMAGE, Texture.class);
        this.game.getAssetManager().finishLoading();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInputs(delta);
        camera.position.set(model.getAim().getX()/PIXEL_TO_METER, model.getAim().getY()/PIXEL_TO_METER,0);

        camera.zoom = camera_zoom;
        controller.update(delta);
        camera.update();
        updateBatch(delta);
    }

    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            camera_zoom += 0.2f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            camera_zoom -= 0.2f;
        }

        controller.updateAimLocation( MainController.getControllerWidth()/2 + Gdx.input.getX() - aimX,  MainController.getControllerHeight()/2 + aimY - Gdx.input.getY());
    }

    private void updateBatch(float delta) {
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();

        drawBackground();
        drawEntities(delta);

        game.getBatch().end();

    }

    private void drawEntities(float delta) {

        List<DuckModel> ducks = model.getDucks();
        for (DuckModel duck : ducks) {
            duckView.update(duck, delta);
            duckView.draw(game.getBatch());
        }

        aimView.update(model.getAim());
        aimView.draw(game.getBatch());
        //this.groundView.update(this.model.getGround());
        //this.groundView.draw(game.getBatch());

    }

    private void drawBackground() {

        //game.getBatch().draw((Texture)game.getAssetManager().get("backgroundGame.jpg"),0,0,camera.viewportWidth,camera.viewportHeight);


        Texture background = game.getAssetManager().get(BACKGROUND_GAME_IMAGE, Texture.class);
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
//        game.getBatch().draw(background, 0, 0, 0, 0, (int)(controller.FIELD_WIDTH / PIXEL_TO_METER), (int) (controller.FIELD_HEIGHT / PIXEL_TO_METER));

        game.getBatch().draw(background, 0, 0, MainController.getControllerWidth() / PIXEL_TO_METER, MainController.getControllerHeight() / PIXEL_TO_METER);
    }
}

