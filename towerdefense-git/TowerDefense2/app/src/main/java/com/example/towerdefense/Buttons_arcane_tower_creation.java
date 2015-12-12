package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Buttons_arcane_tower_creation extends  Buttons{

    public Buttons_arcane_tower_creation(int x, int y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(),R.drawable.arcane_tower_creation));
        this.width = 2*bitmap.getWidth();
        this.height = 2*bitmap.getHeight();
        this.cost = 120;
    }

    public void getEvent(List<Towers> towers, Context context, Player player) {
        if (player.getGold() > cost) {
            towers.add(new Arcane_tower((int) x, (int) y + 200, context));
            player.cost(cost);
        } else {
            Toast toast = Toast.makeText(context, "Not enough gold !", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
