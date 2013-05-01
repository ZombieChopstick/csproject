package com.ruperthodgkins.csproject;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "CS Project";
		cfg.useGL20 = false;
		if(args.length > 0) {
			cfg.width = Integer.parseInt(args[0]);
			cfg.height = Integer.parseInt(args[1]);
			cfg.fullscreen = Boolean.parseBoolean(args[2]);
		}
		else {
			cfg.width = 1366;
			cfg.height = 768;
			cfg.fullscreen = true;
		}
		cfg.resizable = true;
		
		new LwjglApplication(new Game(), cfg);
	}
}
