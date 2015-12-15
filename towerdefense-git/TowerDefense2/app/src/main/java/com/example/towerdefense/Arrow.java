package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Brieuc on 22-11-15.
 */
public class Arrow extends Shot {
    private static final String TAG = Arrow.class.getSimpleName();

    public Arrow(int x, int y, Context context, Enemy enemy, int damage) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow), enemy, 13, damage);
        this.bitmapAngle = 0;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        //Log.d(TAG, "Tir");
    }
}
