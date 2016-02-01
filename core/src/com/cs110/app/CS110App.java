package com.cs110.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cs110.app.Screens.GameScreen;

public class CS110App extends Game { //The automatically generated code has ApplicationAdapter, but
                                    // game allows for screens
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {

        super.setScreen(new GameScreen());
        //super.render();
	}

	@Override
	public void render () {
        super.render();
		//getScreen().render(0);
		/*Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end(); */
	}


}
