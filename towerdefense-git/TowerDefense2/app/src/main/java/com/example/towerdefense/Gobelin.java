package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Gobelin extends Enemy {

    public Gobelin(double x, double y, Context context, int hp, int armor, int mr){
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.goblin), hp, armor, mr);
    }
}
