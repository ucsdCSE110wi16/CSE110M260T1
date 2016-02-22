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
    int xDist;
    boolean active = false;
    Rectangle bounds;
    int duration;

    public Attack(int x, int y)
    {
        this(x,y,555);
    }

    public Attack(int x, int y, int d)
    {
        duration = d;
        bounds = new Rectangle(25,25,400,5000);
        xPos = x;
        yPos = y;
        xDist = 0;
        active = true;
        velocity = 2;
    }

    public Rectangle getBounds() { return bounds;}

    public int getXPos() { return xPos;}
    public int getXDist() { return xDist;}
    public int getYPos() { return yPos;}
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
            bounds.setX(xDist);

            if (--duration == 0)
                active = false;
        }
    }

}
