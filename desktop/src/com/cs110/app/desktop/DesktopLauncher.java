package com.cs110.app.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cs110.app.CS110App;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		if(arg.length > 0) {
			if(arg[0].equals("client")) {
				System.out.println("Client");
				new LwjglApplication(new CS110App(true), config);
			}
			else{
				System.out.println("Server");
				new LwjglApplication(new CS110App(false), config);
			}
		}
		else
			new LwjglApplication(new CS110App(false), config);
	}
}
