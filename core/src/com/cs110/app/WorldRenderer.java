package com.cs110.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Yashwanth on 1/25/16.
 */

//This class renders the world
public class WorldRenderer {

    static final int CAMERA_WIDTH = 10;
    static final int CAMERA_HEIGHT = 10;

    private World world; //this is the world that this object will render
    private OrthographicCamera camera; //the camera viewing the world

    //Constructor takes in a world to render as the parameter
    public WorldRenderer(World world){
        this.world = world;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio <--- according to libgdx wiki
        //sets the viewport of camera
        camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT * (h/w));

        //centers the camera on the player
        camera.position.set(world.getPlayer().getPosition().x, world.getPlayer().getPosition().y, 0);
        camera.update();


    }

    //Draw the world and set the camera
    public void render(){


    }
}
