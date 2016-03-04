package com.cs110.app.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.cs110.app.CS110App;
import com.cs110.app.Model.Player;

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
        if(screenEnum == ScreenEnum.GAME) {
            CS110App app = (CS110App)game;

            if(params.length >= 2) {
                System.out.println("GOGO");
                boolean set = app.setClient((GameScreen) newScreen, (String) params[1]);
                if(!set && currentScreen instanceof BaseScreen) {
                    BaseScreen b = (BaseScreen)currentScreen;
                    b.displayErrorMessage("Couldn't connect to " + params[1]);
                    b.resetScreenChange();
                    return;
                }
            }




        }
        System.out.println("setScreen");
        game.setScreen(newScreen);
        if(screenEnum == ScreenEnum.GAME) {
            CS110App app = (CS110App)game;
            app.startGame((GameScreen) newScreen);
            System.out.println("STARTED THE GAME AHAHH");
            // start
        }
        if(screenEnum == ScreenEnum.GAME && params.length == 1 && (Integer)params[0] == 5) {
            // Local game
            Player myPlayer = new Player(new Vector2(700,700),"Player1");
            GameScreen gs = (GameScreen)newScreen;
            gs.getWorld().setSelfPlayer(myPlayer);
        }

        if(screenEnum == ScreenEnum.WAITING) {
            CS110App app = (CS110App)game;
            app.setServer((ServerWaitingScreen) newScreen);
        }
        if(screenEnum == ScreenEnum.MAIN_MENU) {
            CS110App app = (CS110App)game;
            app.stopNetworking();
        }



        // Dispose previous screen
        /*if (currentScreen != null) {
            currentScreen.dispose();
        }*/
    }
}
