
package com.cs110.app;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cs110.app.Screens.GameScreen;

class CS110App extends Game { //The automatically generated code has ApplicationAdapter, but
                                    // game allows for screens
	SpriteBatch batch;
	Texture img;
	NetworkingServer NS;
	NetworkingClient NC;
	
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
