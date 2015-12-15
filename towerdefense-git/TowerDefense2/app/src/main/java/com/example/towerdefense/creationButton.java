package com.example.towerdefense;

import android.util.Log;

/**
 * Created by Brieuc on 05-12-15.
 */
public class creationButton {

    private static final String TAG = creationButton.class.getSimpleName();
    private boolean canCreate;

    private int delay;
    private int number;
    private long upTime = 0;


    public creationButton(int delay) {
        this.delay = delay;
        this.number = 0;
        this.upTime = System.currentTimeMillis();
        this.canCreate = false;
    }

    public void update(){
        if (canCreate) {
            if (System.currentTimeMillis() - upTime > 3000){
                this.number = this.number +1;
                this.upTime = System.currentTimeMillis();
            }
        } else {
            if (System.currentTimeMillis() - upTime > delay){
                this.canCreate = true;
            }
        }
    }

    public int getNumber() {
        return number;
    }
}
