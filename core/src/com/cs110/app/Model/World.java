package com.cs110.app.Model;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Yashwanth on 1/24/16.
 */

/* TODO/Discussion
    should we keep the player that this map is centered on outside of the players list?
 */
//This class will represent our world and map
public class World
{

    public static final int WORLD_WIDTH = 5000;
    public static final int WORLD_HEIGHT = 5000;

    //since this World object is associated with client side, each client will have his own World
    // (and the worlds share the obstacles and players list). Each world will also know the player
    // associated with the client it is running on

    private Player myPlayer; //the player associated with this client, camera is centered on this
    private Player otherPlayer;

    private ArrayList<Player> players; //list of all the players in the world
    private ArrayList<Obstacle> obstacles; //a list of obstacles in the map. Maybe we can have an interface
    public boolean attackOccured;
    private List<Attack> attacks;
    // called Obstacle and then from there we can have multiple obstacles

    public static long gameTime = System.currentTimeMillis(); //current time in the world

    // I am forcing player to be created with a player because there are too many places that could
    // have null pointer exceptions if done otherwise
    public World(/*Player p*/)
    {
        players = new ArrayList<Player>();
        obstacles = new ArrayList<Obstacle>();
        attacks = new CopyOnWriteArrayList<Attack>();
        //addPlayer(p);
        //setPlayer(p);
        createWorld();
        //p.setWorld(this);
    }

    //creates the game world
    private void createWorld()
    {
        System.err.println("Adding new player and obstalces!!!!");
        addObstacle(new Obstacle(new Vector2(60, 40)));
        addObstacle(new Obstacle(new Vector2(89, 90)));
        addObstacle(new Obstacle(new Vector2(-50, -40), 10, 2));
        addObstacle(0,0);

        //creating the borders
        //left border
        addObstacle(new Obstacle(new Vector2(-WORLD_WIDTH/2, -WORLD_HEIGHT/2), 10, WORLD_HEIGHT));
        //right border
        addObstacle(new Obstacle(new Vector2(WORLD_WIDTH/2, -WORLD_HEIGHT/2), 10, WORLD_HEIGHT));
        //top border
        addObstacle(new Obstacle(new Vector2(-WORLD_WIDTH/2, WORLD_HEIGHT/2), WORLD_WIDTH, 10));
        //bot border
        addObstacle(new Obstacle(new Vector2(-WORLD_WIDTH/2, -WORLD_HEIGHT/2), WORLD_WIDTH, 10));

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
    public void addPlayer(Player p)
    {
        players.add(p);
    }

    //adds a default obstacle at the position
    private void addObstacle(float x, float y)
    {
        obstacles.add(new Obstacle(new Vector2(x, y)));
    }
    //Adds an obstacle to the world
    public void addObstacle( Obstacle o)
    {
        obstacles.add(o);
    }
    //set the main player of this world (i.e. the on camera is centered on)

    public void addAttack( Attack a) { attacks.add(a); }
    public void removeAttack(){ attacks.remove(0);}

    public void removeAttack(Attack a ) {
       int index = attacks.indexOf(a);
        attacks.remove(index);
    }

    public List<Attack> getAttacks() {return attacks;}
    public void setPlayer(Player p)
    {
        myPlayer = p;
        p.setWorld(this);
    }

    //get the main player of this world (i.e. the on camera is centered on)
    public Player getPlayer()
    {
        return myPlayer;
    }

    public void setSelfPlayer(Player p)
    {
        myPlayer = p;
        addPlayer(myPlayer);
        p.setWorld(this);
    }

    public void setOtherPlayer(Player p)
    {
        otherPlayer = p;
        addPlayer(otherPlayer);
//        p.setWorld(this);
    }

    //get the main player of this world (i.e. the on camera is centered on)
    public Player getSelfPlayer()
    {
        return myPlayer;
    }
    public Player getOtherPlayer()
    {
        return otherPlayer;
    }

    public ArrayList<Obstacle> getObstacles()
    {
        return obstacles;
    }
}
