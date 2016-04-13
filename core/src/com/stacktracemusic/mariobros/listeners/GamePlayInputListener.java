package com.stacktracemusic.mariobros.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.stacktracemusic.mariobros.screens.GamePlayScreen;
import com.stacktracemusic.mariobros.util.Config;

/**
 * Created by Kyle Flynn on 4/9/2016.
 */
public class GamePlayInputListener implements InputProcessor {

    private GamePlayScreen screen;

    private Vector3 touchPoint;

    private Array<Integer> touches;

    public GamePlayInputListener(GamePlayScreen screen) {
        this.screen = screen;
        touchPoint = new Vector3();
        touches = new Array<Integer>();
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.LEFT) {
            screen.getPlayer().getKeys().put(screen.getPlayer().LEFT, true);
        }
        if (keycode == Input.Keys.RIGHT) {
            screen.getPlayer().getKeys().put(screen.getPlayer().RIGHT, true);
        }
        if (keycode == Input.Keys.UP) {
            screen.getPlayer().getKeys().put(screen.getPlayer().UP, true);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.LEFT) {
            screen.getPlayer().getKeys().put(screen.getPlayer().LEFT, false);
        }
        if (keycode == Input.Keys.RIGHT) {
            screen.getPlayer().getKeys().put(screen.getPlayer().RIGHT, false);
        }
        if (keycode == Input.Keys.UP) {
            screen.getPlayer().getKeys().put(screen.getPlayer().UP, false);
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screen.getOverlayCam().unproject(touchPoint.set(screenX, screenY, 0));

        if (touchPoint.x > Config.VIRTUAL_WIDTH / 2) {
            screen.getPlayer().getKeys().put(screen.getPlayer().RIGHT, true);
        } else if (touchPoint.x < Config.VIRTUAL_WIDTH / 2) {
            screen.getPlayer().getKeys().put(screen.getPlayer().LEFT, true);
        }

        if (pointer == 1) {
            screen.getPlayer().getKeys().put(screen.getPlayer().UP, true);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        screen.getOverlayCam().unproject(touchPoint.set(screenX, screenY, 0));

        if (touchPoint.x > Config.VIRTUAL_WIDTH / 2) {
            screen.getPlayer().getKeys().put(screen.getPlayer().RIGHT, false);
        } else if (touchPoint.x < Config.VIRTUAL_WIDTH / 2) {
            screen.getPlayer().getKeys().put(screen.getPlayer().LEFT, false);
        }

        if (pointer == 1) {
            screen.getPlayer().getKeys().put(screen.getPlayer().UP, false);
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
