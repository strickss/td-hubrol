package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Brieuc on 05-12-15.
 */
public class Fireball extends Shot {
    private static final String TAG = Fireball.class.getSimpleName();

    public Fireball(int x, int y, Context context, Enemy enemy) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.fireball), enemy, 10);
        this.bitmapAngle = 0;
        //Log.d(TAG, "Tir");
    }
}
