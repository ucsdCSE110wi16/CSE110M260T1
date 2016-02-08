package com.cs110.app.Model;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Yashwanth on 1/24/16.
 */

/* TODO/Discussion
    should we keep the player that this map is centered on outside of the players list?
 */
//This class will represent our world and map
public class World
{

    static final int WORLD_WIDTH = 1000;
    static final int WORLD_HEIGHT = 1000;

    //since this World object is associated with client side, each client will have his own World
    // (and the worlds share the obstacles and players list). Each world will also know the player
    // associated with the client it is running on

    private Player myPlayer; //the player associated with this client, camera is centered on this
    private ArrayList<Player> players; //list of all the players in the world
    private ArrayList<Obstacle> obstacles; //a list of obstacles in the map. Maybe we can have an interface
    // called Obstacle and then from there we can have multiple obstacles

    public static long gameTime = System.currentTimeMillis(); //current time in the world

    // I am forcing player to be created with a player because there are too many places that could
    // have null pointer exceptions if done otherwise
    public World(Player p)
    {
        players = new ArrayList<Player>();
        obstacles = new ArrayList<Obstacle>();
        addPlayer(p);
        setPlayer(p);
        createWorld();
    }

    //creates the game world
    private void createWorld()
    {
        System.err.println("Adding new player and obstalces!!!!");
        addObstacle(new Obstacle(new Vector2(6, 4)));
        addObstacle(new Obstacle(new Vector2(8, 9)));
        addObstacle(new Obstacle(new Vector2(-5, -4), 10, 2));
        addObstacle(0,0);

        //in this instance we should also probabally get all the players from the server and add them too
        // and we need to keep updating the players from the server on every render

    }

    //returns the player that belongs to this world
    public ArrayList<Player> getPlayers()
    {
        return players;
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

    //Adds a player to the world
    public void addPlayer(Player p){
        players.add(p);
    }

    //adds a default obstacle at the position
    private void addObstacle(float x, float y){
        obstacles.add(new Obstacle(new Vector2(x, y)));
    }
    //Adds an obstacle to the world
    public void addObstacle( Obstacle o){
        obstacles.add(o);
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
