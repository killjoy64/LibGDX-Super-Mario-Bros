package com.stacktracemusic.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stacktracemusic.mariobros.GameWindow;
import com.stacktracemusic.mariobros.entities.Mario;
import com.stacktracemusic.mariobros.listeners.GamePlayInputListener;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.util.Resources;
import com.stacktracemusic.mariobros.world.GameData;
import com.stacktracemusic.mariobros.world.TileCreator;

/**
 * Created by Kyle Flynn on 4/9/2016.
 */
public class GamePlayScreen implements Screen {

    private GameWindow game;
    private GameData gameData;

    private Viewport overlayViewport;
    private OrthographicCamera overlayCam;
    private Stage overlayStage;

    private Viewport gameViewport;
    private OrthographicCamera gameCam;

    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    private World world;
    private Box2DDebugRenderer worldDebugger;
    private TileCreator tileCreator;

    private Mario player;

    private Music music;

    private float timer;

    public GamePlayScreen(GameWindow instance, GameData gameData) {
        game = instance;
        this.gameData = gameData;

        gameData.setTime(300);

        overlayCam = new OrthographicCamera();
        overlayViewport = new FitViewport(Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT, overlayCam);
        overlayStage = new Stage(overlayViewport, game.getBatch());

        gameCam = new OrthographicCamera();
        gameViewport = new FitViewport(Config.VIRTUAL_WIDTH / Config.SCALE, Config.VIRTUAL_HEIGHT / Config.SCALE, gameCam);

        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("world-maps/world-" + gameData.getLevel() + ".tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / Config.SCALE);

        world = new World(new Vector2(0, -10), true);
        worldDebugger = new Box2DDebugRenderer();
        tileCreator = new TileCreator(world, tiledMap);
        player = new Mario(world, tiledMap);

        player.setAssetManager(game.getAssetManager());
        tileCreator.create();

        gameCam.position.set(gameViewport.getWorldWidth() / 2, gameViewport.getWorldHeight() / 2, 0);

        music = game.getAssetManager().get(Resources.ABOVE_GROUND_BGM);

        music.setLooping(true);
        music.play();

        Gdx.input.setInputProcessor(new GamePlayInputListener(this));

        overlayStage.addActor(gameData.getHUDTable());

        timer = 0;
    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        player.update(delta);
        gameCam.update();
        mapRenderer.setView(gameCam);

        if (player.isDead() == false) {
            gameData.update(delta);

            if (player.getEntityBody().getPosition().x < 1.28f) {
                gameCam.position.x = 1.28f;
            } else {
                gameCam.position.x = player.getEntityBody().getPosition().x;
            }
        }

    }

    @Override
    public void render(float delta) {
        update(delta);

        if (player.isDead() == false) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            mapRenderer.render();
            //worldDebugger.render(world, gameCam.combined);

            game.getBatch().setProjectionMatrix(gameCam.combined);
            game.getBatch().begin();
            player.draw(game.getBatch());
            game.getBatch().end();

            game.getBatch().setProjectionMatrix(overlayCam.combined);
            overlayStage.draw();

        } else {
            if (timer >= 3.0) {
                int currLives = gameData.getLives();
                if (currLives > 0) {
                    gameData.setLives(currLives - 1);
                    dispose();
                    game.setScreen(new WorldInfoScreen(game, gameData));
                } else {
                    dispose();
                    game.setScreen(new GameOverScreen(game, gameData));
                }
            } else {
                game.getBatch().setProjectionMatrix(gameCam.combined);
                game.getBatch().begin();
                player.draw(game.getBatch());
                game.getBatch().end();

                if (music.isPlaying()) {
                    music.stop();
                }
                timer += delta;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
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
        worldDebugger.dispose();
        world.dispose();
        tiledMap.dispose();
        mapRenderer.dispose();
        overlayStage.dispose();
    }

    public Mario getPlayer() {
        return player;
    }

    public OrthographicCamera getOverlayCam() {
        return overlayCam;
    }

}
