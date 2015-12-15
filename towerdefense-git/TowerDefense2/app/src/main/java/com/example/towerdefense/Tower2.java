package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.List;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Tower2 extends Towers{

    public Tower2(int x, int y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.tower2),600, 1000);
    }

    public void shot(List<Shot> shots, Context context, Enemy enemy) {
        shots.add(new Fireball((int) x, (int) y, context, enemy, getDamage(1, enemy)));
    }


}