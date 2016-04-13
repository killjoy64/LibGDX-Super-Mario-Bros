package com.stacktracemusic.mariobros.listeners;

import com.badlogic.gdx.InputProcessor;
import com.stacktracemusic.mariobros.screens.WorldInfoScreen;

/**
 * Created by Kyle Flynn on 4/9/2016.
 */
public class WorldInfoScreenInputListener implements InputProcessor {

    private WorldInfoScreen screen;

    public WorldInfoScreenInputListener(WorldInfoScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
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
        return false;
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
