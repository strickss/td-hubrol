package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Brieuc on 21-11-15.
 */
public class Gobelin extends Enemy {

    public Gobelin(Context context, int hp, int armor, int mr, ArrayList path){
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.goblin), hp, armor, mr, path);
        this.value=10;
        this.frameNr = 1; //framecount
        this.spriteWidth = bitmap.getWidth() / 1;
        this.spriteHeight = bitmap.getHeight() / 1;
        this.sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.width = 100;
        this.height = 150;
        this.framePeriod = 1000 / 5; // 1000/fps
        this.speed = 2;
    }

    public void update(long gameTime){
        this.setX((int) (this.getX() + this.getDx()*this.getSpeed()));
        this.setY((int) (this.getY() + this.getDy()*this.getSpeed()));
    }
}
