package com.ruperthodgkins.csproject;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "CS Project";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 600;
		cfg.fullscreen = false;
		cfg.resizable = true;
		
		new LwjglApplication(new Game(), cfg);
	}
}
