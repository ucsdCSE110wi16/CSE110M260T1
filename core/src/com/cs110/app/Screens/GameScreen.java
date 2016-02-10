package com.cs110.app.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cs110.app.Model.Player;
import com.cs110.app.Model.World;
import com.cs110.app.View.WorldRenderer;

/**
 * Created by Yashwanth on 1/25/16.
 */

//This is the screen that is shown when the the game itself is being played
public class GameScreen implements Screen
{

    private World world;
    private WorldRenderer renderer;
    Stage stage; //The stage which the touchpad belong to

    Touchpad pad; //the touchpad that controls movement on screen
    //Touchpad resources
    Skin touchPadSkin;
    Touchpad.TouchpadStyle touchpadStyle;
    Drawable touchpadBackground, touchKnob;

    SpriteBatch batch;

    public World getWorld(){
        return world;
    }

    @Override
    public void show()
    {
        //create new world with a player at the location
        world = new World(new Player(new Vector2(5, 5), "Player1"));
        renderer = new WorldRenderer(world);


        batch = new SpriteBatch();

        //init touchpad
        touchPadSkin = new Skin();
        touchPadSkin.add("touchBackground", new Texture("touchBackground.png"));
        touchPadSkin.add("touchKnob", new Texture("touchKnob.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadBackground = touchPadSkin.getDrawable("touchBackground");
        touchKnob = touchPadSkin.getDrawable("touchKnob");

        touchpadStyle.background = touchpadBackground;
        touchpadStyle.knob = touchKnob;


        //The 20 is how much distance touchpad has to be moved before detecting the motion
        pad = new Touchpad(10, touchpadStyle);
        pad.setBounds(15, 15, 200, 200);

        //add touchpad to the stage
        stage = new Stage();
        stage.addActor(pad);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*
        Moving the player
         */
        if(pad.isTouched()) {
            world.getPlayer().move(pad.getKnobPercentX(), pad.getKnobPercentY());
        }

        stage.act(delta);
        stage.draw();

        //System.err.println("Game screen rendedring");
        renderer.render();

    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }
}
