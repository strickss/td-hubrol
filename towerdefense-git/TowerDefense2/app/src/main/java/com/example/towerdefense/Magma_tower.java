package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.List;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Magma_tower extends Towers{

    public Magma_tower(int x, int y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.magma_tower),400, 2000);
        this.width = 2*this.bitmap.getWidth();
        this.height = 2*this.bitmap.getHeight();
        this.penetration = 20;
        this.damage_min = 25;
        this.damage_max = 75;
    }

    public void shot(List<Shot> shots, Context context, Enemy enemy) {
        shots.add(new Fireball((int)x, (int) y, context, enemy, getDamage(1, enemy)));
    }

    public void upgrade() {
        this.damage_min = (int) Math.ceil(this.damage_min*1.15);
        this.damage_max = (int) Math.ceil(this.damage_max*1.15);
        this.range = (int) Math.ceil(this.range*1.10);
        this.recharge = (int) Math.floor(this.recharge*0.80);
        this.level ++;
    }

}