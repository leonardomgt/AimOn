package com.aimon.game;

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

	private static int NUMBER_OF_DUCKS = 600;
	private static int NUMBER_OF_BULLETS = 700;
	public final String playerName = "Player 1";

	private SpriteBatch batch;
    private AssetManager assetManager;
	public BitmapFont font;

	private ScreenAdapter menuScreen;
	private ScreenAdapter gameScreen;

	public BitmapFont pineWoodFont;

    private Skin skin;

    @Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();
		font = new BitmapFont();
		initializeUIConfig();

		this.menuScreen = new MainMenuScreen(this);
		//this.gameScreen = new GameScreen(this, this.playerName, NUMBER_OF_DUCKS, NUMBER_OF_BULLETS);

		this.setMenuScreen();
	}

	public void setMenuScreen() {
		this.setScreen(menuScreen);
		((MainMenuScreen) this.menuScreen).setInputProcessor();

	}

	public void setGameScreen() {
		this.gameScreen = new GameScreen(this, this.playerName, NUMBER_OF_DUCKS, NUMBER_OF_BULLETS);
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

	public GameScreen getGameScreen() {
		return (GameScreen) this.gameScreen;
	}


}
