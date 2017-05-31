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
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.List;

import static com.aimon.game.model.MainModel.LevelState.NEXT_LEVEL;


/**
 * Created by Leo on 18/04/2017.
 */


public class GameScreen extends ScreenAdapter {

    public final AimOn game;
    private final OrthographicCamera camera;
    private Matrix4 debugMatrix;
    private Box2DDebugRenderer debugRenderer;

    public final static float PIXEL_TO_METER = .85f / (114 / 3f);
    public static final float VIEWPORT_WIDTH = 22.5f;
    public static final float  VIEWPORT_HEIGHT = VIEWPORT_WIDTH*((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());

    private static final int BONUS_LEVEL = 0;
    private static final int BONUS_LEVEL_DUCKS = 500;
    private static final int BONUS_LEVEL_BULLETS = 494;

    public static final String BACKGROUND_GAME_IMAGE = "backgroundGame.jpg";

    private final DuckView hueyView;
    private final DuckView deweyView;
    private final DuckView louieView;

    private GameStatusView gameStatusView;

    private final AimView aimView;

    private Vector3 aimPosition;
    private final Vector3 initialAimPosition;

    private InputMultiplexer gameMultiplexer = new InputMultiplexer();

    private GameInputProcessor gameInputProcessor;
    private Stage gameStage = new Stage();



    private MainModel model;
    private MainController controller;
    private PlayerModel playerModel;

    private int initialNumberOfDucks;
    private int initialNumberOfBullets;
    private int level = 0;

    private boolean bonus = false;

    public GameScreen(AimOn game, String playerName, int initialNumberOfDucks, int initialNumberOfBullets) {

        this.game = game;
        this.initialNumberOfDucks = initialNumberOfDucks;
        this.initialNumberOfBullets = initialNumberOfBullets;
        this.playerModel = new PlayerModel(playerName, initialNumberOfBullets);

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

        this.debugMatrix = new Matrix4(this.camera.combined);
        debugMatrix.scale(1/PIXEL_TO_METER, 1/PIXEL_TO_METER, 1f);
        this.debugRenderer = new Box2DDebugRenderer();


        gameInputProcessor.initializeUIElements();

        gameMultiplexer.addProcessor(gameStage);
        gameMultiplexer.addProcessor(gameInputProcessor);

    }

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
            if (initialNumberOfBullets + this.playerModel.getGun().getCapacity() < initialNumberOfDucks) {

                this.bonus = true;
                this.setModelController(BONUS_LEVEL_DUCKS, BONUS_LEVEL_BULLETS, BONUS_LEVEL);

            }
            else {

                this.setModelController(initialNumberOfDucks, initialNumberOfBullets, this.level);

            }


        }

    }

    private void setModelController(int initialNumberOfDucks, int initialNumberOfBullets, int level) {

        this.playerModel.setNumberOfBullets(initialNumberOfBullets);
        this.playerModel.reset();
        this.model = new MainModel(MainController.getControllerWidth()/2, MainController.getControllerHeight()/2, initialNumberOfDucks, this.playerModel, level);
        this.controller = new MainController(this.model);
        this.gameStatusView = new GameStatusView(game, model);

    }


    private void loadAssets(){

        this.game.getAssetManager().load(BACKGROUND_GAME_IMAGE, Texture.class);

        this.gameInputProcessor.loadAssets();

        this.game.getAssetManager().finishLoading();

    }

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

        //debugRenderer.render(controller.getWorld(), debugMatrix);

        gameInputProcessor.updateAim();

        gameStage.act(delta);
        gameStage.draw();
        camera.update();

    }


    public Vector3 getAimPosition() {
        return aimPosition;
    }


    private void updateBatch(float delta) {
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        drawBackground();
        drawEntities(delta);
        game.getBatch().end();

    }

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

    private void drawBackground() {

        Texture background = game.getAssetManager().get(BACKGROUND_GAME_IMAGE, Texture.class);
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        game.getBatch().draw(background, 0, 0, MainController.getControllerWidth() / PIXEL_TO_METER, MainController.getControllerHeight() / PIXEL_TO_METER);
    }

    public MainController getController() {
        return controller;
    }

    public void setInputProcessor(){

        Gdx.input.setInputProcessor(this.gameMultiplexer);
    }



    public OrthographicCamera getCamera() {
        return camera;
    }

    public GameStatusView getGameStatusView() {
        return gameStatusView;
    }

    public MainModel getModel() {
        return model;
    }

    public Vector3 getInitialAimPosition() {
        return initialAimPosition;
    }

    public Stage getGameStage() {
        return gameStage;
    }
}