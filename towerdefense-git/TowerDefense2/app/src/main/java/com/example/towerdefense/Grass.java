package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Hugo on 5/12/2015.
 */
public class Grass extends Elements {
    public Grass(double x, double y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.grass));
    }
}
