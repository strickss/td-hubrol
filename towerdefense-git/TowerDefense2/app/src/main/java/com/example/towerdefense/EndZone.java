package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Hugo on 26/11/2015.
 */
public class EndZone extends Elements {
    public EndZone(double x, double y, Context context) {
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.castle));
    }
}
