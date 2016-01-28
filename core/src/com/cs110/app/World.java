package com.cs110.app;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Yashwanth on 1/24/16.
 */

//This class will represent our world and map
public class World
{

    static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;

    //since this World object is associated with client side, each client will have his own World
    // (and the worlds share the obstacles and players list). Each world will also know the player
    // associated with the client it is running on

    private Player myPlayer; //the player associated with this client, camera is centered on this
    private ArrayList<Player> playerList; //list of all the players in the world
    private ArrayList<Obstacle> obstacles; //a list of obstacles in the map. Maybe we can have an interface
    // called Obstacle and then from there we can have multiple obstacles

    public World()
    {
        createWorld();
    }

    //creates the game world
    private void createWorld()
    {

        playerList.add(new Player(new Vector2(5, 5), "Player1"));
        obstacles.add(new Obstacle(new Vector2(25,30)));
        obstacles.add(new Obstacle(new Vector2(50,50),10,2));

    }

    //returns the player that belongs to this world
    public ArrayList<Player> getPlayers()
    {
        return playerList;
    }

    //returns the player in the world with the passed in ID, if no such player exists returns null
    public Player getPlayerWithId( String PID)
    {
        for( Player p : getPlayers())
        {
            if(p.getId().equals(PID))
                return p;
        }

        return null; //maybe we should return a new player with an empty id instead
    }

    //set the main player of this world (i.e. the on camera is centered on)
    public void setPlayer(Player p)
    {
        myPlayer = p;
    }

    //get the main player of this world (i.e. the on camera is centered on)
    public Player getPlayer()
    {
        return myPlayer;
    }

    public ArrayList<Obstacle> getObstacles()
    {
        return obstacles;
    }
}
