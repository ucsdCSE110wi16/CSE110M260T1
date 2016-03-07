package com.cs110.app.Screens;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.HttpRequestBuilder;
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
import com.cs110.app.RunEnum;
import com.cs110.app.View.WorldRenderer;

/**
 * Created by marcof on 3/2/16.
 */
public class ServerWaitingScreen extends BaseScreen{
    SpriteBatch spriteBatch;
    BitmapFont font;
    private boolean changeScreen;
    private ScreenEnum newScreen;
    private Timer pingTimer;
    private int gameId;


    public ServerWaitingScreen() {
    }

    public void connected(){
        changeScreen = true;
        newScreen = ScreenEnum.GAME;
    }

    public void setGameId(int gameId){
        this.gameId = gameId;
    }

    public void show() {
        super.show();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        pingTimer = new Timer();
        if (CS110App.RUN_TYPE == RunEnum.MULTIPLAYER_BACKEND) {
            System.out.println("timertask added");
            pingTimer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    System.out.println("RAN");
                    HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                    System.out.println("http://" + CS110App.SERVER_URL + "/game/ping/" + gameId);
                    final Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://" + CS110App.SERVER_URL + "/game/ping/" + gameId).build();
                    Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                        @Override
                        public void handleHttpResponse(Net.HttpResponse httpResponse) {
                                if (httpResponse.getStatus().getStatusCode() <= 300 && httpResponse.getStatus().getStatusCode() >= 200) {
                                    System.out.println("successful ping");
                                } else {
                                    displayErrorMessage(httpResponse.getStatus().getStatusCode() + "Server Error");
                                    changeScreen = true;
                                    newScreen = ScreenEnum.MAIN_MENU;
                                }
                        }



                        @Override
                        public void failed(Throwable t) {
                            System.out.println(t.toString());
                            System.out.println(t.fillInStackTrace().toString());
                            displayErrorMessage("Server Error failed");
                            changeScreen = true;
                            newScreen = ScreenEnum.MAIN_MENU;
                        }

                        @Override
                        public void cancelled() {
                            displayErrorMessage("Server Error cancelled");
                            changeScreen = true;
                            newScreen = ScreenEnum.MAIN_MENU;
                        }
                    });
                }

            }, 0, 2, 100);
        }
    }

    @Override
    public void render(float delta) {
        if (changeScreen) {
            ScreenManager.getInstance().showScreen(newScreen);
        }
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        font.draw(spriteBatch, "Waiting for challenger", 5, 25);
        spriteBatch.end();
        super.update(delta);
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
        pingTimer.stop();
    }

    @Override
    public void dispose() {
        pingTimer.stop();
    }

}
