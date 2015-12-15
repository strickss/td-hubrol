package com.example.towerdefense;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Buttons_continue extends Buttons{

    public Buttons_continue(int x, int y, Context context) {
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.main_menu_button));
    }

    public void getEvent(List<Towers> towers, Context context, Player player) {
        //Intent intentGame = new Intent(MainActivity.class, MainActivity.class);
        //startActivityForResult(intentGame,GAME_ON);
    }
}
