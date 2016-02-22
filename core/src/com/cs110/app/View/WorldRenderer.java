package com.cs110.app.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.cs110.app.Model.Attack;
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

    //spritebatch for drawing
    private SpriteBatch spriteBatch;
    private Texture playerTexture;
    private Sprite playerSprite;
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
        //camera.position.set(world.getPlayer().getPosition().x, world.getPlayer().getPosition().y, 0);
        camera.update();

        spriteBatch = new SpriteBatch();
        loadTextures();
        playerSprite = new Sprite(playerTexture);
    }

    //loads the images to be used
    private void loadTextures(){
        playerTexture = new Texture("spaceshipSprite.png");
    }

    //Draw the world and set the camera
    public void render()
    {

        //Keeping the camera the centered on the player
        camera.position.set(world.getPlayer().getPosition().x, world.getPlayer().getPosition().y, 0);
        camera.update();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //Drawing
        //rend.setProjectionMatrix(camera.combined);
//        spriteBatch.setProjectionMatrix(camera.combined);


        //Drawing the obstacles
        for (Obstacle obj : world.getObstacles() )
        {
            Rectangle rec = obj.getBounds();
            float x = w/2;
            float y = h/2;

            x = obj.getPosition().x - world.getPlayer().getPosition().x + x;
            y = obj.getPosition().y - world.getPlayer().getPosition().y + y;
//
//            float x = obj.getPosition().x + rec.x;
//            float y = obj.getPosition().y + rec.y;

            //comment the following 2 lines to not use shaperenderer
            rend.begin(ShapeRenderer.ShapeType.Filled);
            rend.setColor(new Color(0, 194, 90, 1));
            rend.rect(x, y, rec.width, rec.height);
            rend.end();

            //Drawing collision polygon for debug
//            rend.begin(ShapeRenderer.ShapeType.Line);
//            rend.setColor(new Color(0, 20, 3, 1));
//            rend.polygon(obj.getPolygon().getTransformedVertices());
//            rend.end();


        }

        //Drawing the player
        for (Player person : world.getPlayers() ) {

            //drawing image

            //want to get the position of the player relative to the player the camera is centered on
            //getting center of screen

            float spriteX = w/2;
            float spriteY = h/2;

            //setting draw spot based on position relative to player in center
            spriteX = person.getPosition().x - world.getPlayer().getPosition().x + spriteX;
            spriteY = person.getPosition().y - world.getPlayer().getPosition().y + spriteY;

            float recX = spriteX;
            float recY = spriteY;

            //accounting for width and height of image
            spriteX -= playerSprite.getWidth()/2;
            spriteY -= playerSprite.getHeight()/2;

            // spriteX = person.getPosition().x;
            // spriteY = person.getPosition().y;

            spriteBatch.begin();
            //playerSprite.setPosition(spriteX, spriteY);
            playerSprite.setPosition(spriteX, spriteY);
            //playerSprite.setSize(2, 1);
            //playerSprite.setSize(person.getBounds().width,person.getBounds().height);

            playerSprite.setRotation((float) (Math.toDegrees(person.getRotation())));
            playerSprite.draw(spriteBatch);
            spriteBatch.end();

            //drawing debug shape
            Rectangle rec1 = person.getBounds1();
            Rectangle rec2 = person.getBounds2();
            float x= recX - rec1.width/2; //- rec.x;
            float y= recY - rec1.height/2; //- rec.y;
            float x2 = recX - rec2.width/2;
            float y2 = recY - rec2.height/2;

            //comment the following 2 lines to not use shaperenderer

            rend.begin(ShapeRenderer.ShapeType.Line);
            rend.setColor(new Color(1, 0, 0, 1));
            rend.rect(x, y, rec1.width / 2, rec1.height / 2, rec1.width, rec1.height, 1f, 1f, (float) Math.toDegrees(person.getRotation()));
            rend.rect(x2, y2, rec2.width / 2, rec2.height / 2, rec2.width, rec2.height, 1f, 1f, (float) Math.toDegrees(person.getRotation()));
            rend.end();
            //Drawing collision polygon for debug
//            rend.setColor(new Color(0, 1, 1, 0));
//            rend.polygon(person.getPolygon().getTransformedVertices());

//            rend.end();



        }
        for (Attack a : world.getAttacks()) {
            float spriteX = w/2;
            float spriteY = h/2;
            spriteX = a.xPos - world.getSelfPlayer().getPosition().x + spriteX;
            spriteY = a.yPos - world.getSelfPlayer().getPosition().y + spriteY;
            Rectangle rec2 = world.getSelfPlayer().getBounds2();
            float x2 = spriteX - rec2.width/2;
            float y2 = spriteY - rec2.height/4 ;
            rend.begin(ShapeRenderer.ShapeType.Filled);
            rend.setColor(new Color(1, 0, 0, 1));
            rend.rect((float)x2+(float)a.getXDist(),(float)y2+(float)a.getYDist(),10,5);
            //rend.rect((float)x2 ,(float)y2,10,5);
            rend.end();
        }




    }
}
