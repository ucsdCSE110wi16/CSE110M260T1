package com.cs110.app.Screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Timer;
import com.cs110.app.CS110App;
import com.cs110.app.Controller.WorldController;
import com.cs110.app.Model.World;
import com.cs110.app.View.WorldRenderer;
import com.badlogic.gdx.Net.*;
import com.badlogic.gdx.net.*;
import java.awt.Menu;
import java.util.ArrayList;
import java.util.Iterator;



/**
 * Created by caeleanbarnes on 2/28/16.
 */
public class MenuScreen implements Screen {

    Skin buttonSkin;
    TextButton.TextButtonStyle textbuttonStyle;
    TextButton buttonServer, buttonClient;
    BitmapFont font;
    BitmapFont fontIPTitle;
    Stage stage; //The stage which the touchpad belong to
    static String ipStatus;
    SpriteBatch spriteBatch;
    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;
    private CS110App g;
    private boolean changeScreen;
    private ScreenEnum newScreen;
    private int serverclient = 0;
    private String clientip;

    public MenuScreen(CS110App g) {
        this.g = g;
        this.ipStatus = "Checking IP Address";
    }

    public MenuScreen() {
        this.ipStatus = "Checking IP Address";
    }


    public void show() {
        // Get IP ADDRESS
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.GET).url("http://checkip.amazonaws.com/").build();
        Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(HttpResponse httpResponse) {
                MenuScreen.ipStatus = httpResponse.getResultAsString();
            }

            @Override
            public void failed(Throwable t) {
                MenuScreen.ipStatus = "failed to get IP";
            }

            @Override
            public void cancelled() {
                MenuScreen.ipStatus = "failed to get IP";
            }
        });
        backgroundTexture = new Texture("space.jpg");
        backgroundSprite =new Sprite(backgroundTexture);
        buttonSkin = new Skin();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        fontIPTitle = new BitmapFont();
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
                HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                final HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.GET).url("http://192.168.99.100/game/create").build();
                Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(HttpResponse httpResponse) {
                        System.out.println(httpResponse.getStatus());
                        System.out.println(httpResponse.getResultAsString());
                        System.out.println("ran");
                        changeScreen = true;
                        newScreen = ScreenEnum.WAITING;

                    }

                    @Override
                    public void failed(Throwable t) {
                        System.out.println("failure1");
                    }

                    @Override
                    public void cancelled() {
                        System.out.println("failure2");
                    }
                });
                //g.start(true);
                //g.start(true);
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
//              //get current games from the server
                HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://192.168.99.100/games/").build();
                Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        System.out.println(httpResponse.getStatus());
                        String jsonString = httpResponse.getResultAsString();
                        System.out.println(jsonString);
                        try {
                            JsonValue root = new JsonReader().parse(jsonString);
                            System.out.println(root);
                            if(root.size > 0){
                                //There are games pick first one
                                //jank
                                JsonValue game = root.get(0);
                                System.out.println(game);
                                System.out.println(game.size);
                                System.out.println(game.get("id"));
                                System.out.println(game.get("ip"));
                                System.out.println(game.get("start"));
                                changeScreen = true;
                                newScreen = ScreenEnum.GAME;
                                serverclient = 1;
                                clientip = game.get("ip").asString();


                            }
                            else {
                                System.out.println("NO GAMES AVAILABLE");
                            }

                        }
                        catch(Exception e) {
                            System.out.println(e);
                            System.out.println("FAILURE");
                        }
                    }

                    @Override
                    public void failed(Throwable t) {
                        MenuScreen.ipStatus = "failed to get IP";
                    }

                    @Override
                    public void cancelled() {
                        MenuScreen.ipStatus = "failed to get IP";
                    }
                });
                //changeScreen = true;
                //newScreen = ScreenEnum.GAME;
                //serverclient = 1;
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
        if (changeScreen) {
            System.out.println("MENU change screen in render");
            ScreenManager.getInstance().showScreen(newScreen, serverclient, clientip);
        }
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //    renderer.render();
        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        font.draw(spriteBatch, MenuScreen.ipStatus, 5, 25);
        fontIPTitle.draw(spriteBatch, "IP Address: ", 5, 50);

        spriteBatch.end();
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

