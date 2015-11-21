package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Tower extends Towers{

    public Tower(int x, int y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.tower), 1,300,1,1);
    }

}