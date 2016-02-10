
package com.cs110.app;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cs110.app.Screens.GameScreen;

public class CS110App extends Game { //The automatically generated code has ApplicationAdapter, but
                                    // game allows for screens

	SpriteBatch batch;
	Texture img;
	NetworkingClient NC;
	@Override
	public void create (){

		GameScreen screen = new GameScreen();
        super.setScreen(screen);
		try {
			NC = new NetworkingClient(screen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//super.render();
	}

	@Override
	public void render () {
        super.render();
		NC.update();
	}

}
