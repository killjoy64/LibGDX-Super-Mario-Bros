package com.stacktracemusic.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stacktracemusic.mariobros.GameWindow;
import com.stacktracemusic.mariobros.entities.Mario;
import com.stacktracemusic.mariobros.listeners.TitleScreenInputListener;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.util.Resources;
import com.stacktracemusic.mariobros.world.GameData;
import com.stacktracemusic.mariobros.world.TileCreator;

/**
 * Created by Kyle Flynn on 4/7/2016.
 */
public class TitleScreen implements Screen {

    private GameWindow game;

    private OrthographicCamera worldCam;
    private OrthographicCamera overlayCam;
    private Viewport worldViewport;
    private Viewport overlayViewport;
    private Stage overlayStage;

    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    private World world;
    private Box2DDebugRenderer worldDebugger;
    private TileCreator tileCreator;
    private Mario player;

    private Texture menuTexture;
    private Texture cursorTexture;

    private BitmapFont font;
    private String[] options;
    private int selected;
    private GlyphLayout layoutOne;
    private GlyphLayout layoutTwo;

    private Music music;

    private boolean switchingScreens;

    public TitleScreen(GameWindow instance) {
        game = instance;

        worldCam = new OrthographicCamera();
        worldViewport = new FitViewport(Config.VIRTUAL_WIDTH / Config.SCALE, Config.VIRTUAL_HEIGHT / Config.SCALE, worldCam);

        overlayCam = new OrthographicCamera();
        overlayViewport = new FitViewport(Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT, overlayCam);
        overlayStage = new Stage(overlayViewport, game.getBatch());

        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("world-maps/title-screen.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / Config.SCALE);

        worldCam.position.set(worldViewport.getWorldWidth() / 2, worldViewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        worldDebugger = new Box2DDebugRenderer();
        tileCreator = new TileCreator(world, tiledMap, null);

        tileCreator.create();
        player = new Mario(world, tiledMap);

        music = game.getAssetManager().get(Resources.ABOVE_GROUND_BGM);

        music.setLooping(true);
        music.play();

        font = new BitmapFont(Gdx.files.internal("fonts/default.fnt"));
        font.getData().setScale(0.25f);

        options = new String[2];
        options[0] = "1 PLAYER GAME";
        options[1] = "2 PLAYER GAME";
        selected = 0;

        layoutOne = new GlyphLayout(font, options[0]);
        layoutTwo = new GlyphLayout(font, options[1]);

        menuTexture = new Texture(Gdx.files.internal("textures/titlescreen/title-screen.png"));
        cursorTexture = new Texture(Gdx.files.internal("textures/titlescreen/mushroom-cursor.png"));

        Gdx.input.setInputProcessor(new TitleScreenInputListener(this));

        Label optionOne = new Label(options[0], new Label.LabelStyle(font, Color.WHITE));
        Label optionTwo = new Label(options[1], new Label.LabelStyle(font, Color.WHITE));

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(optionOne).padTop(40);
        table.row();
        table.add(optionTwo).padTop(10);

        overlayStage.addActor(table);

        player.setPosition(32 / Config.SCALE, 128 / Config.SCALE);

        switchingScreens = false;
    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        player.update(delta);
        worldCam.update();
        mapRenderer.setView(worldCam);
    }

    @Override
    public void render(float delta) {
        if (!switchingScreens) {
            update(delta);

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            mapRenderer.render();
            //worldDebugger.render(world, worldCam.combined);

            game.getBatch().setProjectionMatrix(worldCam.combined);
            game.getBatch().begin();
            game.getBatch().draw(menuTexture, (worldViewport.getWorldWidth() / 6), (worldViewport.getWorldHeight() / 2), ((worldViewport.getWorldWidth() / 6) * 4), (worldViewport.getWorldHeight() / 2) - (10 / Config.SCALE));
            if (selected == 0) {
                game.getBatch().draw(cursorTexture, (worldViewport.getWorldWidth() / 2) - (layoutOne.width / 2 / Config.SCALE) - (10 / Config.SCALE), (95 - 6) / Config.SCALE, 8 / Config.SCALE, 8 / Config.SCALE);
            } else if (selected == 1) {
                game.getBatch().draw(cursorTexture, (worldViewport.getWorldWidth() / 2) - (layoutOne.width / 2 / Config.SCALE) - (10 / Config.SCALE), (77 - 6) / Config.SCALE, 8 / Config.SCALE, 8 / Config.SCALE);
            }
            player.draw(game.getBatch());
            game.getBatch().end();

            game.getBatch().setProjectionMatrix(overlayCam.combined);
            overlayStage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        worldViewport.update(width, height);
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
        switchingScreens = true;
        music.stop();
        worldDebugger.dispose();
        world.dispose();
        tiledMap.dispose();
        mapRenderer.dispose();
        overlayStage.dispose();
        font.dispose();
    }

    public void startGame() {
        dispose();
        game.setScreen(new WorldInfoScreen(game, new GameData(3, 0, "1-1")));
        game.getAssetManager().get(Resources.COIN, Sound.class).play();
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getSelected() {
        return selected;
    }

    public OrthographicCamera getWorldCamera() {
        return worldCam;
    }

    public OrthographicCamera getOverlayCam() {
        return overlayCam;
    }

    public World getWorld() {
        return world;
    }

}
