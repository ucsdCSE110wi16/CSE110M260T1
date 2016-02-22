package com.cs110.app.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by clarencenguyen on 2/19/16.
 */
public class Attack
{

    int velocity = 10;
    int xPos;
    int yPos;
    double rad;
    double xDist;
    double yDist;
    boolean active = false;
    Rectangle bounds;
    int duration;

    public Attack(int center ,double rad)
    {
        this(center,rad,555);
    }

    public Attack(int center, double rad, int d)
    {
        duration = d;
        bounds = new Rectangle(25,25,400,5000);
        xPos = (int)calculateX(center,rad);
        yPos = (int)calculateY(center, rad);
        this.rad = rad * (180/Math.PI);
        System.out.println(this.rad);
        xDist = 0;
        yDist = 0;
        active = true;
        velocity = 2;
    }

    public Rectangle getBounds() { return bounds;}

    public int getXPos() { return xPos;}
    public double getXDist() { return xDist;}
    public int getYPos() { return yPos;}
    public double getYDist() { return yDist;}

    private double calculateX(int center,double rad)
    {
        return Math.abs((double) center * Math.cos(rad));

    }

    private double calculateY(int center, double rad)
    {
        return Math.abs((double) center * Math.sin(rad));
    }
    public void update()
    {
        if (active)
        {
            xDist += velocity;

            //This is where I should implement the x,y position of the attack depending on player's orientation
            // ie if (player is facing left) bounds,setX(xDist)
            //    if (player is facing top ) bounds.setY(xDist)
            //    if (player is facing right ) bounds.setX( - xDist)
            //    if (player is facing bottom ) bounds.setY( -xDist)
            if (rad >= 0.0 && rad <= 90) // 1st quadrant
            {
                xDist += calculateX(velocity,rad);
                yDist += calculateY(velocity,rad);
            }

            else if (rad > 90 && rad <= 180) // 2nd quadrant
            {
                xDist -= 2*calculateX(velocity,rad);
                yDist += calculateY(velocity,rad);
            }

            else if ( rad < 0.0 && rad > -90) // 3rd quadrant
            {
                xDist += calculateX(velocity,rad);
                yDist -= calculateY(velocity,rad);
            }

            else                               // 4th quadrant
            {
                xDist -= 2*calculateX(velocity,rad);
                yDist -= calculateY(velocity,rad);
            }

            if (--duration == 0)
                active = false;
        }
    }

}
