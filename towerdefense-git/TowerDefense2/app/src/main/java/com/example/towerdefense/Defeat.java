package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Defeat extends Buttons{

    public Defeat(int x, int y, Context context) {
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.defeat2));
        this.width = 4*bitmap.getWidth();
        this.height = 4*bitmap.getHeight();
    }

}
