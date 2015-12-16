package com.example.towerdefense;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Buttons_leave extends Buttons{

    private final Activity activity;

    public Buttons_leave(int x, int y, Context context, Activity activity) {
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.leave_button));
        this.activity = activity;
    }

    public void getEvent(List<Towers> towers, Context context, Player player) {
        ((MainActivity) activity).restart();
    }
}
