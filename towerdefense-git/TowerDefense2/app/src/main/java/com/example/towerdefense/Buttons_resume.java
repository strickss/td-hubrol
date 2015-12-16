package com.example.towerdefense;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Buttons_resume extends Buttons{

    private final Activity activity;
    private final long creationTime;

    public Buttons_resume(int x, int y, Context context, Activity activity) {
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.resume_button));
        this.activity = activity;
        this.creationTime = System.currentTimeMillis();
    }

    public void getEvent(List<Towers> towers, Context context, Player player) {
        ((MainActivity)activity).getGamePanel().setNewButtons(true);
        ((MainActivity)activity).getGamePanel().delayTime(System.currentTimeMillis() - creationTime);
        ((MainActivity)activity).resumeGame();
    }
}
