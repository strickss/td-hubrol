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
        this.penetration = 30;
        this.damage_min = 13;
        this.damage_max = 33;
    }

    public void shot(List<Shot> shots, Context context, Enemy enemy) {
        shots.add(new Arrow((int)x, (int) y, context, enemy, getDamage(1, enemy)));
    }

    public void upgrade() {
        this.damage_min = (int) Math.ceil(this.damage_min*1.19);
        this.damage_max = (int) Math.ceil(this.damage_max*1.21);
        this.range = (int) Math.ceil(this.range*1.10);
        this.recharge = (int) Math.floor(this.recharge*0.9);
        this.level ++;
    }


}