package com.aimon.game.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

/**
 * Contains all buttons used in Main-Menu screen
 */

public class MainMenuButtons implements Disposable {

    /** The Main-Menu screen. */
    final MainMenuScreen mainMenu;

    /** The stage containing all Buttons. */
    public Stage stage;
    
    /** The button Play. Changes to a GameScreen and starts the game. */
    private TextButton buttonPlay;
    
    /** The button Sound. Mute the game sounds. */
    private final ImageButton buttonSound;
    
    /** The button Exit. Close the program.*/
    private final ImageButton buttonExit;


    /**
     * Instantiates a new object MainMenuButtons and creates all its the Buttons.
     *
     * @param mainMenu the Main-Menu screen
     */
    public MainMenuButtons(final MainMenuScreen mainMenu) {

        this.mainMenu = mainMenu;
        stage = new Stage();


        // Text Button
        buttonPlay = new TextButton("PLAY",mainMenu.game.getSkin());
        //button2.setSize(col_width*4,row_height);
        buttonPlay.setPosition(Gdx.graphics.getWidth()/2f - buttonPlay.getWidth()/2f, Gdx.graphics.getHeight()/2f - buttonPlay.getHeight()/2f);
        buttonPlay.getStyle().over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("button-over.png"))));
        buttonPlay.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainMenu.game.setGameScreen();
            }
        });
        stage.addActor(buttonPlay);


        // Sound Button
        buttonSound = new ImageButton(mainMenu.game.getSkin());
        buttonSound.setSize(buttonSound.getHeight()/1.2f, buttonSound.getHeight()/1.2f);

        ImageButton.ImageButtonStyle soundStyle = new ImageButton.ImageButtonStyle(buttonSound.getStyle());
        buttonSound.setStyle(soundStyle);

        buttonSound.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sound_on.png"))));
        buttonSound.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sound_on.png"))));
        buttonSound.getStyle().up = buttonSound.getSkin().getDrawable("button-small");
        buttonSound.getStyle().down = buttonSound.getSkin().getDrawable("button-small-down");
        buttonSound.setPosition(Gdx.graphics.getWidth()- 2*buttonSound.getWidth(), Gdx.graphics.getHeight()- buttonSound.getHeight());
        buttonSound.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainMenu.game.setSoundOn(!mainMenu.game.isSoundOn());
            }
        });
        stage.addActor(buttonSound);


        // Exit Button
        buttonExit = new ImageButton(mainMenu.game.getSkin());
        buttonExit.setSize(buttonExit.getHeight()/1.2f, buttonExit.getHeight()/1.2f);

        ImageButton.ImageButtonStyle settingsStyle = new ImageButton.ImageButtonStyle(buttonExit.getStyle());
        buttonExit.setStyle(settingsStyle);

        buttonExit.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("cross.png"))));
        buttonExit.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("cross.png"))));
        buttonExit.getStyle().up = buttonExit.getSkin().getDrawable("button-small");
        buttonExit.getStyle().down = buttonExit.getSkin().getDrawable("button-small-down");
        buttonExit.setPosition(Gdx.graphics.getWidth()- buttonExit.getWidth(), Gdx.graphics.getHeight()- buttonExit.getHeight());
        buttonExit.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                System.exit(0);
            }
        });
        stage.addActor(buttonExit);

    }

    /**
     * Update the buttons stage.
     *
     * @param delta the delta time since last call
     */
    public void update(float delta){
        stage.act(delta);
        stage.draw();
    }


    /**
     * Dispose the elements used in Main-Menu.
     */
    @Override
    public void dispose() {
        stage.dispose();
        mainMenu.game.getSkin().dispose();
        buttonPlay.clearListeners();
        buttonSound.clearListeners();
        buttonExit.clearListeners();
    }


    /**
     * Gets the buttons stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }
}
