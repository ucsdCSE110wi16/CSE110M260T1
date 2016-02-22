package com.cs110.app;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cs110.app.Net.NetworkingClient;
import com.cs110.app.Net.NetworkingServer;
import com.cs110.app.Screens.GameScreen;

public class CS110App extends Game { //The automatically generated code has ApplicationAdapter, but
                                    // game allows for screens

	com.cs110.app.Net.NetworkingServer NS;
	com.cs110.app.Net.NetworkingClient NC;
	boolean client;
	public CS110App(boolean client) {
		this.client = client;
	}
	
	@Override
	public void create () {
		GameScreen screen = new GameScreen();
		setScreen(screen);
		try {
			//NS = new NetworkingServer(screen);
			if(client) {
				NC = new NetworkingClient(screen); // Uncomment to be client
				System.out.println("CLIENT");
			}
			else {
				NS = new NetworkingServer(screen);
				System.out.println("SERVER");
			}
		}
		catch(Exception e) {

		}

	}

	@Override
	public void render () {
        super.render();
	}

}
