package com.stacktracemusic.mariobros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stacktracemusic.mariobros.screens.TitleScreen;
import com.stacktracemusic.mariobros.util.Resources;

public class GameWindow extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		assetManager.load(Resources.ABOVE_GROUND_BGM, Music.class);
		assetManager.load(Resources.COIN, Sound.class);
		assetManager.load(Resources.MARIO_SMALL_JUMP_SOUND, Sound.class);
		assetManager.load(Resources.MARIO_SUPER_JUMP_SOUND, Sound.class);
		assetManager.load(Resources.MARIO_DIE, Sound.class);
		assetManager.load(Resources.GAME_OVER, Sound.class);
		assetManager.finishLoading();

		setScreen(new TitleScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

}
