package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;

import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Buttons extends Elements{

    protected int cost;

    public Buttons(int x, int y, Bitmap bitmap) {
        super(x, y, bitmap);
    }


    public void getEvent(List<Towers> towers, Context context, Player player) {
    }

    public int getType() {
        return 1;
    }

    public int getTowerIndex(){
        return 0;
    }
}
