package com.aimon.game.view.game;

import com.aimon.game.AimOn;
import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.model.entities.PlayerModel;
import com.aimon.game.view.game.entities.AimView;
import com.aimon.game.view.game.entities.DuckView;
import com.aimon.game.view.game.entities.GameStatusView;
import com.aimon.game.view.game.inputprocessors.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.List;

import static com.aimon.game.model.MainModel.LevelState.NEXT_LEVEL;

/**
 * Game Screen
 *
 * Contains all visual elements needed in game scene.
 */


public class GameScreen extends ScreenAdapter {

    /** The AimOn game. */
    public final AimOn game;
    
    /** The camera. */
    private final OrthographicCamera camera;

    /** The convert rate from PIXELs to METERs. */
    public final static float PIXEL_TO_METER = .85f / (114 / 3f);
    
    /** The ratio Height/Width. */
    public static final float HEIGHT_WIDTH_RATIO = Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();

    /** The VIEWPORT WIDTH. */
    public static final float VIEWPORT_WIDTH = 22.5f;

    /** The VIEWPORT HEIGHT. */
    public static final float VIEWPORT_HEIGHT = VIEWPORT_WIDTH* HEIGHT_WIDTH_RATIO;

    /** The Constant BONUS_LEVEL. */
    private static final int BONUS_LEVEL = 0;
    
    /** The Constant BONUS_LEVEL_DUCKS. */
    private static final int BONUS_LEVEL_DUCKS = 500;
    
    /** The Constant BONUS_LEVEL_BULLETS. */
    private static final int BONUS_LEVEL_BULLETS = 494;

    /** The path to the game background image. */
    public static final String BACKGROUND_GAME_IMAGE = "backgroundGame.jpg";

    /** The huey view. */
    private final DuckView hueyView;
    
    /** The dewey view. */
    private final DuckView deweyView;
    
    /** The louie view. */
    private final DuckView louieView;

    /** The game status view. */
    private GameStatusView gameStatusView;

    /** The aim view. */
    private final AimView aimView;

    /** The aim position. */
    private Vector3 aimPosition;
    
    /** The initial aim position. */
    private final Vector3 initialAimPosition;

    /** The game multiplexer. Holds input processor and buttons stage. */
    private InputMultiplexer gameMultiplexer = new InputMultiplexer();

    /** The game input processor. */
    private GameInputProcessor gameInputProcessor;
    
    /** The game stage. */
    private Stage gameStage = new Stage();


    /** The Game model. */
    private MainModel model;
    
    /** The Game controller. */
    private MainController controller;
    
    /** The player model. */
    private PlayerModel playerModel;

    /** The initial number of ducks. */
    private int initialNumberOfDucks;
    
    /** The initial number of bullets. */
    private int initialNumberOfBullets;
    
    /** The level count. */
    private int level = 0;

    /** The bonus flag. True if Level is a bonus level. */
    private boolean bonus = false;

    /**
     * Instantiates a new game screen.
     *
     * @param game the AimOn game
     * @param playerName the player name
     * @param initialNumberOfDucks the initial number of ducks
     * @param initialNumberOfBullets the initial number of bullets
     */
    public GameScreen(AimOn game, String playerName, int initialNumberOfDucks, int initialNumberOfBullets) {

        this.game = game;
        this.initialNumberOfDucks = initialNumberOfDucks;
        this.initialNumberOfBullets = initialNumberOfBullets;
        this.playerModel = new PlayerModel(playerName, initialNumberOfBullets, GameScreen.VIEWPORT_WIDTH-0.5f, GameScreen.VIEWPORT_HEIGHT-0.5f);

        initiateLevel();

        this.initialAimPosition = new Vector3(model.getAim().getX(), model.getAim().getY(), 0);
        this.aimPosition = new Vector3(initialAimPosition);

        switch (Gdx.app.getType()){
            case Android:
                this.gameInputProcessor = new AndroidInputProcessor(this); break;
            case Desktop:
            default:
                this.gameInputProcessor = new DesktopInputProcessor(this); break;

        }

        this.loadAssets();

        this.deweyView = new DuckView(game, DuckModel.DuckType.DEWEY);
        this.hueyView = new DuckView(game, DuckModel.DuckType.HUEY);
        this.louieView = new DuckView(game, DuckModel.DuckType.LOUIE);
        this.aimView = new AimView(game);

        // create the camera and the SpriteBatch

        camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_HEIGHT / PIXEL_TO_METER);
        camera.position.set(MainController.getControllerWidth()  / PIXEL_TO_METER / 2f, camera.viewportHeight / 2f, 0);
        camera.update();


        gameInputProcessor.initializeUIElements();

