
package com.cs110.app;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cs110.app.Net.NetworkingClient;
import com.cs110.app.Net.NetworkingServer;
import com.cs110.app.Screens.GameScreen;

public class CS110App extends Game { //The automatically generated code has ApplicationAdapter, but
                                    // game allows for screens
	SpriteBatch batch;
	Texture img;
	com.cs110.app.Net.NetworkingServer NS;
	com.cs110.app.Net.NetworkingClient NC;
	
	@Override
	public void create () {

		GameScreen screen = new GameScreen();
		setScreen(screen);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		try {
//			NS = new NetworkingServer(screen);
			NC = new NetworkingClient(screen); // Uncomment to be client
		}
		catch(Exception e) {

		}
        //super.render();

	}

	@Override
	public void render () {
        super.render();
	}

}
