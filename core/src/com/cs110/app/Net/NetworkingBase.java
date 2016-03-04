package com.cs110.app.Net;

import com.esotericsoftware.kryonet.Listener;
import com.cs110.app.Screens.GameScreen;
/**
 * Created by marcof on 3/3/16.
 */
public interface NetworkingBase {
    //starts Networking by passing a game screen
    public abstract void startGame(GameScreen gs);
    public abstract void update();
    public abstract void stop();
}
