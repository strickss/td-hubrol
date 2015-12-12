package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Buttons_tower_upgrade extends Buttons {

    private final int towerIndex;

    public Buttons_tower_upgrade(int x, int y, Context context, int towerIndex) {
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.boost_button));
        this.towerIndex = towerIndex;
    }

    public void getEvent(List<Towers> towers, Context context, Player player) {
        //towers.add(new Tower2((int) towers.get(towerIndex).getX(), (int) towers.get(towerIndex).getY(), context));
        //towers.remove(towerIndex);
        this.cost = 100*towers.get(towerIndex).getLevel()*towers.get(towerIndex).getLevel();
        if (player.getGold() >= cost){
            towers.get(towerIndex).upgrade();
            player.cost(cost);
        } else {
            Toast toast = Toast.makeText(context, "Not enough gold !", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public int getType() {
        return 2;
    }

    public int getTowerIndex(){
        return towerIndex;
    }

}
