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
import com.cs110.app.RunEnum;
import com.cs110.app.View.WorldRenderer;
import com.badlogic.gdx.Net.*;
import com.badlogic.gdx.net.*;
import java.awt.Menu;
import java.util.ArrayList;
import java.util.Iterator;



/**
 * Created by caeleanbarnes on 2/28/16.
 */
public class MenuScreen extends BaseScreen {

    Skin buttonSkin;
    TextButton.TextButtonStyle textbuttonStyle;
    TextButton buttonServer, buttonClient;
    BitmapFont font;
    BitmapFont fontIPTitle;
    static String ipStatus;
    SpriteBatch spriteBatch;
    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;

    public MenuScreen() {
        this.ipStatus = "Checking IP Address";
        displayBackButton = false;
    }


    public void show() {
        super.show();
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
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (CS110App.RUN_TYPE == RunEnum.MULTIPLAYER_LOCAL) {
                    changeScreen = true;
                    newScreen = ScreenEnum.WAITING;
                    serverclient = false;
                }
                else {
                    HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                    System.out.println("http://" + CS110App.SERVER_URL + "/game/create");
                    final HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.GET).url("http://" + CS110App.SERVER_URL + "/game/create").build();
                    Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
                        @Override
                        public void handleHttpResponse(HttpResponse httpResponse) {
                            System.out.println(httpResponse.getStatus().getStatusCode());
                            if (CS110App.RUN_TYPE == RunEnum.MULTIPLAYER_LOCAL) {
                                changeScreen = true;
                                newScreen = ScreenEnum.WAITING;
                            } else {
                                System.out.println(httpResponse.getStatus().getStatusCode());
                                if (httpResponse.getStatus().getStatusCode() <= 300 && httpResponse.getStatus().getStatusCode() >= 200) {
                                    System.out.println(httpResponse.getStatus());
                                    System.out.println(httpResponse.getResultAsString());
                                    changeScreen = true;
                                    newScreen = ScreenEnum.WAITING;
                                } else {
                                    displayErrorMessage(httpResponse.getStatus().getStatusCode() + "Server Error");
                                }
                            }

                        }

                        @Override
                        public void failed(Throwable t) {
                            System.out.println(t.toString());
                            System.out.println(t.fillInStackTrace().toString());
                            displayErrorMessage("Server Error failed");
                        }

                        @Override
                        public void cancelled() {
                            displayErrorMessage("Server Error cacnelled");
                        }
                    });

                }
            }
        });

        buttonClient = new TextButton(" Start Client ",textbuttonStyle);
        buttonClient.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//              //get current game  s from the server
                if (CS110App.RUN_TYPE == RunEnum.MULTIPLAYER_LOCAL) {
                    changeScreen = true;
                    newScreen = ScreenEnum.GAME;
                    serverclient = true;
                    clientip = "127.0.0.1";
                } else {
                    HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                    Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://" + CS110App.SERVER_URL + "/games/").build();
                    Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                        @Override
                        public void handleHttpResponse(Net.HttpResponse httpResponse) {
                            System.out.println(httpResponse.getStatus());
                            String jsonString = httpResponse.getResultAsString();
                            System.out.println(jsonString);
                            if (httpResponse.getStatus().getStatusCode() <= 300 && httpResponse.getStatus().getStatusCode() >= 200) {
                                try {
                                    JsonValue root = new JsonReader().parse(jsonString);
                                    System.out.println(root);
                                    if (root.size > 0) {
                                        JsonValue game = root.get(0);
                                        changeScreen = true;
                                        newScreen = ScreenEnum.GAME;
                                        serverclient = true;
                                        clientip = game.get("ip").asString();
                                    } else {
                                        displayErrorMessage("No games available");
                                    }

                                } catch (Exception e) {
                                    System.out.println(e);
                                    System.out.println("FAILURE");
                                }
                            } else {
                                displayErrorMessage("Backend Server error");
                            }
                        }

                        @Override
                        public void failed(Throwable t) {
                            displayErrorMessage("Backend Server error");
                        }

                        @Override
                        public void cancelled() {
                            displayErrorMessage("Backend Server error");
                        }
                    });
                }
            }

        });
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        buttonClient.pad(h/20);
        buttonServer.pad(h/20);
        HorizontalGroup group = new HorizontalGroup();
        group.pad(h/3);
        group.align(Align.bottom);
        group.space(h/13);

        group.addActor(buttonServer);
        group.addActor(buttonClient);
        //add touchpad to the stage
        //stage = new Stage();
        stage.addActor(group);

        //Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //    renderer.render();
        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        font.draw(spriteBatch, MenuScreen.ipStatus, 5, 25);
        fontIPTitle.draw(spriteBatch, "IP Address: ", 5, 50);

        spriteBatch.end();
        super.update(delta);


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

