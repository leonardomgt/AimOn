package com.aimon.game.view.menu;

import com.aimon.game.AimOn;
import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

    final String BACKGROUND_MAIN_MENU = "backgroundMainMenu2.jpg";
    final String DUCK_MAIN_MENU = "duck.png";
    final String HUNTER_MAIN_MENU = "hunter.png";

    float duckRatio;
    float hunterRatio;


    BitmapFont fontTitle;



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

        initializeFontConfig();

    }

    private void initializeFontConfig() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pinewood.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        fontTitle = generator.generateFont(parameter); // font size 60 pixels
        generator.dispose();
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
        buttonPlay.setDisabled(true);

    }

    private void updateBatch(){
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();

        // Draw background
        game.getBatch().draw((Texture)game.getAssetManager().get(BACKGROUND_MAIN_MENU),0,0,camera.viewportWidth,camera.viewportHeight);
        game.getBatch().draw((Texture)game.getAssetManager().get(DUCK_MAIN_MENU), 730, 100, 285, 200);
        game.getBatch().draw((Texture)game.getAssetManager().get(HUNTER_MAIN_MENU), 50, 50, 388, 300);

        // Draw Title
        fontTitle.setColor(Color.ORANGE);
        fontTitle.draw(game.getBatch(), "AimOn", 150, 500);


        game.getBatch().end();
    }

    private void loadAssets(){

        this.game.getAssetManager().load(BACKGROUND_MAIN_MENU, Texture.class);
        this.game.getAssetManager().load(DUCK_MAIN_MENU, Texture.class);
        this.game.getAssetManager().load(HUNTER_MAIN_MENU, Texture.class);

        Texture duck = this.game.getAssetManager().get(DUCK_MAIN_MENU);

        duckRatio = duck.getWidth()/duck.getHeight();


        this.game.getAssetManager().finishLoading();

    }

    @Override
    public void show(){
        initializeButtonsConfig();

        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonPlay.pad(20);
        //buttonPlay.getLabel().setFontScale(2, 2);
        buttonPlay.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                MainModel model = new MainModel(MainController.getControllerWidth()/2, MainController.getControllerHeight()/2, 3 /*TODO nº of ducks*/);
                MainController controller = new MainController(model);
                game.setScreen(new GameScreen(game,model,controller));
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
        textButtonStyle.font = fontTitle;
        textButtonStyle.fontColor = Color.WHITE;

        stage.addActor(table);
    }
}