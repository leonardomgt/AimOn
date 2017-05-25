package com.aimon.game.view.game;

import com.aimon.game.AimOn;
import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.DuckModel;
import com.aimon.game.view.game.entities.AimView;
import com.aimon.game.view.game.entities.DuckView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.List;


/**
 * Created by Leo on 18/04/2017.
 */


public class GameScreen extends ScreenAdapter {

    private final AimOn game;
    private final OrthographicCamera camera;
    private Matrix4 debugMatrix;
    private Box2DDebugRenderer debugRenderer;

    public final static float PIXEL_TO_METER = .85f / (114 / 3f);
    public static final float VIEWPORT_WIDTH = 22.5f;
    public static final float  VIEWPORT_HEIGHT = VIEWPORT_WIDTH*((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());

    public static final String AIM_IMAGE = "arm-cross.png";
    public static final String BACKGROUND_GAME_IMAGE = "backgroundGame.jpg";
    private static final String DEWEY_SPRITE_RIGHT = "dewey_right.png";
    private static final String DEWEY_SPRITE_LEFT = "dewey_left.png";
    private static final String HUEY_SPRITE_RIGHT = "huey_right.png";
    private static final String HUEY_SPRITE_LEFT = "huey_left.png";
    private static final String LOUIE_SPRITE_RIGHT = "louie_right.png";
    private static final String LOUIE_SPRITE_LEFT = "louie_left.png";

    public static final String DEWEY_DEAD = "dewey_dead.png";
    public static final String DEWEY_SHOT = "dewey_shot.png";
    public static final String HUEY_DEAD = "huey_dead.png";
    public static final String HUEY_SHOT = "huey_shot.png";
    public static final String LOUIE_DEAD = "louie_dead.png";
    public static final String LOUIE_SHOT = "louie_shot.png";

    private static final String SHOT = "shotgun.wav";

    private static float camera_zoom = 1f;

    private final MainController controller;

    private final DuckView hueyView;
    private final DuckView deweyView;
    private final DuckView louieView;

    private final AimView aimView;

    private final MainModel model;
    private int aimX, aimY;

    private Stage stage = new Stage();
    private Vector3 aimPosition = new Vector3();

    private InputProcessor gameInputProcessor = new GameInputProcessor(this);

    private final Sound shotSoundEffect;

    public GameScreen(AimOn game, MainModel model, MainController controller) {

//        Gdx.input.setInputProcessor(stage);

        this.game = game;
        this.model = model;
        this.controller = controller;

        this.loadAssets();

        this.deweyView = new DuckView(game, DuckModel.DuckType.DEWEY);
        this.hueyView = new DuckView(game, DuckModel.DuckType.HUEY);
        this.louieView = new DuckView(game, DuckModel.DuckType.LOUIE);
        this.aimView = new AimView(game);

        // create the camera and the SpriteBatch

        camera = new OrthographicCamera(camera_zoom *VIEWPORT_WIDTH / PIXEL_TO_METER, camera_zoom *VIEWPORT_HEIGHT / PIXEL_TO_METER);
        //camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.position.set(MainController.getControllerWidth()  / PIXEL_TO_METER / 2f, camera.viewportHeight / 2f, 0);
        System.out.println("camera x: " + camera.position.x + "camera y:" + camera.position.y);
        System.out.println("camera largura: " + camera.viewportWidth + "camera altura:" + camera.viewportHeight);
        camera.update();

        this.debugMatrix = new Matrix4(this.camera.combined);
        debugMatrix.scale(1/PIXEL_TO_METER, 1/PIXEL_TO_METER, 1f);
        this.debugRenderer = new Box2DDebugRenderer();

        this.shotSoundEffect = this.game.getAssetManager().get(SHOT);

        initializeMousePosition();
    }

    private void initializeMousePosition() {

        this.aimX = Gdx.input.getX();
        this.aimY = Gdx.input.getY();
    }

    private void loadAssets(){

        this.game.getAssetManager().load(AIM_IMAGE, Texture.class);

        this.game.getAssetManager().load(DEWEY_SPRITE_RIGHT, Texture.class);
        this.game.getAssetManager().load(DEWEY_SPRITE_LEFT, Texture.class);

        this.game.getAssetManager().load(HUEY_SPRITE_RIGHT, Texture.class);
        this.game.getAssetManager().load(HUEY_SPRITE_LEFT, Texture.class);

        this.game.getAssetManager().load(LOUIE_SPRITE_RIGHT, Texture.class);
        this.game.getAssetManager().load(LOUIE_SPRITE_LEFT, Texture.class);

        this.game.getAssetManager().load(DEWEY_DEAD, Texture.class);
        this.game.getAssetManager().load(DEWEY_SHOT, Texture.class);
        this.game.getAssetManager().load(HUEY_DEAD, Texture.class);
        this.game.getAssetManager().load(HUEY_SHOT, Texture.class);
        this.game.getAssetManager().load(LOUIE_DEAD, Texture.class);
        this.game.getAssetManager().load(LOUIE_SHOT, Texture.class);

        this.game.getAssetManager().load(BACKGROUND_GAME_IMAGE, Texture.class);
        this.game.getAssetManager().load(SHOT, Sound.class);

        this.game.getAssetManager().finishLoading();

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //updateAim();

        controller.update(delta);


        updateBatch(delta);

        //debugRenderer.render(controller.getWorld(), debugMatrix);


        updateAim();

        camera.zoom = camera_zoom;

        camera.update();



    }

    private void updateAim() {

        this.aimPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(this.aimPosition);

        this.aimPosition.set(aimPosition.x * PIXEL_TO_METER, aimPosition.y*PIXEL_TO_METER, 0);
        controller.updateAimLocation(this.aimPosition.x,this.aimPosition.y);


        if(camera_zoom == 0.5f) {
            System.out.println("   aimPosition" + this.aimPosition);
            //this.camera.position.set(aimPosition.x /PIXEL_TO_METER , aimPosition.y /PIXEL_TO_METER ,0);

        }

    }

    public Vector3 getAimPosition() {
        System.out.println("x: " + aimPosition.x + "y:" + aimPosition.y);
        return aimPosition;
    }

    public void changeZoom() {

        if(camera_zoom == 1) {

            System.out.println("   Camera: " + this.camera.position);


            this.camera.position.set(aimPosition.x /PIXEL_TO_METER, aimPosition.y /PIXEL_TO_METER,0);
            camera_zoom = 0.5f;

            camera.update();

            Vector2 aimPositionScreen = new Vector2(-camera_zoom*(Gdx.input.getX() - Gdx.graphics.getWidth()/2f), -camera_zoom*(Gdx.graphics.getHeight()/2f - Gdx.input.getY()));

            this.camera.translate(aimPositionScreen.x, aimPositionScreen.y);

        }

        else {
            camera_zoom = 1;
            camera.position.set(MainController.getControllerWidth()  / PIXEL_TO_METER / 2f, camera.viewportHeight / 2f, 0);
        }


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

        aimView.setAimZoom(camera_zoom);
        aimView.update(model.getAim());
        aimView.draw(game.getBatch());

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
        Gdx.input.setInputProcessor(this.gameInputProcessor);
    }

    public void shot() {
        this.controller.shotFired(this.aimPosition.x, this.aimPosition.y);
        this.shotSoundEffect.play();
    }
}

