package com.cs110.app.Model;

import com.badlogic.gdx.Gdx;

/**
 * Created by clarencenguyen on 2/8/16.
 */
public class CoolDowns{
    private float coolDownTime;
    private int seconds;

    public CoolDowns(){
        coolDownTime = 10;
    }
    public void renderTime() {
        float deltaTime = Gdx.graphics.getRawDeltaTime();
        coolDownTime -= deltaTime;
        seconds = ((int)(coolDownTime)) % 60;
    }

    public void resetTimer() {
        coolDownTime = 10;
    }

    public String getTime(){
        return "" + seconds;
    }




}
