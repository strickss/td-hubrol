package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.List;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Mortar_tower extends Towers{

    public Mortar_tower(int x, int y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.mortar_tower),400,2000);
        this.width = 2*this.bitmap.getWidth();
        this.height = 2*this.bitmap.getHeight();
        this.penetration = 10;
        this.damage_min = 24;
        this.damage_max = 58;
    }

    public void shot(List<Shot> shots, Context context, Enemy enemy) {
        shots.add(new Missile((int)x, (int) y, context, enemy, getDamage(1, enemy)));
    }


}