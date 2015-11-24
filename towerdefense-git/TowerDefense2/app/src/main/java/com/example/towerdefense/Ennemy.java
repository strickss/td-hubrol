package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Ennemy extends Elements{
    private Speed speed;
    private int hp;
    private int armor;
    private int mr;
    private int test ;

    public Ennemy(int x, int y, Context context, int hp, int armor, int mr){
        super(x,y, BitmapFactory.decodeResource(context.getResources(), R.drawable.goblin));
        this.speed = new Speed();
        this.hp = hp;
        this.armor = armor;
        this.mr = mr;
    }

    private void damaged(int damage){
        this.hp = this.hp - damage;
    }

   // private void slowed(int slow){
   //     this.speed = this.getSpeed()*slow;
   // }

    public Speed getSpeed() {
        return speed;
   }

    private void move(int dx, int dy){
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
    }

    public void motion(int dirX, int dirY){

    }

    public void update() {
            this.setX((int) (this.getX()+ (speed.getXv() * speed.getxDirection())));
            this.setY((int) (this.getY() + (speed.getYv() * speed.getyDirection())));
    }


}
