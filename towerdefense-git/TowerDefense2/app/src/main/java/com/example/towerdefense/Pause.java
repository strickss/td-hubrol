package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Pause extends Buttons{

    public Pause(int x, int y, Context context) {
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.pause));
        this.width = 1*bitmap.getWidth();
        this.height = 1*bitmap.getHeight();
    }


}
