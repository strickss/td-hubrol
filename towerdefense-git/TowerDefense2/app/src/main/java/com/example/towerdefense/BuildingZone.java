package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by Hugo on 5/12/2015.
 */
public class BuildingZone extends Elements {
    public BuildingZone(float x, float y, Context context, int blockSizeX, int blockSizeY) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.sign));
        this.width = blockSizeX;
        this.height = blockSizeY;
    }
}
