package com.cs110.app.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by clarencenguyen on 2/19/16.
 */
public class Attack
{

    int velocity = 10;
    float xPos;
    float yPos;
    double rad;
    double xDist;
    double yDist;
    int type;
    double CONST_FACTOR;
    boolean active = false;
    Rectangle bounds;
    int duration;
    World w;

    public Attack(float x, float y, int center ,double rad,World w)
    {
        this(x,y,center,rad,555,w);
    }

    public Attack(float x, float y,int center, double rad, int d,World w)
    {
        this.w = w;
        w.addAttack(this);
        duration = d;
        xPos = x + (float) calculateX(70,rad);
        yPos =  y + (float)calculateY(70,rad);
        this.rad = rad ;
        xDist = 0;
        CONST_FACTOR =1;
        yDist = 0;
        active = true;
        velocity = 2;
    }

    public Attack(float x,float y, int center, double rad, World w, int type)
    {
        this(x,y,center,rad,555,w);
        this.type = type;

        if (type == 1)  //rapid fire attack
            bounds = new Rectangle(x,y,5,5);

        else if (type == 0) //big ball attack
            bounds = new Rectangle(x,y,100,100);
        else
            bounds = new Rectangle(x,y,3,3);
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
        if (active)
        {

            //This is where I should implement the x,y position of the attack depending on player's orientation

            xDist += CONST_FACTOR*calculateX(velocity,rad);
            yDist += CONST_FACTOR*calculateY(velocity,rad);
            CONST_FACTOR+= 0.05;

            if (--duration == 0)
            {
                active = false;
                w.removeAttack();
            }
        }
    }

}
