package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.List;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Archer_tower extends Towers{

    public Archer_tower(int x, int y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.archer_tower), 700, 2000);
        this.width = 2*this.bitmap.getWidth();
        this.height = 2*this.bitmap.getHeight();
        this.penetration = 10;
        this.damage_min = 13;
        this.damage_max = 33;
    }

    public void shot(List<Shot> shots, Context context, Enemy enemy) {
        shots.add(new Arrow((int)x, (int) y, context, enemy, getDamage(1, enemy)));
    }


}