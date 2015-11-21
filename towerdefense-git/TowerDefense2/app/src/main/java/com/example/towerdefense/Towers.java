package com.example.towerdefense;

import android.graphics.Bitmap;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Towers extends Elements{
    private boolean touched;
    private int attack;
    private int armorPen;
    private int magicPen;
    private int range;

    public Towers(int x, int y, Bitmap bitmap,int attack, int range, int armorPen, int magicPen) {
        super(x, y, bitmap);
        this.attack = attack;
        this.range = range;
        this.armorPen = armorPen;
        this.magicPen = magicPen;
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

    public boolean isTouched(){
        return touched;
    }

    public void setTouched(boolean touched){
        this.touched = touched;
    }

}
