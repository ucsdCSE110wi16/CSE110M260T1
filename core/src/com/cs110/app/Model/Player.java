package com.cs110.app.Model;

import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Yashwanth on 1/24/16.
 */
public class Player {

    public enum STATE {IDLE, MOVING, ATTACKING, DEAD}

    public static final float SIZE = 0.5f; //half a unit size
//    public static final int IMAGE_HEIGHT = 190; //size of the image being drawn for player in pixels
//    public static final int IMAGE_WIDTH = 144;
    public static final int IMAGE_HEIGHT = 94; //size of the image being drawn for player in pixels
    public static final int IMAGE_WIDTH = 150;
    public static final int MAX_HEALTH = 100;
    public static final int MAX_ARMOR = 100;
    public static final float SPEED = 10f;

    Vector2 position = new Vector2(); //position of the player character
    Vector2 acceleration = new Vector2(); //acceleration of the player character
    Vector2 velocity = new Vector2(); //velocity of the player character

    public int health; // the health of the player
    private int armor;  // armor of the player
    private String Id; // the Id of the player
    private double rotation; //angle of the player in radians, (java math sin and cos use radians)

    Rectangle bounds1 = new Rectangle(); //hitbox from nose to tail
    Rectangle bounds2 = new Rectangle(); //hitbox wingspan

    //Polygon used for collision detection
    Polygon polygon;

    private World world;

    //Constructor for the player, takes in a vector2 to set the position of the player character
    public Player(Vector2 position, String playerID) {
        this.position = position;
        bounds1.height = IMAGE_HEIGHT/2;
        bounds1.width = IMAGE_WIDTH;

        bounds2.height = IMAGE_HEIGHT;
        bounds2.width = IMAGE_WIDTH/2;
        health = MAX_HEALTH;
        armor = MAX_ARMOR;
        Id = playerID;
        rotation = 0;

        polygon = new Polygon(new float[]{
           -IMAGE_WIDTH/4, IMAGE_HEIGHT/2, //A
           IMAGE_WIDTH/4, IMAGE_HEIGHT/2, //B
           IMAGE_WIDTH/4, IMAGE_HEIGHT/4, //C
           IMAGE_WIDTH/2, IMAGE_HEIGHT/4, //D
           IMAGE_WIDTH/2, -IMAGE_HEIGHT/4, //E
           IMAGE_WIDTH/4, -IMAGE_HEIGHT/4, //F
           IMAGE_WIDTH/4, -IMAGE_HEIGHT/2, //G
           -IMAGE_WIDTH/4, -IMAGE_HEIGHT/2,//H
           -IMAGE_WIDTH/4, -IMAGE_HEIGHT/4,//I
           -IMAGE_WIDTH/2, -IMAGE_HEIGHT/4, //J
           -IMAGE_WIDTH/2, IMAGE_HEIGHT/4, //K
           -IMAGE_WIDTH/4, IMAGE_HEIGHT/4 //L

           //
        });
        polygon.setOrigin(0,0);
        polygon.setPosition(getPosition().x, getPosition().y);


        //triangleMesh = new TriangleMesh(true, 3, 3, )
    }


    //set angle to the passed in radians
    public void move(float rotation){

    }
    //Moves the Player by adding the two vectors
    public void move(Vector2 moveVector) {

        float xNew = getPosition().x + moveVector.x;
        float yNew = getPosition().y + moveVector.y;

        getPolygon().setPosition(xNew, yNew);

        if(collides()){
            getPolygon().setPosition(getPosition().x, getPosition().y);
            return;
        }

        setPosition(position.x + moveVector.x, position.y + moveVector.y);

    }

    //moves Player based on the given touchpad knob percentage
    public void move(float knobPercentageX, float knobPercentageY) {

        float xNew = getPosition().x + knobPercentageX * SPEED;
        float yNew = getPosition().y + knobPercentageY * SPEED;
        double rotNew = Math.atan2((double) knobPercentageY, (double) knobPercentageX);

//        getPolygon().setPosition(xNew, yNew);
//        getPolygon().setRotation((float) (Math.toDegrees(rotNew)));
//
//        if(collides()){
//            getPolygon().setPosition(getPosition().x, getPosition().y);
//            getPolygon().setRotation((float) Math.toDegrees(getRotation()));
//            return;
//        }
        setRotation(rotNew);
        setPosition(xNew, yNew);
    }

    //Launches a skillshot in the direction the player is facing, TODO: params TBD
    public void skillshot(Vector2 attackVector) {

    }

    //The aoe attack that launches when button is pressed TODO: params TBD
    public void attack() {

    }

    //if double tap on location blink onto that location TBD: determine range of blink
    public void blink(Vector2 blinkVector) {

    }

    //returns true if they are the same player
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            //check if they are the same player by comparing Id
            return ((Player) obj).getId().equals(getId());
        } else
            return false;

    }

    public String getId() {
        return Id;
    }

    public Vector2 getPosition() {
        return position;
    }

    //Sets the position to the passed in x and y
    public void setPosition(float x, float y) {

        getPolygon().setPosition(x, y);

        if(collides()){
            getPolygon().setPosition(getPosition().x, getPosition().y);
           return;
        }

        position.set(x, y);
    }

    public void setRotation(double radians){


        //getPolygon().setPosition(xNew, yNew);
        getPolygon().setRotation((float)(Math.toDegrees(radians)));

        if(collides()){
            //getPolygon().setPosition(getPosition().x, getPosition().y);
            getPolygon().setRotation((float) Math.toDegrees(getRotation()));
            return;
        }

        rotation = radians;
        //getPolygon().setRotation((float) (Math.toDegrees(radians)));
    }
    public int getHealth() {
        return this.health;
    }
    //returns the rectangle hitbox from nose to tail of this Player
    public Rectangle getBounds1() {
        return bounds1;
    }
    //returns the rectangle hitbox of wingspan
    public Rectangle getBounds2() {
        return bounds2;
    }

    //returns polygons corresponding to the bounds
    public Polygon getPolygon()  { return polygon;  }
    //TODO we might not need this anymore
    //Updates Player's position based on velocity
    public void update(float delta) {
        // position.add(velocity.cpy().scl(delta));

    }

    public double getRotation(){
        return rotation;
    }

    public boolean collides(Player player){
        return Intersector.overlapConvexPolygons(player.getPolygon(), getPolygon());
    }

    public boolean collides(Obstacle obstacle){
        return Intersector.overlapConvexPolygons(obstacle.polygon, getPolygon());
    }

    public void setWorld(World w){
        world = w;
    }

    public boolean collides(){
        for(Obstacle o : world.getObstacles()){
            if(collides(o)){
                return true;
            }
        }

        int collideCount = 0;
        for(Player p: world.getPlayers()){
            if(collides(p)){
                collideCount++;
                if(collideCount > 1)
                    return true;
            }
        }
        return false;
    }
}
