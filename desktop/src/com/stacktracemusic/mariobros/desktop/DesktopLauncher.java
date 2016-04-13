package com.stacktracemusic.mariobros.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stacktracemusic.mariobros.GameWindow;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 782;
		config.height = 636;
		config.resizable = false;

		new LwjglApplication(new GameWindow(), config);
	}
}
