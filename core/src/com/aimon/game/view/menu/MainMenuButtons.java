package com.aimon.game.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Leo on 24/05/2017.
 */

public class MainMenuButtons implements Disposable {

    final MainMenuScreen mainMenu;

    public Stage stage;
    private Table table;
    private TextButton buttonPlay;
    private final ImageButton buttonSound;
    private final ImageButton buttonSettings;


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


        // Settings Button
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
                mainMenu.game.setGameScreen();
            }
        });
        stage.addActor(buttonSound);


        // Settings Button
        buttonSettings = new ImageButton(mainMenu.game.getSkin());
        buttonSettings.setSize(buttonSettings.getHeight()/1.2f,buttonSettings.getHeight()/1.2f);

        ImageButton.ImageButtonStyle settingsStyle = new ImageButton.ImageButtonStyle(buttonSettings.getStyle());
        buttonSettings.setStyle(settingsStyle);

        buttonSettings.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings.png"))));
        buttonSettings.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings.png"))));
        buttonSettings.getStyle().up = buttonSettings.getSkin().getDrawable("button-small");
        buttonSettings.getStyle().down = buttonSettings.getSkin().getDrawable("button-small-down");
        buttonSettings.setPosition(Gdx.graphics.getWidth()- buttonSettings.getWidth(), Gdx.graphics.getHeight()-buttonSettings.getHeight());
        buttonSettings.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainMenu.game.setGameScreen();
            }
        });
        stage.addActor(buttonSettings);

    }

    public void update(float delta){
        stage.act(delta);
        stage.draw();
    }


    @Override
    public void dispose() {
        stage.dispose();
        mainMenu.game.getSkin().dispose();
        buttonPlay.clearListeners();
    }


    public Stage getStage() {
        return stage;
    }
}
