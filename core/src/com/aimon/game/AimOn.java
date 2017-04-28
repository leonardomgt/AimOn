package com.aimon.game;

import com.aimon.game.controller.MainController;
import com.aimon.game.model.MainModel;
import com.aimon.game.view.game.GameScreen;
import com.aimon.game.view.menu.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AimOn extends Game {
	private SpriteBatch batch;
    private AssetManager assetManager;
	public BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();
		font = new BitmapFont();

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {super.render();}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
