package com.cs110.app;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Yashwanth on 1/24/16.
 */

//This class will represent our world and map
public class World {

    private Player myPlayer;

    public World(){
        createWorld();
    }

    //creates the game world
    private void createWorld(){

        myPlayer = new Player(new Vector2(5,5));
    }

    //returns the player that belongs to this world
    public Player getPlayer(){
        return myPlayer;
    }
}
