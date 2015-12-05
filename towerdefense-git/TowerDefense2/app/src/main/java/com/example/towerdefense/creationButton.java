package com.example.towerdefense;

/**
 * Created by Brieuc on 05-12-15.
 */
public class creationButton {

    private int delay;
    private int number;
    private long upTime;


    public creationButton(int delay) {
        this.delay = delay;
        this.number = 0;
        this.upTime = 0;
    }

    public void update(long time){
        if (time > delay) {
            if (time - upTime > 3000){
                this.number ++;
                this.upTime = time;
            }
        }
    }

    public int getNumber() {
        return number;
    }
}
