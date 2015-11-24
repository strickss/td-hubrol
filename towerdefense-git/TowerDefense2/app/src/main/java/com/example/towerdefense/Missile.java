package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Brieuc on 22-11-15.
 */
public class Missile extends Shot {
    private static final String TAG = Missile.class.getSimpleName();

    public Missile(int x, int y, Context context, Enemy enemy) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.missile), enemy, 10);
        Log.d(TAG, "Tir");
    }
}
