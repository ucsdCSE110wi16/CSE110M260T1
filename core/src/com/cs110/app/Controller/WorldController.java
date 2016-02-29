package com.cs110.app.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.cs110.app.Model.Player;
import com.cs110.app.Model.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yashwanth on 1/31/16.
 */

public class WorldController implements GestureDetector.GestureListener
{   private final long tapDiff = 500; //50 ms window to double tap
    private long tapStartTime = 0;

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        long currTime = World.gameTime;
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        if(currTime - tapStartTime > tapDiff) {
            tapStartTime = currTime;

        }
        else {
            int wMid = Gdx.graphics.getWidth()/2;
            int hMid = Gdx.graphics.getHeight()/2;


            //vector of the click from window's topLeft
            Vector2 clickPos = new Vector2(x,y);
            System.out.println("clickPos x: " + clickPos.x);
            System.out.println("clickPos y: " + clickPos.y);

            //position of player in GAMESCREEN, not window
            Vector2 currPos = player.getPosition();


            //gets the topLeft corner of the window in GAMESCREEN coordinates
            Vector2 topLeft = currPos.add(-wMid,hMid);

            //gets the spot we want to move to.
            Vector2 movePos = topLeft.add(x,-y);

            player.setPosition(movePos.x, movePos.y);
            
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

    public WorldController(World world)
    {
        this.world = world;
        this.player = world.getPlayer();
    }

    /* Following methods are for keys pressed and touched */

    //This is simialr to the update method in screen it gets called every cycle

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


    private void processInput(){

    }

    public void blinkPressed(Vector2 blinkVector) {
        player.blink(blinkVector);
    }

}
