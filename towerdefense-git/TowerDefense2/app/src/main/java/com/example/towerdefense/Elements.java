package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Elements {
    private int x;
    private int y;
    private Bitmap bitmap;

    public Elements(int x, int y, Bitmap bitmap){
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
    }

    protected int getX(){
        return this.x;
    }

    protected void setX(int x){
        this.x = x;
    }

    protected int getY(){
        return this.y;
    }

    protected void setY(int y){
        this.y = y;
    }

    protected void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    protected Bitmap getBitmap(){
        return this.bitmap;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x- (bitmap.getWidth()/2), y -(bitmap.getHeight()/2), null);
    }


}
