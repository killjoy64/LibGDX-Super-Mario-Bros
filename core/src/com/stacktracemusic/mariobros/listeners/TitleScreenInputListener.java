package com.stacktracemusic.mariobros.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.stacktracemusic.mariobros.screens.TitleScreen;
import com.stacktracemusic.mariobros.util.Config;

/**
 * Created by Kyle Flynn on 4/7/2016.
 */
public class TitleScreenInputListener implements InputProcessor {

    private TitleScreen screen;

    private Vector3 touchPoint;

    public TitleScreenInputListener(TitleScreen instance) {
        screen = instance;
        touchPoint = new Vector3();
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.UP || keycode == Input.Keys.DOWN) {
            if (screen.getSelected() == 0) {
                screen.setSelected(1);
            } else {
                screen.setSelected(0);
            }
        }

        if (keycode == Input.Keys.ENTER) {
            if (screen.getSelected() == 0) {
                // Start 1 player game
                screen.startGame();
            } else {
                // Start 2 player game
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (button != Input.Buttons.LEFT || pointer > 0) return false;

        screen.getOverlayCam().unproject(touchPoint.set(screenX, screenY, 0));

        if (touchPoint.y > 85.0 && touchPoint.y < 105) {
            // Start 1 player game
            screen.startGame();
        } else {
            // Start 2 player game
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
