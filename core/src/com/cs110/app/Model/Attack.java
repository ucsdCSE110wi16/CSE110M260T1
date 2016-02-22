package com.cs110.app.Model;

import com.badlogic.gdx.math.Rectangle;
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
    double CONST_FACTOR;
    boolean active = false;
    Rectangle bounds;
    int duration;

    public Attack(float x, float y, int center ,double rad)
    {
        this(x,y,center,rad,555);
    }

    public Attack(float x, float y,int center, double rad, int d)
    {
        duration = d;
        bounds = new Rectangle(25,25,400,5000);
        xPos = x + (float) calculateX(30,rad);
        yPos =  y + (float)calculateY(30,rad);
        this.rad = rad ;
        System.out.println(this.rad);
        xDist = 0;
        CONST_FACTOR =1;
        yDist = 0;
        active = true;
        velocity = 2;
    }

    public Rectangle getBounds() { return bounds;}

    public float getXPos() { return xPos;}
    public double getXDist() { return xDist;}
    public float getYPos() { return yPos;}
    public double getYDist() { return yDist;}

    private double calculateX(int center,double rad)
    {
        return (double) center * Math.cos(rad);

    }

    public boolean isActive(){
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

//            if (rad >= 0.0 && rad <= 90.0) // 1st quadrant
//            {
                xDist += CONST_FACTOR*calculateX(velocity,rad);
                yDist += CONST_FACTOR*calculateY(velocity,rad);
//            System.out.println(yDist);
                                CONST_FACTOR+= 0.05;
//            }
//
//            else if (rad > 90.0 && rad <= 180.0) // 2nd quadrant
//            {
//                xDist -= CONST_FACTOR*calculateX(velocity,rad);
//                yDist += calculateY(velocity,rad);
//                CONST_FACTOR+= 0.05;
//            }
//
//            else if ( rad < 0.0 && rad > -90.0) // 3rd quadrant
//            {
//                xDist += CONST_FACTOR*calculateX(velocity,rad);
//                yDist -= calculateY(velocity,rad);
//                                CONST_FACTOR+= 0.05;
//            }
//
//            else                               // 4th quadrant
//            {
//                xDist -= CONST_FACTOR*calculateX(velocity,rad);
//                yDist -= calculateY(velocity,rad);
//                                CONST_FACTOR+= 0.05;
//            }

            if (--duration == 0)
                active = false;
        }
    }

}
