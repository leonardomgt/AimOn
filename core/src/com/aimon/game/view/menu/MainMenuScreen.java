package com.aimon.game.view.menu;

import com.aimon.game.AimOn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * The Main-Menu screen. Contains all actors in the main-menu stage.
 */

public class MainMenuScreen extends ScreenAdapter {

    /** The AimOn game. */
    final AimOn game;

    /** The path to the main-menu background image. */
    final String BACKGROUND_MAIN_MENU = "backgroundMainMenu2.jpg";
    
    /** The path to the duck image on main-menu. */
    final String DUCK_MAIN_MENU = "duck.png";
    
    /** The path to the hunter image on main-menu. */
    final String HUNTER_MAIN_MENU = "hunter.png";


    /** All buttons acting in Main-Menu. */
    private MainMenuButtons buttons;


    /** The font of game's title. */
    BitmapFont fontTitle;

    /** The camera. */
    OrthographicCamera camera;

    /**
     * Instantiates a new Main-Menu screen.
     *
     * @param game the AimOn game
     */
    public MainMenuScreen(AimOn game) {

        this.game = game;
        fontTitle = new BitmapFont(game.getPineWoodFont().getData(), game.getPineWoodFont().getRegion(), game.getPineWoodFont().usesIntegerPositions());
        loadAssets();
        camera = createCamera();

        buttons = new MainMenuButtons(this);

    }

    /**
     * Creates the camera.
     *
     * @return the orthographic camera created
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 540);
        return camera;
    }


    /**
     * Render. Is called once per frame.
     *
     * @param delta the delta time in seconds since last render call
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
     * Dispose main-menu elements.
     */
    @Override
    public void dispose() {

        buttons.dispose();
    }

    /**
     * Update all visual elements in Main-Menu.
     */
    private void updateBatch(){
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();

        // Draw background
        game.getBatch().draw((Texture)game.getAssetManager().get(BACKGROUND_MAIN_MENU),0,0,camera.viewportWidth,camera.viewportHeight);
        game.getBatch().draw((Texture)game.getAssetManager().get(DUCK_MAIN_MENU), 20, 320, 285, 200);
        game.getBatch().draw((Texture)game.getAssetManager().get(HUNTER_MAIN_MENU), camera.viewportWidth - 400, 40, 388, 300);

        // Draw Title
        fontTitle.setColor(Color.ORANGE);
        
        GlyphLayout layout = new GlyphLayout(game.getPineWoodFont(), "AimOn");

        fontTitle.draw(game.getBatch(), "AimOn", Gdx.graphics.getWidth()/2f - layout.width*2/3f, 450);

        game.getBatch().end();
    }

    /**
     * Load all assets needed for Main-Menu screen.
     */
    private void loadAssets(){

        this.game.getAssetManager().load(BACKGROUND_MAIN_MENU, Texture.class);
        this.game.getAssetManager().load(DUCK_MAIN_MENU, Texture.class);
        this.game.getAssetManager().load(HUNTER_MAIN_MENU, Texture.class);

        this.game.getAssetManager().finishLoading();

    }

    /**
     * Sets the input processor as the stage of Main-Menu buttons.
     */
    public void setInputProcessor(){
        Gdx.input.setInputProcessor(buttons.getStage());
    }
}

