package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hugo on 24/11/2015.
 */
public class Path extends Elements{
    public Path(double x, double y, Context context){
        super(x, y, BitmapFactory.decodeResource(context.getResources(), R.drawable.path));
        this.width = 200;
        this.height = 200;
    }
}
