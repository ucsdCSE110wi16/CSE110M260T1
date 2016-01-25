package com.cs110.app;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
/**
 * Created by Yashwanth on 1/24/16.
 */
public class Player {

    static final float SIZE = 0.5f; //half a unit size
    static final int MAX_HEALTH = 100;
    static final int MAX_ARMOR = 100;

    Vector2 position = new Vector2(); //position of the player character
    Vector2 acceleration = new Vector2(); //acceleration of the player character
    Vector2 velocity = new Vector2(); //velocity of the player character

    private int health; // the health of the player
    private int armor;  // armor of the player
    private String Id; // the Id of the player

    Rectangle bounds = new Rectangle();

    //Constructor for the player, takes in a vector2 to set the position of the player character
    public Player(Vector2 position, String playerID){
        this.position = position;
        bounds.height = SIZE;
        bounds.width = SIZE;
        health = MAX_HEALTH;
        armor = MAX_ARMOR;
        Id = playerID;
    }


    public void move(Vector2 moveVector){

    }

    //Launches a skillshot in the direction the player is facing, TODO: params TBD
    public void skillshot(Vector2 attackVector){

    }
    //The aoe attack that launches when button is pressed TODO: params TBD
    public void attack(){

    }

    //if double tap on location blink onto that location TBD: determine range of blink
    public void blind(Vector2 blinkVector){

    }

    //returns true if they are the same player
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Player) {
            //check if they are the same player by comparing Id
            return ((Player)obj).getId().equals(getId());
        }
        else
            return false;

    }

    public String getId(){
        return Id;
    }

    public Vector2 getPosition(){
        return position;
    }

    //returns the rectangle of this Player
    public Rectangle getBounds(){
        return bounds;
    }
}
