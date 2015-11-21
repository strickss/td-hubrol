package com.example.towerdefense;

import android.graphics.Bitmap;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Tower extends Elements{
    private boolean touched;
    private int attack;
    private int armorPen;
    private int magicPen;

    public Tower(int x, int y, Bitmap bitmap,int attack, int armorPen, int magicPen) {
        super(x, y, bitmap);
        this.attack = attack;
        this.armorPen = armorPen;
        this.magicPen = magicPen;
    }

    public boolean isTouched(){
        return touched;
    }

    public void setTouched(boolean touched){
        this.touched = touched;
    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (this.getX()-this.getBitmap().getWidth()/2) && (eventX <= (this.getX()+this.getBitmap().getWidth()/2))){
            if (eventY >= (this.getY() - this.getBitmap().getHeight() / 2) && (this.getY() <= (this.getY() + this.getBitmap().getHeight() / 2))) {
                //tower is touched
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }
    }

}