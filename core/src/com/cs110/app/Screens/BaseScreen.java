package com.cs110.app.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

/**
 * Created by marcof on 3/3/16.
 */
public class BaseScreen implements Screen {
    // Displaying error message
    private static int ERROR_MESSAGE_TIME_DEFAULT = 2;
    public String currentErrorMessage;
    public float currentErrorMessageTime = 0;
    BitmapFont errorMessageFont;

    // Changing Screens
    protected boolean changeScreen;
    protected ScreenEnum newScreen;
    protected boolean serverclient = false;
    protected String clientip;

    //Back button
    TextButton.TextButtonStyle backButtonStyle;
    TextButton backButton;
    Skin buttonSkin;
    BitmapFont backButtonFont;
    protected boolean displayBackButton = true;


    //General stuff
    SpriteBatch baseSpriteBatch;
    Stage stage;
    private Table table;

    public void resetScreenChange(){
        changeScreen = false;
        newScreen = null;
    }

    public void displayErrorMessage(String message) {
        displayErrorMessage(message, ERROR_MESSAGE_TIME_DEFAULT);
    }

    public void displayErrorMessage(String message, float length) {
        currentErrorMessage = message;
        currentErrorMessageTime = length;
    }


    @Override
    public void show() {
        baseSpriteBatch = new SpriteBatch();
        errorMessageFont = new BitmapFont();
        errorMessageFont.setColor(Color.RED);

        // Back button
        backButtonFont = new BitmapFont();
        buttonSkin = new Skin();
        buttonSkin.add("button", new Texture("touchKnob.png"));
        buttonSkin.add("buttonDown",new Texture("buttonDown.png"));
        backButtonStyle = new TextButton.TextButtonStyle();
        backButtonStyle.font = backButtonFont;
        backButtonStyle.up = buttonSkin.getDrawable("button");
        backButtonStyle.down = buttonSkin.getDrawable("buttonDown");
        backButtonStyle.checked = buttonSkin.getDrawable("button");

        backButton = new TextButton("Back",backButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // go to Main menu
                changeScreen = true;
                newScreen = ScreenEnum.MAIN_MENU;
            }
        });

        table = new Table();
        table.setFillParent(true);
        table.left().top();
        /*backButton.pad(20);
        HorizontalGroup group = new HorizontalGroup();
        group.pad(180);
        group.align(Align.bottom);
        group.space(50);
        */
        //group.addActor(backButton);
        if(displayBackButton) {
            table.add(backButton);
        }
        //add touchpad to the stage
        stage = new Stage();
        stage.addActor(table);
        //baseStage.addActor(group);
        Gdx.input.setInputProcessor(stage);
    }


    public void update(float delta) {
        if (changeScreen) {
            System.out.println("MENU change screen in render");
            ScreenManager.getInstance().showScreen(newScreen, serverclient, clientip);
            serverclient = false;
        }
        baseSpriteBatch.begin();
        if(currentErrorMessage != null) {
            errorMessageFont.draw(baseSpriteBatch, "Error: " + currentErrorMessage, 100, 100);
            currentErrorMessageTime = currentErrorMessageTime - delta;
            if(currentErrorMessageTime <= 0){
                currentErrorMessage = null;
            }
        }
        baseSpriteBatch.end();
        stage.act(delta);
        stage.draw();
    }

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
