package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Elements {
    protected double x;
    protected double y;
    protected Bitmap bitmap;
    public Elements(double x, double y,Bitmap bitmap){
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
    }

    protected double getX(){
        return this.x;
    }

    protected void setX(double x){
        this.x = x;
    }

    protected double getY(){
        return this.y;
    }

    protected void setY(double y){
        this.y = y;
    }

    protected void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    protected Bitmap getBitmap(){
        return this.bitmap;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, (int) x- (bitmap.getWidth()/2),(int) y -(bitmap.getHeight()/2), null);
    }


}
