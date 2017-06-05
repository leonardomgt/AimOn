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

/**
 * The Class AimOn.
 */
public class AimOn extends Game {

	/** The number of ducks. */
	private static int NUMBER_OF_DUCKS = 6;
	
	/** The number of bullets. */
	private static int NUMBER_OF_BULLETS = 9;
	
	/** The player name. */
	public final String playerName = "Player 1";

	/** The batch. */
	private SpriteBatch batch;
    
    /** The asset manager. */
    private AssetManager assetManager;
	
	/** The font. */
	public BitmapFont font;

	/** The menu screen. */
	private ScreenAdapter menuScreen;
	
	/** The game screen. */
	private ScreenAdapter gameScreen;

	/** The pine wood font. */
	private BitmapFont pineWoodFont;

    /** The skin. */
    private Skin skin;

	/** The sound on. */
	private boolean soundOn = true;


    /**
     * Creates the.
     */
    @Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();
		font = new BitmapFont();
		initializeUIConfig();

		this.menuScreen = new MainMenuScreen(this);

		this.setMenuScreen();
	}

	/**
	 * Sets the menu screen.
	 */
	public void setMenuScreen() {
		this.setScreen(menuScreen);
		((MainMenuScreen) this.menuScreen).setInputProcessor();
		Gdx.input.setCursorCatched(false);

	}

	/**
	 * Sets the game screen.
	 */
	public void setGameScreen() {
		this.gameScreen = new GameScreen(this, this.playerName, NUMBER_OF_DUCKS, NUMBER_OF_BULLETS);
		this.setScreen(gameScreen);
		((GameScreen) this.gameScreen).setInputProcessor();
		Gdx.input.setCursorCatched(true);
	}

	/**
	 * Render.
	 */
	@Override
	public void render () {
		super.render();
	}
	
	/**
	 * Dispose.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		assetManager.dispose();
	}

    /**
     * Gets the batch.
     *
     * @return the batch
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Gets the asset manager.
     *
     * @return the asset manager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

	/**
	 * Initialize UI config.
	 */
	private void initializeUIConfig() {

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pinewood.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 60;
		pineWoodFont = generator.generateFont(parameter); // font size 60 pixels
		generator.dispose();

        skin = new Skin(Gdx.files.internal("glassy/glassy-ui.json"));

    }

    /**
     * Gets the skin.
     *
     * @return the skin
     */
    public Skin getSkin() {
        return skin;
    }

	/**
	 * Gets the game screen.
	 *
	 * @return the game screen
	 */
	public GameScreen getGameScreen() {
		return (GameScreen) this.gameScreen;
	}

	/**
	 * Gets the pine wood font.
	 *
	 * @return the pine wood font
	 */
	public BitmapFont getPineWoodFont() {
		return pineWoodFont;
	}

	/**
	 * Checks if is sound on.
	 *
	 * @return true, if is sound on
	 */
	public boolean isSoundOn() {
		return soundOn;
	}

	/**
	 * Sets the sound on.
	 *
	 * @param soundOn the new sound on
	 */
	public void setSoundOn(boolean soundOn) {
		this.soundOn = soundOn;
	}
}
