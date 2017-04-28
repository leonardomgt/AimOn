package com.aimon.game.view.game;

import com.aimon.game.AimOn;
import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.view.game.entities.DuckView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Leo on 18/04/2017.
 */



public class GameScreen extends ScreenAdapter {

    private final AimOn game;
    private final OrthographicCamera camera;

    public final static float PIXEL_TO_METER = 0.7f / (375f / 9f);

    private static final float VIEWPORT_WIDTH = 20;

    private final MainController controller;

    private final DuckView duckView;

    private final MainModel model;

    public GameScreen(AimOn game, MainModel model, MainController controller) {

        this.game = game;
        this.model = model;
        this.controller = controller;
        this.loadAssets();
        this.duckView = new DuckView(game);



        // create the camera and the SpriteBatch
        camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    private void loadAssets(){

        this.game.getAssetManager().load("dewey.png", Texture.class);
        this.game.getAssetManager().finishLoading();

    }
}
