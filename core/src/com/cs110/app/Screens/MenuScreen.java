package com.cs110.app.Screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.cs110.app.CS110App;
import com.cs110.app.Controller.WorldController;
import com.cs110.app.Model.World;
import com.cs110.app.View.WorldRenderer;

/**
 * Created by caeleanbarnes on 2/28/16.
 */
public class MenuScreen implements Screen {

    Skin buttonSkin;
    TextButton.TextButtonStyle textbuttonStyle;
    TextButton buttonServer, buttonClient;
    BitmapFont font;
    Stage stage; //The stage which the touchpad belong to
    private CS110App g;
//    private WorldRenderer renderer;
//    private World world;
//    private WorldController controller;
    public MenuScreen(CS110App g) {
        this.g = g;
    }


    public void show() {

//        world = new World();
//        renderer = new WorldRenderer(world);
//        controller = new WorldController(world);


        buttonSkin = new Skin();
        font = new BitmapFont();
        buttonSkin.add("button",new Texture("touchKnob.png"));
        buttonSkin.add("buttonDown",new Texture("buttonDown.png"));
        textbuttonStyle = new TextButton.TextButtonStyle();
        textbuttonStyle.font = font;
        textbuttonStyle.up = buttonSkin.getDrawable("button");
        textbuttonStyle.down = buttonSkin.getDrawable("buttonDown");
        textbuttonStyle.checked = buttonSkin.getDrawable("button");

        buttonServer = new TextButton("Start Server",textbuttonStyle);

        buttonServer.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                g.start(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

        });

        buttonClient = new TextButton(" Start Client ",textbuttonStyle);
        buttonClient.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                g.start(false);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

        });

        buttonClient.pad(20);
        buttonServer.pad(20);
        HorizontalGroup group = new HorizontalGroup();
        group.pad(180);
        group.align(Align.bottom);
        group.space(50);

        group.addActor(buttonServer);
        group.addActor(buttonClient);
        //add touchpad to the stage
        stage = new Stage();
        stage.addActor(group);

        Gdx.input.setInputProcessor(stage);
    }
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    //    renderer.render();
        stage.act(delta);
        stage.draw();

    }

//    public World getWorld(){
//        return world;
//    }
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
