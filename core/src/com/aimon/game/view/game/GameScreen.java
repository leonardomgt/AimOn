package com.aimon.game.view.game;

import com.aimon.game.AimOn;
import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.view.game.entities.AimView;
import com.aimon.game.view.game.entities.DuckView;
import com.aimon.game.view.game.entities.GameStatusView;
import com.aimon.game.view.game.inputprocessors.*;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.List;


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

    public static final String BACKGROUND_GAME_IMAGE = "backgroundGame.jpg";

    private final MainController controller;

    private final DuckView hueyView;
    private final DuckView deweyView;
    private final DuckView louieView;

    private final GameStatusView gameStatusView;

    private final AimView aimView;

    private final MainModel model;

    private Vector3 aimPosition;

    private InputMultiplexer gameMultiplexer = new InputMultiplexer();

    private GameInputProcessor gameInputProcessor;
    private Stage gameStage = new Stage();
    private ImageButton buttonHome;
    private ImageButton buttonReload;
    private ImageButton buttonZoom;


    public GameScreen(AimOn game, MainModel model, MainController controller) {

        this.game = game;
        this.model = model;
        this.controller = controller;

        this.aimPosition = new Vector3(model.getAim().getX(), model.getAim().getY(), 0);

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
        this.gameStatusView = new GameStatusView(game, model.getPlayerModel());

        // create the camera and the SpriteBatch

        camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_HEIGHT / PIXEL_TO_METER);
        camera.position.set(MainController.getControllerWidth()  / PIXEL_TO_METER / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        this.debugMatrix = new Matrix4(this.camera.combined);
        debugMatrix.scale(1/PIXEL_TO_METER, 1/PIXEL_TO_METER, 1f);
        this.debugRenderer = new Box2DDebugRenderer();


        initializeUIElements();

        gameMultiplexer.addProcessor(gameStage);
        gameMultiplexer.addProcessor(gameInputProcessor);
    }

    private void initializeUIElements() {

        // Home Button
        buttonHome = new ImageButton(game.getSkin());
        buttonHome.setSize(buttonHome.getHeight()/1.2f,buttonHome.getHeight()/1.2f);

        ImageButton.ImageButtonStyle homeStyle = new ImageButton.ImageButtonStyle(buttonHome.getStyle());
        buttonHome.setStyle(homeStyle);

        buttonHome.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("home.png"))));
        buttonHome.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("home.png"))));
        buttonHome.getStyle().up = buttonHome.getSkin().getDrawable("button-small");
        buttonHome.getStyle().down = buttonHome.getSkin().getDrawable("button-small-down");
        buttonHome.setPosition(0, Gdx.graphics.getHeight()-buttonHome.getHeight());
        buttonHome.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                game.setMenuScreen();
            }
        });
        gameStage.addActor(buttonHome);


        if(Gdx.app.getType() == Application.ApplicationType.Android){
            initializeAndroidUIElements();
        }
    }

    private void initializeAndroidUIElements() {

        // Reload Button
        buttonReload = new ImageButton(game.getSkin());
        buttonReload.setSize(buttonReload.getHeight()/1, buttonReload.getHeight()/1);

        ImageButton.ImageButtonStyle reloadStyle = new ImageButton.ImageButtonStyle(buttonReload.getStyle());
        buttonReload.setStyle(reloadStyle);

        buttonReload.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ammo_empty.png"))));
        buttonReload.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ammo_empty.png"))));
        buttonReload.getStyle().up = buttonReload.getSkin().getDrawable("button");
        buttonReload.getStyle().down = buttonReload.getSkin().getDrawable("button-down");
        buttonReload.setPosition(Gdx.graphics.getWidth() - buttonReload.getWidth(), 0);
        buttonReload.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                gameInputProcessor.reloadGun();
            }
        });
        gameStage.addActor(buttonReload);


        // Zoom Button
        buttonZoom = new ImageButton(game.getSkin());
        buttonZoom.setSize(buttonZoom.getHeight()/1, buttonZoom.getHeight()/1);

        ImageButton.ImageButtonStyle fireStyle = new ImageButton.ImageButtonStyle(buttonZoom.getStyle());
        buttonZoom.setStyle(fireStyle);

        buttonZoom.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("fire.png"))));
        buttonZoom.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("fire.png"))));
        buttonZoom.getStyle().up = buttonZoom.getSkin().getDrawable("button"/*"button-small"*/);
        buttonZoom.getStyle().down = buttonZoom.getSkin().getDrawable("button-down"/*"button-small-down"*/);
        buttonZoom.setPosition(0, 0);
        buttonZoom.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                gameInputProcessor.shot();
            }
        });
        gameStage.addActor(buttonZoom);
    }

    private void loadAssets(){

        this.game.getAssetManager().load(BACKGROUND_GAME_IMAGE, Texture.class);

        this.gameInputProcessor.loadAssets();

        this.game.getAssetManager().finishLoading();

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        controller.update(delta);
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
}