package com.cs110.app;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
/**
 * Created by Yashwanth on 1/24/16.
 */
public class Player {

    static final float SIZE = 0.5f; //half a unit size

    Vector2 position = new Vector2(); //position of the player character
    Vector2 acceleration = new Vector2(); //acceleration of the player character
    Vector2 velocity = new Vector2(); //velocity of the player character

    Rectangle bounds = new Rectangle();

    //Constructor for the player, takes in a vector2 to set the position of the player character
    public Player(Vector2 position){
        this.position = position;
        bounds.height = SIZE;
        bounds.width = SIZE;
    }


}
