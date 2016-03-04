package com.cs110.app.Screens;

import com.badlogic.gdx.Screen;

/**
 * Created by marcof on 3/2/16.
 * From tutorial for managing screens
 * http://www.pixnbgames.com/blog/libgdx/how-to-manage-screens-in-libgdx/
 */
public enum ScreenEnum {
    MAIN_MENU {
        public Screen getScreen(Object... params) {
                return new MenuScreen();
        }
    },
    WAITING {
        public Screen getScreen(Object... params) {
            return new ServerWaitingScreen();
        }
    },
    GAME {
        public Screen getScreen(Object... params) {
            return new GameScreen();
        }
    };
    public abstract Screen getScreen(Object... params);
}
