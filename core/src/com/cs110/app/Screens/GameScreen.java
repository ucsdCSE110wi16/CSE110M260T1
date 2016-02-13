package com.cs110.app.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cs110.app.Controller.WorldController;
import com.cs110.app.Model.CoolDowns;
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
    private WorldController controller;
    Stage stage; //The stage which the touchpad belong to

    Touchpad pad; //the touchpad that controls movement on screen
    //Touchpad resources
    Skin touchPadSkin;
    Touchpad.TouchpadStyle touchpadStyle;
    Drawable touchpadBackground, touchKnob;

    Skin buttonSkin;
    TextButton.TextButtonStyle textbuttonStyle;
    TextButton buttonX, buttonY, buttonZ;
    TextureAtlas buttonsAtlas;
    BitmapFont font;

    SpriteBatch batch;

    public static Boolean buttonXClicked = false,
                          buttonYClicked = false,
                          ButtonZClicked = false;

    @Override
    public void show()
    {
        //create new world with a player at the location
        world = new World(new Player(new Vector2(5, 5), "Player1"));
        renderer = new WorldRenderer(world);
        controller = new WorldController(world);



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

        //init stage & table and on screen controls
        buttonSkin = new Skin();
        font = new BitmapFont();
        buttonSkin.add("button",new Texture("touchKnob.png"));
        buttonSkin.add("buttonDown",new Texture("buttonDown.png"));
        textbuttonStyle = new TextButton.TextButtonStyle();
        textbuttonStyle.font = font;
        textbuttonStyle.up = buttonSkin.getDrawable("button");
        textbuttonStyle.down = buttonSkin.getDrawable("buttonDown");
        textbuttonStyle.checked = buttonSkin.getDrawable("button");

        buttonX = new TextButton("X",textbuttonStyle);
        buttonY = new TextButton("Y",textbuttonStyle);
        buttonZ = new TextButton("Z",textbuttonStyle);

        buttonZ.pad(30);
        buttonX.pad(30);
        buttonY.pad(30);

        //Adding on-touch listeners for buttons
        buttonX.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {

                controller.buttonXPressed();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                controller.buttonXReleased();

                buttonX.setTouchable(Touchable.disabled);
                new Timer().schedule(new Timer.Task()
                {
                    int CD = 10;
                    @Override
                    public void run()
                    {
                        String in = "" + CD--;
                        buttonX.setText(in);
                    }

                },0,1,10);
                new Timer().schedule(new Timer.Task()
                {
                    @Override
                    public void run()
                    {
                        buttonX.setText("X");
                        buttonX.setTouchable(Touchable.enabled);
                    }

                },10,1,1);
            }

        });

        buttonY.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                controller.buttonYPressed();
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                controller.buttonYReleased();

                buttonY.setTouchable(Touchable.disabled);
                new Timer().schedule(new Timer.Task()
                {
                    int CD = 10;
                    @Override
                    public void run()
                    {
                        String in = "" + CD--;
                        buttonY.setText(in);
                    }

                },0,1,10);
                new Timer().schedule(new Timer.Task()
                {
                    @Override
                    public void run()
                    {
                        buttonY.setText("Y");
                        buttonY.setTouchable(Touchable.enabled);
                    }

                },10,1,1);
            }
        });

        buttonZ.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                controller.buttonZPressed();
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                controller.buttonZReleased();

                buttonZ.setTouchable(Touchable.disabled);
                new Timer().schedule(new Timer.Task()
                {
                    int CD = 10;
                    @Override
                    public void run()
                    {
                        String in = "" + CD--;
                        buttonZ.setText(in);
                    }

                },0,1,10);
                new Timer().schedule(new Timer.Task()
                {
                    @Override
                    public void run()
                    {
                        buttonZ.setText("Z");
                        buttonZ.setTouchable(Touchable.enabled);
                    }

                },10,1,1);
            }
        });

        //The 20 is how much distance touchpad has to be moved before detecting the motion
        pad = new Touchpad(10, touchpadStyle);
        pad.setBounds(30, 30, 200, 200);

        //Adds the pad and on-screen buttons to group
        HorizontalGroup group = new HorizontalGroup();
        group.pad(0,50,250,0);
        group.align(Align.left);
        group.addActor(pad);
        group.addActor(buttonX);
        group.addActor(buttonY);
        group.addActor(buttonZ);
        group.space(20);

        //add touchpad to the stage
        stage = new Stage();
        stage.addActor(group);

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
        if(pad.isTouched())
            world.getPlayer().move(pad.getKnobPercentX(), pad.getKnobPercentY());



        //System.err.println("Game screen rendedring");
        World.gameTime = System.currentTimeMillis();
        renderer.render();

        stage.act(delta);
        stage.draw();

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

    /**************************** Input Processes **************************************/

}
