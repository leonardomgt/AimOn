package com.aimon.game.view.menu;

import com.aimon.game.AimOn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

// TODO: Auto-generated Javadoc
/**
 * Created by Leo on 18/04/2017.
 */

public class MainMenuScreen extends ScreenAdapter {

    /** The game. */
    final AimOn game;

    /** The background main menu. */
    final String BACKGROUND_MAIN_MENU = "backgroundMainMenu2.jpg";
    
    /** The duck main menu. */
    final String DUCK_MAIN_MENU = "duck.png";
    
    /** The hunter main menu. */
    final String HUNTER_MAIN_MENU = "hunter.png";


    /** The buttons. */
    private MainMenuButtons buttons;


    /** The font title. */
    BitmapFont fontTitle;

    /** The camera. */
    OrthographicCamera camera;

    /**
     * Instantiates a new main menu screen.
     *
     * @param game the game
     */
    public MainMenuScreen(AimOn game) {

        this.game = game;
        fontTitle = new BitmapFont(game.pineWoodFont.getData(), game.pineWoodFont.getRegion(), game.pineWoodFont.usesIntegerPositions());
        loadAssets();
        camera = createCamera();

        buttons = new MainMenuButtons(this);

    }

    /**
     * Creates the camera.
     *
     * @return the orthographic camera
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 540);
        return camera;
    }


    /**
     * Render.
     *
     * @param delta the delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        updateBatch();

        buttons.update(delta);
    }

    /**
     * Dispose.
     */
    @Override
    public void dispose() {

        buttons.dispose();
    }

    /**
     * Update batch.
     */
    private void updateBatch(){
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();

        // Draw background
        game.getBatch().draw((Texture)game.getAssetManager().get(BACKGROUND_MAIN_MENU),0,0,camera.viewportWidth,camera.viewportHeight);
        game.getBatch().draw((Texture)game.getAssetManager().get(DUCK_MAIN_MENU), 20, 320, 285, 200);
        game.getBatch().draw((Texture)game.getAssetManager().get(HUNTER_MAIN_MENU), Gdx.graphics.getWidth() - 320, 40, 388, 300);

        // Draw Title
        fontTitle.setColor(Color.ORANGE);

        fontTitle.draw(game.getBatch(), "AimOn", Gdx.graphics.getWidth()/2 - 50, 450);

        game.getBatch().end();
    }

    /**
     * Load assets.
     */
    private void loadAssets(){

        this.game.getAssetManager().load(BACKGROUND_MAIN_MENU, Texture.class);
        this.game.getAssetManager().load(DUCK_MAIN_MENU, Texture.class);
        this.game.getAssetManager().load(HUNTER_MAIN_MENU, Texture.class);

        this.game.getAssetManager().finishLoading();

    }

    /**
     * Sets the input processor.
     */
    public void setInputProcessor(){
        Gdx.input.setInputProcessor(buttons.getStage());
    }
}

