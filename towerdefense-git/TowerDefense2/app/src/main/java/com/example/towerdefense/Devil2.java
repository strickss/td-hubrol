package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Brieuc on 01-12-15.
 */
public class Devil2 extends Enemy{

    public Devil2(Context context, ArrayList<Elements> path) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.devil2_sprite), 5, 2, 2, path);
        this.value = 4;
        this.cost = 40;
        this.frameNr = 4; //framecount
        this.spriteWidth = bitmap.getWidth() / 4;
        this.spriteHeight = bitmap.getHeight() / 4;
        this.sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.width = 150;
        this.height = 150;
        this.framePeriod = 1000 / 5; // 1000/fps
        this.speed = 2;
    }
}
