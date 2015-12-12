package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Towers extends Elements{
    private static final String TAG = Towers.class.getSimpleName();

    private long lastFired;
    private boolean touched;
    protected int penetration;
    private int range;
    private int recharge;
    protected int damage_max;
    protected int damage_min;
    private int level;

    public Towers(int x, int y, Bitmap bitmap, int range, int recharge) {
        super(x, y, bitmap);
        this.range = range;
        this.lastFired = 0;
        this.recharge = recharge; //tir toutes les recharge millisecondes
        this.level = 1;
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

    public int getDamage(int i, Enemy enemy) {
        int red;
        if (i==1) {
            red = enemy.getArmor() - this.getPen();
        } else {
            red = enemy.getRM() - this.getPen();
        }
        if (red >= 0){
            return (int) ((damage_min + Math.random() * (damage_max - damage_min))*(red/100));
        } else {
            return (int) (damage_min + Math.random() * (damage_max - damage_min));
        }
    }

    public int getPen() {
        return penetration;
    }

    public void upgrade() {
        this.damage_min = (int) Math.ceil(this.damage_min*1.1);
        this.damage_max = (int) Math.ceil(this.damage_max*1.1);
        this.range = (int) Math.ceil(this.range*1.1);
        this.recharge = (int) Math.floor(this.recharge*0.9);
        this.level ++;
    }

    public int getLevel() {
        return level;
    }
}
