package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Brieuc on 22-11-15.
 */
public class Missile extends Shot {
    public Missile(int x, int y, Context context, int targetX, int targetY) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.missile), targetX, targetY);
    }
}
