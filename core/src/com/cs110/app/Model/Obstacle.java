package com.cs110.app.Model;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by clarencenguyen on 1/28/16.
 */
public class Obstacle {
    static final float SIZE = 20f;

    Rectangle bounds = new Rectangle();
    Vector2 position;
    Polygon polygon;

    /**
     * Constructor for Obstacle with size 1x1
     * @param pos position
     */
    Obstacle(Vector2 pos) {
        bounds.width = SIZE;
        bounds.height = SIZE;
        position = pos;
        polygon = new Polygon(new float[]{
                0,0,
                bounds.width, 0,
                bounds.width, bounds.height,
                0, bounds.height
        });

        polygon.setPosition(getPosition().x, getPosition().y);
    }

    /*
    Constructor for obstacles with give position and size
     */
    Obstacle(Vector2 pos, float w, float h) {
        bounds.width = w;
        bounds.height = h;
        position = pos;

        polygon = new Polygon(new float[]{
                0,0,
                bounds.width, 0,
                bounds.width, bounds.height,
                0, bounds.height
        });

        polygon.setPosition(getPosition().x, getPosition().y);
    }

    public Rectangle getBounds() { return bounds; }

    public Vector2 getPosition() { return position; }

    public Polygon getPolygon()  { return polygon; }
}
