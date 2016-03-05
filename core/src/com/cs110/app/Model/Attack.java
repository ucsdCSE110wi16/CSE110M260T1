package com.cs110.app.Model;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by clarencenguyen on 2/19/16.
 */
public class Attack
{

    int velocity = 10;
    public float xPos;
    public float yPos;
    public double rad;
    double xDist;
    double yDist;
    int type;
    double CONST_FACTOR;
    public boolean drawn = false;
    boolean active = false;
    Rectangle bounds;
    int duration;
    public int center;
    private String senderID;

    public Vector2 position;
    public Vector2 velocity2;
    World w;
    Polygon polygon; //used for collision detection

    public Attack(float x, float y, int center ,double rad,World w)
    {
        this(x,y,rad,555,w, center);
    }
    public Attack(float xPos, float yPos, float rad) {
        this.xPos = (int)xPos;
        this.yPos = (int)yPos;
        System.out.println("new attack");
        System.out.println(this.xPos);
        System.out.println(this.yPos);
        this.rad = (double)rad;
        duration = 555;
        bounds = new Rectangle(25,25,400,5000);
        xDist = 0;
        CONST_FACTOR =1;
        yDist = 0;
        active = true;
        velocity = 20;
    }
    public Attack(int center, double rad, int d){}

    //FOR NETWORKING ONLY


    public Attack(float x,float y, double rad, World w, int type, String sender){
        this(x,y,rad,w,type);
        setSenderID(sender);
    }



    //MAIN CONSTRUCTOR
    public Attack(float x, float y, double rad, int d,World w, int type)
    {
        int center;

        if (type == 1) {  //rapid fire attack
            center = w.getSelfPlayer().IMAGE_WIDTH / 2 + 15;
        }
        else if (type == 0) { //big ball attack
            center = w.getSelfPlayer().IMAGE_WIDTH / 2 + 35;
        }
        else if (type == 2){
            center = w.getSelfPlayer().IMAGE_WIDTH / 2;
        }
        else{
            center = type;
        }
        w.attackOccured = true;

        this.w = w;
        w.addAttack(this);
        duration = d;
//        xPos = x + (float) calculateX(70 + 20,rad);
//        yPos =  y + (float)calculateY(70 + 20, rad);

//        xPos = x + (float) calculateX(center,rad);
//        yPos =  y + (float)calculateY(center, rad);
        this.rad = rad ;
        xDist = 0;
        CONST_FACTOR =1;
        yDist = 0;
        active = true;
        velocity = 20;
        this.center = center;
        //polygon = new Polygon(new float[] {10, 10,});
    }

    //MAIN CONSTRUCTOR
    public Attack(float x,float y, double rad, World w, int type)
    {
        this(x,y,rad,555,w, type);

        this.type = type;

        if (type == 1)    //rapid fire attack
        {
            bounds = new Rectangle(x, y, 5, 5);
            reCalc(x,y,center+15,rad);
        }

        else if (type == 0)   //big ball attack
        {
            bounds = new Rectangle(x, y, 55, 55);
            reCalc(x, y,center + 35, rad);
        }

        else
        {
            bounds = new Rectangle(x, y, 10, 10);
            reCalc(x,y,center+15,rad);
        }


        float width = bounds.width + 5;
        float diag_dist = (float) (width/Math.sqrt(2));
        polygon = new Polygon(new float[]
                              {
                                  0, width, //A
                                  diag_dist, diag_dist,//B
                                  width, 0, //C
                                  diag_dist, -diag_dist, //D
                                  0, -width, //E
                                  -diag_dist, -diag_dist, //F
                                  -width, 0, //G
                                  -diag_dist, diag_dist,//H
                              });

        polygon.setOrigin(0,0);
        polygon.setPosition(xPos, yPos);
    }

    public void reCalc(float x, float y, int center, double rad )
    {
        xPos = x + (float) calculateX(center,rad);
        yPos = y + (float) calculateY(center, rad);
    }

    public Rectangle getBounds() { return bounds;}
    public int getType() { return type;}
    public float getXPos() { return xPos;}
    public double getXDist() { return xDist;}
    public float getYPos() { return yPos;}
    public double getYDist() { return yDist;}

    private double calculateX(int center,double rad)
    {
        return (double) center * Math.cos(rad);

    }

    public boolean isActive()
    {
        return active;
    }

    private double calculateY(int center, double rad)
    {
        return (double) center * Math.sin(rad);
    }
    public void update()
    {
        if (active) {
            //This is where I should implement the x,y position of the attack depending on player's orientation

            xDist += CONST_FACTOR*calculateX(velocity,rad);
            yDist += CONST_FACTOR*calculateY(velocity, rad);
            CONST_FACTOR+= 0.05;
            polygon.setPosition((float)(xPos + xDist), (float) (yPos + yDist));
            collidesWithPlayer();
            collidesWithObstacle();

            if (--duration == 0)
            {
                active = false;
                w.removeAttack();
            }
        }
    }

    public Polygon getPolygon()
    {
        return polygon;
    }

    private void collidesWithPlayer()
    {
        for( Player chimichanga : w.getPlayers())
        {
            if(Intersector.overlapConvexPolygons(chimichanga.getPolygon(), getPolygon()))
            {
                chimichanga.health -= 10;
                active = false;
            }

        }
    }

    private void collidesWithObstacle(){
        for( Obstacle o : w.getObstacles()){
            if(Intersector.overlapConvexPolygons(o.getPolygon(), getPolygon())){
                active = false;
            }
        }
    }

    public void setSenderID(String id){
        senderID = id;
    }

    public String getSenderID(){
        return senderID;
    }


}
