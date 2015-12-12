package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Brieuc on 05-12-15.
 */
public class Iceball extends Shot {
    private static final String TAG = Iceball.class.getSimpleName();

    public Iceball(int x, int y, Context context, Enemy enemy, int damage) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.iceball), enemy, 10, damage);
        this.bitmapAngle = 0;
        //Log.d(TAG, "Tir");
    }
}
