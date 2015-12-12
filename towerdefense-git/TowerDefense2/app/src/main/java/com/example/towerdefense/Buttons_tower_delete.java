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
public class Buttons_tower_delete extends  Buttons{
    private int towerIndex;

    public Buttons_tower_delete(int x, int y, Context context, int towerIndex) {
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.delete_button));
        this.towerIndex = towerIndex;
    }

    public void getEvent(List<Towers> towers, Context context, Player player) {
        towers.remove(towerIndex);
    }
}
