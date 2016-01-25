package com.cs110.app;

/**
 * Created by Yashwanth on 1/24/16.
 */
public class World {

    private Player myPlayer;

    public World(){
        createWorld();
    }

    //creates the game world
    private void createWorld(){

    }

    //returns the player that belongs to this world
    public Player getPlayer(){
        return myPlayer;
    }
}
