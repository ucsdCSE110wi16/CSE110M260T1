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

    static ArrayList<TextButton> button;


    public WorldController(World world)
    {
        this.world = world;
        this.player = world.getSelfPlayer();
        this.button = new ArrayList<TextButton>();
    }

    public void addButton (TextButton b)
    {
        button.add(b);
    }

    public TextButton getButton(TextButton b)
    {
        int index = button.indexOf(b);
        return button.get(index);
    }

    /* Following methods are for keys pressed and touched */

    //This is simialr to the update method in screen it gets called every cycle

    public void buttonXPressed()
    {
        TextButton x = button.get(0);
        keys.put(Keys.BUTTON_X, true);
        System.out.println("Rotation: " + world.getSelfPlayer().getRotation());
        world.addAttack((new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y,world.getSelfPlayer().IMAGE_WIDTH / 2, world.getSelfPlayer().getRotation())));
    }
    public void buttonYPressed()
    {
        keys.put(Keys.BUTTON_Y,true);
        button.get(1).setTouchable(Touchable.disabled);
        new Timer().schedule(new Timer.Task()
        {
            int CD = 10;
            @Override
            public void run()
            {
                String in = "" + CD--;
                button.get(1).setText(in);
            }

        },0,1,10);
        new Timer().schedule(new Timer.Task()
        {
            @Override
            public void run()
            {
                button.get(1).setText("Y");
                button.get(1).setTouchable(Touchable.enabled);
            }

        },10,1,1);
    }
    public void buttonZPressed()
    {
        keys.put(Keys.BUTTON_Z,true);
        button.get(2).setTouchable(Touchable.disabled);
        new Timer().schedule(new Timer.Task()
        {
            int CD = 10;
            @Override
            public void run()
            {
                String in = "" + CD--;
                button.get(2).setText(in);
            }

        },0,1,10);
        new Timer().schedule(new Timer.Task()
        {
            @Override
            public void run()
            {
                button.get(2).setText("Z");
                button.get(2).setTouchable(Touchable.enabled);
            }

        },10,1,1);
    }

    public void buttonXReleased()
    {
        keys.put(Keys.BUTTON_X, false);

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
