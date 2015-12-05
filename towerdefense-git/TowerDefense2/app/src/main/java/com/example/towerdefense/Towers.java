package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Towers extends Elements{
    private static final String TAG = MainGamePanel.class.getSimpleName();

    private long lastFired;
    private boolean touched;
    private int attack;
    private int armorPen;
    private int magicPen;
    private int range;
    private int recharge;

    public Towers(int x, int y, Bitmap bitmap,int attack, int range, int armorPen, int magicPen, int recharge) {
        super(x, y, bitmap);
        this.attack = attack;
        this.range = range;
        this.armorPen = armorPen;
        this.magicPen = magicPen;
        this.lastFired = 0;
        this.recharge = recharge; //tir toutes les recharge millisecondes
    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (this.getX()-this.getBitmap().getWidth()/2) && (eventX <= (this.getX()+this.getBitmap().getWidth()/2))){
            if ((eventY >= (this.getY() - this.getBitmap().getHeight()/2)) && (eventY <= (this.getY() + this.getBitmap().getHeight()/2))) {
                //tower is touched
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }
    }

    public boolean canFire(){
        if (System.currentTimeMillis() > this.lastFired + this.recharge){
            return true;
        }else {
            return false;
        }
    }

    public void fire(){
        this.lastFired = System.currentTimeMillis();
    }

    public boolean isTouched(){
        return touched;
    }

    public void setTouched(boolean touched){
        this.touched = touched;
    }

    public int getRange() {
        return range;
    }

    public void shot(List<Shot> shots, Context context, Enemy enemy) {
    }
}
