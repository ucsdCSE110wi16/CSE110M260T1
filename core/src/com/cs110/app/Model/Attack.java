package com.cs110.app.Model;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by clarencenguyen on 2/19/16.
 */
public class Attack
{

    int velocity = 1;
    int xPos;
    int yPos;
    boolean active = false;
    Rectangle bounds;
    int duration;

    public Attack()
    {
        this(0,0,5);
    }
    public Attack(int x, int y, int d)
    {
        duration = d;
        bounds = new Rectangle(x,y,3,1);
        xPos = x;
        yPos = y;
        active = true;
        velocity = 2;
    }

    public void update()
    {
        if (active)
        {
            xPos += velocity;
            yPos += velocity;
            bounds.setX(xPos);
            bounds.setY(yPos);

            if (--duration == 0)
                active = false;
        }
    }

}
