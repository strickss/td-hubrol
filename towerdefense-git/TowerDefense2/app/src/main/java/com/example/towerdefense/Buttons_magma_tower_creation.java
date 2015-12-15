package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Buttons_magma_tower_creation extends  Buttons{

    public Buttons_magma_tower_creation(int x, int y, Context context) {
        super(x, y, BitmapFactory.decodeResource(context.getResources(),R.drawable.magma_tower_creation));
        this.width = 2*bitmap.getWidth();
        this.height = 2*bitmap.getHeight();
        this.cost = 80;
    }

    public void getEvent(List<Towers> towers, Context context, Player player) {
        if (player.getGold() >= cost) {
            towers.add(new Magma_tower((int) (x - 200), (int) y, context));
            player.cost(cost);
        } else {
            Toast toast = Toast.makeText(context, "Not enough gold !", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
