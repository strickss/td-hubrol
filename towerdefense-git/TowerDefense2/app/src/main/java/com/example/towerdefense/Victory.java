package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Victory extends Buttons{

    public Victory(int x, int y, Context context) {
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.victory));
        this.width = 3*bitmap.getWidth();
        this.height = 3*bitmap.getHeight();
    }

}
