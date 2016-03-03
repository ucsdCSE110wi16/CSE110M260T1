package com.cs110.app.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.cs110.app.CS110App;

/**
 * Created by marcof on 3/2/16.
 * From tutorial for managing screens
 * http://www.pixnbgames.com/blog/libgdx/how-to-manage-screens-in-libgdx/
 */
public class ScreenManager {

    private static ScreenManager instance;

    private Game game;

    // Singleton: private constructor
    private ScreenManager() {
        super();
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(Game game) {
        this.game = game;
    }

    // Show in the game the screen which enum type is received
    public void showScreen(ScreenEnum screenEnum, Object... params) {
        System.out.println("Called Screen Manager showScreen to change the screen");
        System.out.println(screenEnum);
        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Show new screen
        Screen newScreen = screenEnum.getScreen(params);
        game.setScreen(newScreen);

        if(screenEnum == ScreenEnum.WAITING) {
            CS110App app = (CS110App)game;
            app.startServer((ServerWaitingScreen)newScreen);
        }

        if(screenEnum == ScreenEnum.GAME) {
            if(params.length >= 2) {
                CS110App app = (CS110App)game;
                System.out.println(params[1]);
                app.startClient((GameScreen)newScreen, (String)params[1]);
            }
            else {
                CS110App app = (CS110App) game;
                app.setGameScreen((GameScreen) newScreen);
            }
        }

        // Dispose previous screen
        /*if (currentScreen != null) {
            currentScreen.dispose();
        }*/
    }
}
