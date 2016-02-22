package com.cs110.app.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by clarencenguyen on 2/19/16.
 */
public class Attack
{

    int velocity = 10;
    public int xPos;
    public int yPos;
    public double rad;
    double xDist;
    double yDist;
    double CONST_FACTOR;
    boolean active = false;
    Rectangle bounds;
    int duration;
    public int center;

    public Vector2 position;
    public Vector2 velocity2;

    public Attack(int center ,double rad)
    {
        this(center,rad,555);
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
    public Attack(int center, double rad, int d)
    {
        duration = d;
        bounds = new Rectangle(25,25,400,5000);
        xPos = (int)calculateX(center,rad);
        yPos = (int)calculateY(center, rad);
        this.rad = rad * (180/Math.PI);
        System.out.println(this.rad);
        xDist = 0;
        CONST_FACTOR =1;
        yDist = 0;
        active = true;
        velocity = 2;
        this.center = center;
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
        if (active) {
            //This is where I should implement the x,y position of the attack depending on player's orientation
            // ie if (player is facing left) bounds,setX(xDist)
            //    if (player is facing top ) bounds.setY(xDist)
            //    if (player is facing right ) bounds.setX( - xDist)
            //    if (player is facing bottom ) bounds.setY( -xDist)
            if (rad >= 0.0 && rad <= 90) // 1st quadrant
            {
                xDist += CONST_FACTOR*calculateX(velocity,rad);
                yDist += calculateY(velocity,rad);
            }

            else if (rad > 90 && rad <= 180) // 2nd quadrant
            {
                xDist -= CONST_FACTOR*calculateX(velocity,rad);
                yDist += calculateY(velocity,rad);
                CONST_FACTOR+= 0.1;
            }

            else if ( rad < 0.0 && rad > -90) // 3rd quadrant
            {
                xDist += CONST_FACTOR*calculateX(velocity,rad);
                yDist -= calculateY(velocity,rad);
            }

            else                               // 4th quadrant
            {
                xDist -= CONST_FACTOR*calculateX(velocity,rad);
                yDist -= calculateY(velocity,rad);
            }

            if (--duration == 0)
                active = false;
        }
    }

}
