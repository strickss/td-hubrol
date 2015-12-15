package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Gobelin extends Enemy {

    public Gobelin(Context context,ArrayList path){
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.goblin), 2, 1, 1, path);
        this.value=1;
        this.cost = 10;
        this.frameNr = 3; //framecount
        this.spriteWidth = bitmap.getWidth() / 3;
        this.spriteHeight = bitmap.getHeight() / 4;
        this.sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.width = 80;
        this.height = 100;
        this.framePeriod = 1000 / 7; // 1000/fps
        this.speed = 2;
        this.type = 1;
    }
}
