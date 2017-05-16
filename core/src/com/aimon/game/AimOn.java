package com.aimon.game;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.view.game.GameScreen;
import com.aimon.game.view.menu.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AimOn extends Game {

	private static final int NUMBER_OF_DUCKS = 10;

	private SpriteBatch batch;
    private AssetManager assetManager;
	public BitmapFont font;

	private ScreenAdapter menuScreen;
	private ScreenAdapter gameScreen;
	private MainModel mainModel;
	private MainController mainController;


	@Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();
		font = new BitmapFont();
		this.mainModel = new MainModel(MainController.getControllerWidth()/2, MainController.getControllerHeight()/2, NUMBER_OF_DUCKS);
		this.mainController = new MainController(this.mainModel);
		this.menuScreen = new MainMenuScreen(this);
		this.gameScreen = new GameScreen(this, this.mainModel, this.mainController);
		this.setMenuScreen();
	}

	public void setMenuScreen() {
		this.setScreen(menuScreen);
	}

	public void setGameScreen() {
		this.setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		assetManager.dispose();
	}

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }


}
