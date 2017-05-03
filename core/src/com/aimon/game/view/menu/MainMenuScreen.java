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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Leo on 18/04/2017.
 */

public class MainMenuScreen extends ScreenAdapter {

    final AimOn game;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay;
    private TextButton.TextButtonStyle textButtonStyle;
    float buttonsRate;

    OrthographicCamera camera;

    public MainMenuScreen(AimOn game) {

        this.game = game;
        loadAssets();
        camera = createCamera();
        Texture buttonDown = game.getAssetManager().get("buttonDown.png");
        buttonsRate = (float)buttonDown.getWidth()/buttonDown.getHeight();
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
        updateBatch();
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        buttonPlay.clearListeners();
    }

    private void updateBatch(){
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();

        // Draw background
        game.getBatch().draw((Texture)game.getAssetManager().get("backgroundMainMenu.png"),0,0,camera.viewportWidth,camera.viewportHeight);
        game.font.draw(game.getBatch(), "Welcome to AimOn!!! ", 100, 150);
        game.getBatch().end();
    }

    private void loadAssets(){

        this.game.getAssetManager().load("backgroundMainMenu.png", Texture.class);
        this.game.getAssetManager().load("buttonUp.png", Texture.class);
        this.game.getAssetManager().load("buttonDown.png", Texture.class);
        this.game.getAssetManager().finishLoading();

    }

    @Override
    public void show(){
        initializeButtonsConfig();
        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonPlay.pad(20);
        buttonPlay.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                MainMenuScreen.this.game.setGameScreen();
            }
        });
        table.add(buttonPlay);
    }


    private void initializeButtonsConfig(){
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        /*black = new BitmapFont(Gdx.files.internal("font/black.fnt"));
        white = new BitmapFont(Gdx.files.internal("font/white.fnt"));*/

        atlas = new TextureAtlas("button.pack");
        skin = new Skin(atlas);
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.over = skin.getDrawable("buttonOver");
        textButtonStyle.down = skin.getDrawable("buttonDown");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = new BitmapFont();

        stage.addActor(table);
    }
}

