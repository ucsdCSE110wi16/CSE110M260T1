package com.cs110.app.Controller;

import com.cs110.app.Model.Player;
import com.cs110.app.Model.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yashwanth on 1/31/16.
 */
public class WorldController
{

    //These are the possible buttons that can be pressed
    enum Keys
    {
        BUTTON_X, BUTTON_Y, BUTTON_Z
    }

    private World world;
    private Player player;

    //This is a map associating each button with a boolean determining whether the key has been pressed or not

    //This is currently static because, I am assuming that every client will only have one World. Therefore we can access this things
    // from anywhere without creating a WorldController by making them static ~Yashwanth
    static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
    static
    {
        keys.put(Keys.BUTTON_X, false);
        keys.put(Keys.BUTTON_Y, false);
        keys.put(Keys.BUTTON_Z, false);
    };

    public WorldController(World world)
    {
        this.world = world;
        this.player = world.getPlayer();
    }

    /* Following methods are for keys pressed and touched */

    // public void upPressed()
    // {
    //     //The tutorial has keys.get(keys.put(Keys.LEFT, true);) but i don't see the point of the get ~Yashwanth
    //     keys.put(Keys.LEFT, true);
    // }

    // public void downPressed()
    // {
    //     keys.put(Keys.DOWN, true);
    // }

    // public void leftPressed()
    // {
    //     keys.put(Keys.LEFT, true);
    // }
    // public void rightPressed()
    // {
    //     keys.put(Keys.RIGHT, true); // }
    //

    public void buttonXPressed()
    {
        keys.put(Keys.BUTTON_X,true);

    }
    public void buttonYPressed()
    {
        keys.put(Keys.BUTTON_Y,true);
    }
    public void buttonZPressed()
    {
        keys.put(Keys.BUTTON_Z,true);
    }

    public void buttonXReleased()
    {
        keys.put(Keys.BUTTON_X,false);
    }

    public void buttonYReleased()
    {
        keys.put(Keys.BUTTON_Y,false);
    }

    public void buttonZReleased()
    {
        keys.put(Keys.BUTTON_Z,false);
    }

    //This is similar to the update method in screen it gets called every cycle
    public void update(float delta)
    {
        processInput();
        player.update(delta);
    }

    private void processInput()
    {

    }

}
