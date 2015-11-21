package com.example.towerdefense;

import android.graphics.Bitmap;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Ennemy extends Elements{
    private Speed speed;
    private int hp;
    private int armor;
    private int mr;
    private int test ;

    public Ennemy(int x, int y, Bitmap bitmap, int hp, int armor, int mr){
        super(x,y,bitmap);
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
            this.setX((int) (this.getX()+ (10*speed.getXv() * speed.getxDirection())));
            this.setY((int) (this.getY() + (10*speed.getYv() * speed.getyDirection())));
    }


}
