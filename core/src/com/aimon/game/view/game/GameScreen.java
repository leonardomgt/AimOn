package com.aimon.game.view.game;

import com.aimon.game.AimOn;
import com.aimon.game.controller.MainController;
import com.aimon.game.controller.entities.DuckBody;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.view.game.entities.DuckView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

/**
 * Created by Leo on 18/04/2017.
 */



public class GameScreen extends ScreenAdapter {

    private final AimOn game;
    private final OrthographicCamera camera;

    public final static float PIXEL_TO_METER = 0.7f / (114 / 3f);

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
        this.game.getAssetManager().load("backgroundGame.jpg", Texture.class);
        this.game.getAssetManager().load("ground.png", Texture.class);
        this.game.getAssetManager().finishLoading();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.update(delta);
        camera.update();
        updateBatch(delta);
    }

    private void updateBatch(float delta) {
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        game.getBatch().draw((Texture)game.getAssetManager().get("backgroundGame.jpg"),0,0,camera.viewportWidth,camera.viewportHeight);
        drawEntities(delta);
        game.getBatch().end();

    }

    private void drawEntities(float delta) {

        List<DuckModel> ducks = model.getDucks();
        for (DuckModel duck : ducks) {
            duckView.update(duck, delta);
            duckView.draw(game.getBatch());
        }
        //this.groundView.update(this.model.getGround());
        //this.groundView.draw(game.getBatch());

    }
}
