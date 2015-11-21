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
public class Buttons_tower_creation extends  Buttons{

    public Buttons_tower_creation(int x, int y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(),R.drawable.play_now_green));
    }

    public void getEvent(MotionEvent event, List<Towers> towers, Context context) {
        towers.add(new Tower((int) event.getX(), (int) event.getY() + 200,context));
    }


}
