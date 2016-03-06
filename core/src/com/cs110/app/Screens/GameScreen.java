package com.cs110.app.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
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
import com.cs110.app.Model.Attack;
import com.cs110.app.Model.Player;
import com.cs110.app.Model.World;
import com.cs110.app.View.WorldRenderer;
import com.badlogic.gdx.graphics.Color;


/**
 * Created by Yashwanth on 1/25/16.
 */

//This is the screen that is shown when the the game itself is being played
public class GameScreen extends BaseScreen
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
    BitmapFont healthFont;
    GestureDetector gestureDetector;
    private boolean show;
    private boolean changeScreen;
    private ScreenEnum newScreen;

    SpriteBatch batch;

    public static Boolean buttonXClicked = false,
            buttonYClicked = false,
            ButtonZClicked = false;

    @Override
    public void show()
    {
        displayBackButton = false;
        super.show();
        System.out.println("SHOW CALLED GAME");
        //create new world with a player at the location
        //world = new World(new Player(new Vector2(300, 200), "Player1"));
        world = new World();
        renderer = new WorldRenderer(world);
        controller = new WorldController(world);


        batch = new SpriteBatch();

        font = new BitmapFont();
        font.setColor(Color.RED);


        float w = Gdx.graphics.getWidth()/20;
        //init touchpad
        touchPadSkin = new Skin();
        touchPadSkin.add("touchBackground", new Texture("touchBackground.png"));
        touchPadSkin.add("touchKnob", new Texture("touchKnob.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadBackground = touchPadSkin.getDrawable("touchBackground");
        touchKnob = touchPadSkin.getDrawable("touchKnob");

        touchpadStyle.background = touchpadBackground;
        touchpadStyle.knob = touchKnob;
        touchpadStyle.background.setMinHeight(5*w);
        touchpadStyle.background.setMinWidth(5*w);
        touchpadStyle.knob.setMinHeight((5*w)/3);
        touchpadStyle.knob.setMinWidth((5*w)/3);
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
//        float w = Gdx.graphics.getWidth()/20;
         buttonX.getLabel().setFontScale(w/24);
          buttonY.getLabel().setFontScale(w/24);
          buttonZ.getLabel().setFontScale(w / 24);
        buttonZ.pad(90);
        buttonX.pad(90);
        buttonY.pad(90);
        controller.addButton(buttonX,'x');
        controller.addButton(buttonY, 'y');
        controller.addButton(buttonZ, 'z');
        //Adding on-touch listeners for buttons
        buttonX.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                controller.buttonXPressed();
//                buttonXClicked = true;
//                System.out.println("Rotation: " + world.getSelfPlayer().getRotation());
//
//
//                //world.addAttack((new Attack(world.getSelfPlayer().IMAGE_WIDTH / 2, world.getSelfPlayer().getRotation())));
//                world.addAttack(new Attack(world.getSelfPlayer().getPosition().x, world.getSelfPlayer().getPosition().y,(float)world.getSelfPlayer().getRotation()));
//                world.attackOccured = true;
//                buttonX.setTouchable(Touchable.disabled);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                controller.buttonXReleased();
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
                }
        });

        //The 20 is how much distance touchpad has to be moved before detecting the motion
        pad = new Touchpad(50, touchpadStyle);
        pad.setBounds(90, 90,100,100);

        //Adds the pad and on-screen buttons to group
        HorizontalGroup group = new HorizontalGroup();
        HorizontalGroup buttonGroup = new HorizontalGroup();
        HorizontalGroup UI = new HorizontalGroup();
         float h = Gdx.graphics.getHeight();
        float w2 = Gdx.graphics.getWidth();
        group.pad(0, 50, h/2, 0);
        buttonGroup.pad(0,0, h / 4, 0);
//        buttonGroup.align(Align.right);
        group.addActor(pad);
        buttonGroup.addActor(buttonX);
        buttonGroup.addActor(buttonY);
        buttonGroup.addActor(buttonZ);
        buttonGroup.space(w / 4);
        UI.addActor(group);
        UI.addActor(buttonGroup);
        UI.space(w2 - w2/4 - ((float)w2/480 + (float)3*w/4 + (180*4)));

        //add touchpad to the stage
        stage = new Stage();
        stage.addActor(UI);

        //instantiating myGestureDetector
//        myGestureDetector = new GestureDetector (this);
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new GestureDetector(controller));
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
        show = true;
    }

    @Override
    public void render(float delta)
    {
        if(! show) {
            return;
        }
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*
        Moving the player
         */
        if(pad.isTouched()) {
            world.getPlayer().move(pad.getKnobPercentX(), pad.getKnobPercentY());
        }

        batch.begin();
        //font.draw(batch, "Health: "+Integer.toString(world.getSelfPlayer().getHealth()), 550, 25);
        batch.end();

        if (world.getAttacks().size() > 0) {
            for (Attack a : world.getAttacks()) {
                a.update();
            }
        }
        controller.processInput();



        //System.err.println("Game screen rendedring");
        World.gameTime = System.currentTimeMillis();
        renderer.render();

        stage.act(delta);
        stage.draw();
        super.update(delta);

    }

    public World getWorld(){
        return world;
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

    public void disconnect(){
        displayErrorMessage("disconnection");
        newScreen = ScreenEnum.MAIN_MENU;
        changeScreen = true;
        serverclient = true;
    }

    /**************************** Input Processes **************************************/

}
