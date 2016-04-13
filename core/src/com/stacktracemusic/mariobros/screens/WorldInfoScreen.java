package com.stacktracemusic.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stacktracemusic.mariobros.GameWindow;
import com.stacktracemusic.mariobros.listeners.WorldInfoScreenInputListener;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.util.Resources;
import com.stacktracemusic.mariobros.world.GameData;

/**
 * Created by Kyle Flynn on 4/9/2016.
 */
public class WorldInfoScreen implements Screen {

    private GameWindow game;
    private GameData gameData;

    private Viewport overlayViewport;
    private OrthographicCamera overlayCam;
    private Stage overlayStage;

    private TextureRegion marioTexture;

    private float timeElapsed;

    public WorldInfoScreen(GameWindow instance, GameData gameData) {
        game = instance;
        this.gameData = gameData;
        this.timeElapsed = 0;

        gameData.setTime(0);

        overlayCam = new OrthographicCamera();
        overlayViewport = new FitViewport(Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT, overlayCam);
        overlayStage = new Stage(overlayViewport, game.getBatch());

        marioTexture = new TextureRegion(new Texture(Resources.MARIO_SPRITE_SHEET), 0, 0, 16, 16);

        Gdx.input.setInputProcessor(new WorldInfoScreenInputListener(this));

        overlayStage.addActor(gameData.getHUDTable());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (timeElapsed >= 3.0) {
            dispose();
            game.setScreen(new GamePlayScreen(game, gameData));
        } else {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.getBatch().begin();
            gameData.getGameFont().draw(game.getBatch(), "WORLD " + gameData.getLevel(), (Config.VIRTUAL_WIDTH / 2) - 30, (Config.VIRTUAL_HEIGHT / 2) + 45);
            game.getBatch().draw(marioTexture, (Config.VIRTUAL_WIDTH / 2) - 32, (Config.VIRTUAL_HEIGHT / 2) + 12, 16, 16);
            gameData.getGameFont().draw(game.getBatch(), "x  " + gameData.getLives(), (Config.VIRTUAL_WIDTH / 2) - 6, (Config.VIRTUAL_HEIGHT / 2) + 22);
            game.getBatch().end();

            game.getBatch().setProjectionMatrix(overlayCam.combined);
            overlayStage.draw();

            timeElapsed += delta;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        overlayStage.dispose();
    }

    @Override
    public void dispose() {

    }
}
