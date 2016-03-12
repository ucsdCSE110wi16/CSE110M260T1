package com.cs110.app.Controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

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



public class WorldController implements GestureDetector.GestureListener
{   private final long tapDiff = 500; //50 ms window to double tap
    private long tapStartTime = 0;

    int durationY;
    int durationZ;

    public static int tapCounter;
    //private tapStartTime;



    //These are the possible buttons that can be pressed
    enum Keys
    {
        BUTTON_X, BUTTON_Y, BUTTON_Z
    }

    private World world;
    private Player player;
    public static int tapCount=0;

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
    static
    {
        duration.put(Keys.BUTTON_X,0);
        duration.put(Keys.BUTTON_Y,0);
        duration.put(Keys.BUTTON_Z,0);
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
        //world.getSelfPlayer().setHealth(world.getSelfPlayer().getHealth()-10);
        System.out.println("Rotation: " + world.getSelfPlayer().getRotation());
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y, world.getSelfPlayer().getRotation(),world,1);
    }
    public void buttonYPressed()
    {
        keys.put(Keys.BUTTON_Y,true);
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y, world.getSelfPlayer().getRotation(),world,0);
        button.get(Keys.BUTTON_Y).setTouchable(Touchable.disabled);
        duration.put(Keys.BUTTON_Y,500);
    }
    public void buttonZPressed()
    {
        keys.put(Keys.BUTTON_Z,true);
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y, world.getSelfPlayer().getRotation(),world,2);
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y,  world.getSelfPlayer().getRotation() + Math.PI/8,world,2);
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y, world.getSelfPlayer().getRotation() - Math.PI/8,world,2);
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y, world.getSelfPlayer().getRotation() + Math.PI/16,world,2);
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y, world.getSelfPlayer().getRotation() - Math.PI/16,world,2);
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y, world.getSelfPlayer().getRotation() + Math.PI/32,world,2);
        new Attack(world.getSelfPlayer().getPosition().x,world.getSelfPlayer().getPosition().y, world.getSelfPlayer().getRotation() - Math.PI/32,world,2);
        button.get(Keys.BUTTON_Z).setTouchable(Touchable.disabled);
        duration.put(Keys.BUTTON_Z,200);
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
        if(keys.get(k) && duration.get(k).equals(0))
        {
            button.get(k).setText(getButtonText(k));
            button.get(k).setTouchable(Touchable.enabled);
            keys.put(k,false);
        }

        else if(keys.get(k))
        {
            button.get(k).setText("" + duration.get(k)/100);
            duration.put(k,duration.get(k) - 1);
        }

        else
            return;

    }

    public void processInput()
    {

        updateAttack(Keys.BUTTON_Y);
        updateAttack(Keys.BUTTON_Z);

    }

    public void blinkPressed(Vector2 blinkVector) {
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        long currTime = World.gameTime;
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        System.out.println(x + "COORDINATE" + y);


        //dont want to blink if we are on attack buttons
        if(x>=width/2 + width/15 && x<=width && y>=height-height/4 && y<=height) {
            return false;
        }


        //dont blink if we are on the move pad
        if(x>=0 && x<=width/3.6 && y>=height-height/2 && y<=height) {
            return false;
        }






        if(currTime - tapStartTime > tapDiff) {
            tapStartTime = currTime;

        }
        else {


            int wMid = Gdx.graphics.getWidth()/2;
            int hMid = Gdx.graphics.getHeight()/2;


            //vector of the click from window's topLeft
            Vector2 clickPos = new Vector2(x,y);

            //position of player in GAMESCREEN, not window
            Vector2 currPos = world.getSelfPlayer().getPosition();
            Vector2 newPos = new Vector2(currPos.x,currPos.y);
            //gets the topLeft corner of the window in GAMESCREEN coordinates
            Vector2 topLeft = newPos.add(-wMid,hMid);

            //gets the spot we want to move to.
            Vector2 movePos = topLeft.add(x,-y);

            //System.out.println("teleport x: " + movePos.x + "teleport y: " + y);
            //if out of bounds, dont move it
            if(movePos.x > 2500 || movePos.x < -2500 || movePos.y > 2500 || movePos.y < -2500){
                return false;
            }
            world.getSelfPlayer().setPosition(movePos.x, movePos.y);
            return true;

        }

        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }



}
