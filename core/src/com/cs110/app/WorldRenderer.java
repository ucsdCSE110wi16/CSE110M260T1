package com.cs110.app;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cs110.app.Model.World;

/**
 * Created by Yashwanth on 1/25/16.
 * Personal class we made to help us render the world
 */

//This class renders the world
public class WorldRenderer {

    private World world; //this is the world that this object will render
    private OrthographicCamera camera; //the camera viewing the world

    //Constructor takes in a world to render as the parameter
    public WorldRenderer(World world){
        this.world = world;
    }

    //Draw the world and set the camera
    public void render(){

    }
}
