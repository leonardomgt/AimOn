package com.aimon.game.view.menu;

import com.aimon.game.AimOn;
import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Leo on 18/04/2017.
 */

public class MainMenuScreen extends ScreenAdapter {

    final AimOn game;

    public Texture backgroundImage;
    public Texture buttonReleased;
    public Texture buttonPressed;

    float buttonsRate;


    OrthographicCamera camera;

    public MainMenuScreen(AimOn game) {
        this.game = game;

        loadAssets();
        camera = createCamera();

    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 540);
        return camera;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        this.backgroundImage = game.getAssetManager().get("backgroundMainMenu.png", Texture.class);
        this.buttonReleased = game.getAssetManager().get("button.png", Texture.class);
        this.buttonPressed = game.getAssetManager().get("buttonPressed.png", Texture.class);
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(backgroundImage,0,0,camera.viewportWidth,camera.viewportHeight);
        game.getBatch().draw(buttonReleased,20,20);

        game.font.draw(game.getBatch(), "Welcome to AimOn!!! ", 100, 150);
        game.font.draw(game.getBatch(), "Tap anywhere to begin!", 100, 100);
        game.getBatch().end();

        if (Gdx.input.justTouched()) {
           // game.setScreen(new GameScreen(game, new MainModel(3), new MainController()));
            dispose();
        }
    }

    @Override
    public void dispose() {

    }

    private void loadAssets(){

        this.game.getAssetManager().load("backgroundMainMenu.png", Texture.class);
        this.game.getAssetManager().load("button.png", Texture.class);
        this.game.getAssetManager().load("buttonPressed.png", Texture.class);
        this.game.getAssetManager().finishLoading();

    }
}
