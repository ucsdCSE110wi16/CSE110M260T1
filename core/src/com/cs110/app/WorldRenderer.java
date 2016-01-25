package com.cs110.app;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Yashwanth on 1/25/16.
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
