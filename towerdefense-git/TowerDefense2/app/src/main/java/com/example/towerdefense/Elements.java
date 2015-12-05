package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Elements {
    private final Rect sourceRect;
    protected int width;
    protected int height;
    protected double x;
    protected double y;
    protected Bitmap bitmap;
    public Elements(double x, double y,Bitmap bitmap){
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        this.sourceRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
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
        Rect destRect = new Rect((int) this.getX() - this.width/2, (int) this.getY() - height/2, (int) this.getX() + width/2, (int) this.getY() + height/2);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }


}
