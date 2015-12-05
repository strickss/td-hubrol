package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.List;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Tower extends Towers{

    public Tower(int x, int y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.tower), 1,400,1,1, 2000);
    }

    public void shot(List<Shot> shots, Context context, Enemy enemy) {
        shots.add(new Missile((int)x, (int) y, context, enemy));
    }


}