package com.stacktracemusic.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stacktracemusic.mariobros.GameWindow;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.util.Resources;
import com.stacktracemusic.mariobros.world.GameData;

/**
 * Created by Kyle Flynn on 4/10/2016.
 */
public class GameOverScreen implements Screen {

    private GameWindow game;
    private GameData gameData;

    private OrthographicCamera overlayCam;
    private Viewport overlayViewport;
    private Stage overlayStage;

    private float elapsedTime;

    public GameOverScreen(GameWindow game, GameData gameData) {
        this.game = game;
        this.gameData = gameData;

        overlayCam = new OrthographicCamera();
        overlayViewport = new FitViewport(Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT, overlayCam);
        overlayStage = new Stage(overlayViewport, game.getBatch());

        gameData.getGameFont().getData().setScale(0.75f);

        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.padTop(80);
        table.add(new Label("GAME OVER", new Label.LabelStyle(gameData.getGameFont(), Color.WHITE))).center();

        overlayStage.addActor(table);

        elapsedTime = 0;
    }

    @Override
    public void show() {
        game.getAssetManager().get(Resources.GAME_OVER, Sound.class).play();
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().setProjectionMatrix(overlayCam.combined);
        overlayStage.draw();

        if (elapsedTime >= 5) {
            dispose();
            game.setScreen(new TitleScreen(game));
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

    }

    @Override
    public void dispose() {
        overlayStage.dispose();
    }
}
