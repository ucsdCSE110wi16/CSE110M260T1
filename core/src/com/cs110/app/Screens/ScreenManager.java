package com.cs110.app.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.cs110.app.CS110App;
import com.cs110.app.Model.Player;
import com.cs110.app.RunEnum;

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

        CS110App app = (CS110App)game;

        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Create new Screen
        Screen newScreen = screenEnum.getScreen(params);

        // Change to Game, make sure Client is set
        if(screenEnum == ScreenEnum.GAME && ! (app.RUN_TYPE == RunEnum.SINGLE_PLAYER)) {
            // Try to create client networking
            if(params.length > 1 && (Boolean) params[0]) {
                try {
                    app.setClient((GameScreen) newScreen, (String) params[1]);
                }
                catch(Exception e) {
                    BaseScreen b = (BaseScreen)currentScreen;
                    b.displayErrorMessage(e.toString());
                    b.resetScreenChange();
                    return;
                }
            }
        }
        game.setScreen(newScreen);

        // Start the game after the screen has been set if multiplayer
        if(screenEnum == ScreenEnum.GAME && ! (app.RUN_TYPE == RunEnum.SINGLE_PLAYER)) {
            app.startGame((GameScreen) newScreen);
        }
        else if(screenEnum == ScreenEnum.GAME){
            // Local game
            Player myPlayer = new Player(new Vector2(700,700),"Player1");
            GameScreen gs = (GameScreen)newScreen;
            gs.getWorld().setSelfPlayer(myPlayer);
        }

        //Start server in Waiting screen
        if(screenEnum == ScreenEnum.WAITING) {
            app.setServer((ServerWaitingScreen) newScreen);
        }

        //Stop Networking if MainMenu
        if(screenEnum == ScreenEnum.MAIN_MENU) {
            app.stopNetworking();
        }
    }
}
