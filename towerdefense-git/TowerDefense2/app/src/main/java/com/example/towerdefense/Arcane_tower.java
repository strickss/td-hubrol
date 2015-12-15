package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.List;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Arcane_tower extends Towers{

    public Arcane_tower(int x, int y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.arcane_tower), 400, 200);
        this.width = 2*this.bitmap.getWidth();
        this.height = 2*this.bitmap.getHeight();
        this.penetration = 10;
        this.damage_min = 3;
        this.damage_max = 9;
    }

    public void shot(List<Shot> shots, Context context, Enemy enemy) {
        shots.add(new Iceball((int)x, (int) y, context, enemy, getDamage(2, enemy)));
    }


}