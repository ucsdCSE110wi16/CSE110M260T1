package com.cs110.app.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cs110.app.CS110App;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		if(arg.length > 0) {
			if(arg[0].equals("local")) {
				System.out.println("Local Client");
				new LwjglApplication(new CS110App(0), config);
			}
			else if(arg[0].equals("gameonly")) {
				System.out.println("Game only");
				new LwjglApplication(new CS110App(1), config);
			}
			else{
				System.out.println("Normal Connect to backed Server Client");
				new LwjglApplication(new CS110App(2), config);
			}
		}
		else
			new LwjglApplication(new CS110App(false), config);
	}
}
