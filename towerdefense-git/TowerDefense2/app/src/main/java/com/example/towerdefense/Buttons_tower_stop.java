package com.example.towerdefense;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;

import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Buttons_tower_stop extends Buttons{

    public Buttons_tower_stop(int x, int y, Context context) {
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.stop_button));
    }

    public void getEvent(List<Towers> towers, Context context) {
    }
}
