package com.cs110.app.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.cs110.app.Model.Obstacle;
import com.cs110.app.Model.Player;
import com.cs110.app.Model.World;

/**
 * Created by Yashwanth on 1/25/16.
 */

//This class renders the world
public class WorldRenderer
{

    static final int CAMERA_WIDTH = 10;
    static final int CAMERA_HEIGHT = 10;

    private World world; //this is the world that this object will render
    private OrthographicCamera camera; //the camera viewing the world
    private ShapeRenderer rend = new ShapeRenderer(); //the renderer

    //Constructor takes in a world to render as the parameter
    public WorldRenderer(World world)
    {
        this.world = world;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio <--- according to libgdx wiki
        //sets the viewport of camera
        camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT * (h/w));

        //centers the camera on the player
//        camera.position.set(world.getSelfPlayer().getPosition().x, world.getSelfPlayer().getPosition().y, 0);
        camera.update();


    }

    //Draw the world and set the camera
    public void render()
    {

        //Keeping the camera the centered on the player
        camera.position.set(world.getSelfPlayer().getPosition().x, world.getSelfPlayer().getPosition().y, 0);
        camera.update();

        //Drawing
        rend.setProjectionMatrix(camera.combined);
        rend.begin(ShapeRenderer.ShapeType.Filled);

        //Drawing the obstacles
        for (Obstacle obj : world.getObstacles() )
        {
            Rectangle rec = obj.getBounds();
            float x = obj.getPosition().x + rec.x;
            float y = obj.getPosition().y + rec.y;
            rend.setColor(new Color(0,194,90,1));
            rend.rect(x,y,rec.width,rec.height);
        }

        rend.end();
        rend.begin(ShapeRenderer.ShapeType.Line);

        //Drawing the player
        for (Player person : world.getPlayers() ) {
            Rectangle rec = person.getBounds();
            float x = person.getPosition().x + rec.x;
            float y = person.getPosition().y + rec.y;
            rend.setColor(new Color(1, 0, 0, 1));
            rend.rect(x, y, rec.width, rec.height);
        }

        rend.end();



    }
}
