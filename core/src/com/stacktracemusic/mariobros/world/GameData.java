package com.stacktracemusic.mariobros.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


/**
 * Created by Kyle Flynn on 4/9/2016.
 */
public class GameData {

    private int lives;
    private int coins;
    private String level;
    private int time;
    private int score;

    private Label timeDisplay;
    private Label worldDisplay;
    private Label coinsDisplay;
    private Label scoreDisplay;

    private float timeElapsed;

    private BitmapFont gameFont;

    public GameData() {
        this.lives = 3;
        this.coins = 0;
        this.level = "1-1";
        this.time = 0;
        this.score = 0;
        this.timeElapsed = 0;

        gameFont = new BitmapFont(Gdx.files.internal("fonts/default.fnt"));
    }

    public GameData(int lives, int coins, String world) {
        this.lives = lives;
        this.coins = coins;
        this.level = world;
        this.score = 0;
        this.time = 0;
        this.timeElapsed = 0;

        gameFont = new BitmapFont(Gdx.files.internal("fonts/default.fnt"));
    }

    public void update(float delta) {
        timeElapsed += delta;
        if (timeElapsed >= 1) {
            if (time > 0) {
                time--;
            } else {
                // Time is up!
            }
            timeDisplay.setText(String.format("%03d", time));
            timeElapsed = 0;
        }
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setLevel(String world) {
        this.level = world;
    }

    public int getLives() {
        return lives;
    }

    public int getCoins() {
        return coins;
    }

    public String getLevel() {
        return level;
    }

    public BitmapFont getGameFont() {
        return gameFont;
    }

    public Table getHUDTable() {
        gameFont.getData().setScale(0.2f);

        Label coinLabel = new Label("COINS", new Label.LabelStyle(gameFont, Color.WHITE));
        Label marioLabel = new Label("MARIO", new Label.LabelStyle(gameFont, Color.WHITE));
        Label worldLabel = new Label("WORLD", new Label.LabelStyle(gameFont, Color.WHITE));
        Label timeLabel = new Label("TIME", new Label.LabelStyle(gameFont, Color.WHITE));
        timeDisplay = new Label(String.format("%03d", this.time), new Label.LabelStyle(gameFont, Color.WHITE));
        worldDisplay = new Label(this.level, new Label.LabelStyle(gameFont, Color.WHITE));
        coinsDisplay = new Label(String.format("%02d", this.coins), new Label.LabelStyle(gameFont, Color.WHITE));
        scoreDisplay = new Label(String.format("%06d", this.score), new Label.LabelStyle(gameFont, Color.WHITE));

        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.padTop(5);
        table.add(marioLabel).expandX().center().padLeft(25);
        table.add(coinLabel).expandX().center();
        table.add(worldLabel).expandX().center();
        table.add(timeLabel).expandX().center().padRight(25);
        table.row();
        table.padTop(5);
        table.add(scoreDisplay).expandX().center().padLeft(25);
        table.add(coinsDisplay).expandX().center();
        table.add(worldDisplay).expandX().center();
        table.add(timeDisplay).expandX().center().padRight(25);

        return table;
    }

}
