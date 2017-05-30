package com.aimon.game;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.model.entities.PlayerModel;
import com.aimon.game.view.game.GameScreen;
import com.aimon.game.view.menu.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AimOn extends Game {

	private static int NUMBER_OF_DUCKS = 8;
	private static int NUMBER_OF_BULLETS = 2;
	public final String playerName = "Player 1";

	private SpriteBatch batch;
    private AssetManager assetManager;
	public BitmapFont font;

	private ScreenAdapter menuScreen;
	private ScreenAdapter gameScreen;
	private MainModel mainModel;
	private MainController mainController;
	private PlayerModel playerModel;
	public BitmapFont pineWoodFont;

    private Skin skin;

    @Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();
		font = new BitmapFont();
		initializeUIConfig();
		this.playerModel = new PlayerModel("Player 1", NUMBER_OF_BULLETS);

		this.mainModel = new MainModel(MainController.getControllerWidth()/2, MainController.getControllerHeight()/2, NUMBER_OF_DUCKS,playerModel);
		this.mainController = new MainController(this.mainModel);
		this.menuScreen = new MainMenuScreen(this);
		this.gameScreen = new GameScreen(this, this.mainModel, this.mainController);

		this.setMenuScreen();
	}

	public void setMenuScreen() {
		this.setScreen(menuScreen);
		((MainMenuScreen) this.menuScreen).setInputProcessor();

	}

	public void setGameScreen() {
		this.setScreen(gameScreen);
		((GameScreen) this.gameScreen).setInputProcessor();
		//Gdx.input.setCursorCatched(true);
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

	private void initializeUIConfig() {

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pinewood.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 60;
		pineWoodFont = generator.generateFont(parameter); // font size 60 pixels
		generator.dispose();

        skin = new Skin(Gdx.files.internal("glassy/glassy-ui.json"));

    }

    public Skin getSkin() {
        return skin;
    }

	public BitmapFont getPineWoodFont() {
		return pineWoodFont;
	}

	public MainModel getMainModel() {
		return mainModel;
	}
}
