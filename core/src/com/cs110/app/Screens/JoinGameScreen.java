package com.cs110.app.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.HttpRequestBuilder;

/**
 * Created by marcof on 3/2/16.
 */
public class JoinGameScreen implements Screen {

    SpriteBatch spriteBatch;
    BitmapFont font;
    private boolean changeScreen;
    private ScreenEnum newScreen;


    public JoinGameScreen() {
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