        gameMultiplexer.addProcessor(gameStage);
        gameMultiplexer.addProcessor(gameInputProcessor);

    }

    /**
     * Initiate a new level.
     */
    private void initiateLevel() {

        if(this.bonus) {

            this.bonus = false;
            this.initialNumberOfDucks *= 2;
            this.initialNumberOfBullets = (3*initialNumberOfDucks)/2;
            this.setModelController(initialNumberOfDucks, initialNumberOfBullets, this.level);

        }

        else {

            this.level++;
            this.initialNumberOfBullets-=3;
            if (initialNumberOfBullets + this.playerModel.getGun().getCapacity()/2 < initialNumberOfDucks) {

                this.bonus = true;
                this.setModelController(BONUS_LEVEL_DUCKS, BONUS_LEVEL_BULLETS, BONUS_LEVEL);

            }
            else {

                this.setModelController(initialNumberOfDucks, initialNumberOfBullets, this.level);

            }


        }

    }

    /**
     * Creates a new model and a new controller for the new level.
     *
     * @param initialNumberOfDucks the initial number of ducks
     * @param initialNumberOfBullets the initial number of bullets
     * @param level the level count
     */
    private void setModelController(int initialNumberOfDucks, int initialNumberOfBullets, int level) {

        this.playerModel.setNumberOfBullets(initialNumberOfBullets);
        this.playerModel.reset();
        this.model = new MainModel(MainController.getControllerWidth()/2, VIEWPORT_HEIGHT/2f, initialNumberOfDucks, this.playerModel, level, HEIGHT_WIDTH_RATIO);
        this.controller = new MainController(this.model, HEIGHT_WIDTH_RATIO);
        this.gameStatusView = new GameStatusView(game, model);

    }


    /**
     * Load assets needed to game screen.
     */
    private void loadAssets(){

        this.game.getAssetManager().load(BACKGROUND_GAME_IMAGE, Texture.class);

        this.gameInputProcessor.loadAssets();

        this.game.getAssetManager().finishLoading();

    }

    /**
     * Render. Is called once per frame.
     *
     * @param delta the delta time in seconds since last render call
     */
    @Override
    public void render(float delta) {

        controller.update(delta);

        if (this.model.getLevelState() == NEXT_LEVEL) {
            initiateLevel();
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateBatch(delta);

        gameInputProcessor.updateAim();

        gameStage.act(delta);
        gameStage.draw();
        camera.update();

    }


    /**
     * Gets the aim position.
     *
     * @return the aim position
     */
    public Vector3 getAimPosition() {
        return aimPosition;
    }


    /**
     * Update all visual elements in game screen.
     *
     * @param delta the delta time in seconds since last call
     */
    private void updateBatch(float delta) {
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        drawBackground();
        drawEntities(delta);
        game.getBatch().end();

    }

    /**
     * Draw entities:
     *
     *   if Running: Draw duckView, aimView and gameStatusView.
     *   else gameOver
     *
     * @param delta the delta
     */
    private void drawEntities(float delta) {

        if (this.model.getLevelState() == MainModel.LevelState.RUNNING) {

            List<DuckModel> ducks = model.getDucks();

            for (DuckModel duck : ducks) {
                DuckView duckView;
                switch (duck.getType()){
                    case DEWEY:
                        duckView = deweyView; break;
                    case HUEY:
                        duckView = hueyView; break;
                    case LOUIE:
                        duckView = louieView; break;
                    default:
                        duckView = null;
                }

                duckView.update(duck, delta, model.getNumberOfDucks());
                duckView.draw(game.getBatch());

            }

            aimView.setAimZoom(camera.zoom);
            aimView.update(model.getAim());
            aimView.draw(game.getBatch());
            gameStatusView.update(model.getPlayerModel());
            gameStatusView.draw(game.getBatch());

        }

        else {

            String gameOver = "Game Over";
            String score = "Total Ducks Killed: " + Integer.toString(this.model.getPlayerModel().getScore());

            GlyphLayout layout = new GlyphLayout(game.getPineWoodFont(), gameOver);

            game.getPineWoodFont().draw(this.game.getBatch(), gameOver, MainController.getControllerWidth()/PIXEL_TO_METER/2 - layout.width/2f, MainController.getControllerHeight()/PIXEL_TO_METER*2/3 - layout.height/2f);

            layout.setText(game.getPineWoodFont(), score);
            game.getPineWoodFont().draw(this.game.getBatch(), score, MainController.getControllerWidth()/PIXEL_TO_METER/2 - layout.width/2f, MainController.getControllerHeight()/PIXEL_TO_METER/2 - layout.height/2f);

            
            gameMultiplexer.removeProcessor(gameInputProcessor);
            gameMultiplexer.addProcessor(new InputAdapter() {

                @Override
                public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                    GameScreen.this.game.setMenuScreen();
                    return true;
                }

            });

        }

    }

    /**
     * Draw background.
     */
    private void drawBackground() {

        Texture background = game.getAssetManager().get(BACKGROUND_GAME_IMAGE, Texture.class);
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        game.getBatch().draw(background, 0, 0, MainController.getControllerWidth() / PIXEL_TO_METER, MainController.getControllerHeight() / PIXEL_TO_METER);
    }

    /**
     * Gets the game controller.
     *
     * @return the game controller
     */
    public MainController getController() {
        return controller;
    }

    /**
     * Sets the input processor as the multiplexer with input processor and buttons stage.
     */
    public void setInputProcessor(){

        Gdx.input.setInputProcessor(this.gameMultiplexer);
    }


    /**
     * Gets the camera.
     *
     * @return the camera
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Gets the game status view.
     *
     * @return the game status view
     */
    public GameStatusView getGameStatusView() {
        return gameStatusView;
    }

    /**
     * Gets the game model.
     *
     * @return the game model
     */
    public MainModel getModel() {
        return model;
    }

    /**
     * Gets the initial aim position.
     *
     * @return the initial aim position
     */
    public Vector3 getInitialAimPosition() {
        return initialAimPosition;
    }

    /**
     * Gets the game stage with all Buttons used in game screen.
     *
     * @return the game stage
     */
    public Stage getGameStage() {
        return gameStage;
    }
}