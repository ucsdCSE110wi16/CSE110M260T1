package com.cs110.app.Controller;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.cs110.app.Model.Attack;
import com.cs110.app.Model.Player;
import com.cs110.app.Model.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.Text;

/**
 * Created by Yashwanth on 1/31/16.
 */

public class WorldController
{
    int durationY;
    int durationZ;

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

//    static ArrayList<TextButton> button;
    static Map<Keys, TextButton> button = new HashMap<Keys, TextButton>();
    static Map<Keys, Integer> duration = new HashMap<Keys, Integer>();
    static Map<Keys,Integer> coolDown = new HashMap<Keys, Integer>();

    static
    {
        duration.put(Keys.BUTTON_X,0);
        duration.put(Keys.BUTTON_Y,0);
        duration.put(Keys.BUTTON_Z,0);
        coolDown.put(Keys.BUTTON_X,0);
        coolDown.put(Keys.BUTTON_Y,500);
        coolDown.put(Keys.BUTTON_Z,200);
    }

    public WorldController(World world)
    {
        this.world = world;
        this.player = world.getSelfPlayer();
    }

    public void addButton (TextButton b , char type)
    {
        if (type =='x')  button.put(Keys.BUTTON_X, b);
        else if (type =='y')  button.put(Keys.BUTTON_Y, b);
        else    button.put(Keys.BUTTON_Z, b);
    }

    /* Following methods are for keys pressed and touched */

    //This is simialr to the update method in screen it gets called every cycle

    public void buttonXPressed()
    {
        TextButton x = button.get(Keys.BUTTON_X);
        keys.put(Keys.BUTTON_X, true);
        System.out.println("Rotation: " + world.getSelfPlayer().getRotation());
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y,world.getSelfPlayer().IMAGE_WIDTH / 2, world.getSelfPlayer().getRotation(),world,1);
    }
    public void buttonYPressed()
    {
        keys.put(Keys.BUTTON_Y,true);
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y,world.getSelfPlayer().IMAGE_WIDTH / 2, world.getSelfPlayer().getRotation(),world,0);
        button.get(Keys.BUTTON_Y).setTouchable(Touchable.disabled);
        duration.put(Keys.BUTTON_Y,0);
    }
    public void buttonZPressed()
    {
        keys.put(Keys.BUTTON_Z,true);
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y,world.getSelfPlayer().IMAGE_WIDTH / 2, world.getSelfPlayer().getRotation(),world,2);
        button.get(Keys.BUTTON_Z).setTouchable(Touchable.disabled);
        duration.put(Keys.BUTTON_Z,0);
    }

    public void buttonXReleased()
    {
        keys.put(Keys.BUTTON_X, false);

    }

    private String getButtonText(Keys k)
    {
        if (k == Keys.BUTTON_X)  return "X";
        else if (k == Keys.BUTTON_Y)  return "Y";
        else return "Z";
    }


    public void buttonYReleased()
    {
    }

    public void buttonZReleased()
    {
    }

    //This is similar to the update method in screen it gets called every cycle
    public void update(float delta)
    {
        processInput();
        player.update(delta);
    }

    private void updateAttack(Keys k)
    {
        if(keys.get(k) && duration.get(k).equals(coolDown.get(k)))
        {
            button.get(k).setText(getButtonText(k));
            button.get(k).setTouchable(Touchable.enabled);
            keys.put(k,false);
        }

        else if(keys.get(k))
        {
            button.get(k).setText("" + duration.get(k)/100);
            duration.put(k,duration.get(k) + 1);
        }

        else
            return;

    }



    public void processInput()
    {

        updateAttack(Keys.BUTTON_Y);
        updateAttack(Keys.BUTTON_Z);

    }

}
