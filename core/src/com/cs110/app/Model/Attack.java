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
    boolean active = false;
    Rectangle bounds;
    int duration;
    public int center;

    public Vector2 position;
    public Vector2 velocity2;
    World w;
    Polygon polygon; //used for collision detection

    public Attack(float x, float y, int center ,double rad,World w)
    {
        this(x,y,center,rad,555,w);
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
        velocity = 2;
    }
    public Attack(int center, double rad, int d){}

    //MAIN CONSTRUCTOR
    public Attack(float x, float y,int center, double rad, int d,World w)
    {
        w.attackOccured = true;
        this.w = w;
        w.addAttack(this);
        duration = d;
//        xPos = x + (float) calculateX(70 + 20,rad);
//        yPos =  y + (float)calculateY(70 + 20, rad);

        xPos = x + (float) calculateX(center,rad);
        yPos =  y + (float)calculateY(center, rad);
        this.rad = rad ;
        xDist = 0;
        CONST_FACTOR =1;
        yDist = 0;
        active = true;
        velocity = 2;
        this.center = center;
        //polygon = new Polygon(new float[] {10, 10,});
    }

    public Attack(float x,float y, int center, double rad, World w, int type)
    {
        this(x,y,center,rad,555,w);
        this.type = type;

        if (type == 1) {  //rapid fire attack
            bounds = new Rectangle(x, y, 5, 5);


        }
        else if (type == 0) //big ball attack
            bounds = new Rectangle(x,y,25,25);
        else
            bounds = new Rectangle(x,y,3,3);


        float width = bounds.width + 5;
        float diag_dist = (float) (width/Math.sqrt(2));
        polygon = new Polygon(new float[]{
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
            if(getType()!= 2)
                collidesWithPlayer();

            if (--duration == 0)
            {
                active = false;
                w.removeAttack();
            }
        }
    }

    public Polygon getPolygon(){
        return polygon;
    }

    private void collidesWithPlayer(){
        for( Player chimichanga : w.getPlayers()){
            if(Intersector.overlapConvexPolygons(chimichanga.getPolygon(), getPolygon())){
                chimichanga.health -= 10;
                active = false;
            }

        }
    }


}
