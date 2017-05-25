package com.aimon.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.aimon.game.AimOn;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "AimOn";
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new AimOn(), config);
	}
}
