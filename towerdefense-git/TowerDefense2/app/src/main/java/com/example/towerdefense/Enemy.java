package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Enemy extends Elements{
    private ArrayList<Elements> path;
    private Speed speed;
    private int hp;
    private int armor;
    private int mr;
    private int test ;
    private int dy;
    private int dx;
    private int increment;
<<<<<<< HEAD
    private int cost;
=======
>>>>>>> origin/master
    protected int value;

    public Enemy(double x, double y, Bitmap bitmap, int hp, int armor, int mr, ArrayList<Elements> path){
        super(x,y, bitmap);
        this.speed = new Speed();
        this.hp = hp;
        this.armor = armor;
        this.mr = mr;
        this.dx = 0;
        this.dy = 0;
        this.increment = 0;
        this.path = path;
<<<<<<< HEAD
        this.value = 1;
        this.cost = 10;
=======
        this.value=0;
>>>>>>> origin/master
    }

    protected void damaged(int damage){
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

    protected void gobUpdate(Map map) {
        try {
            if (this.getX() < this.path.get(this.increment).getX()) {
                this.setDx(1);
            } else if (this.getX() > this.path.get(this.increment).getX()) {
                this.setDx(-1);
            } else {
                this.setDx(0);
            }
            // check collision with left wall if heading left
            if (this.getY() < this.path.get(this.increment).getY()) {
                this.setDy(1);
            } else if (this.getY() > this.path.get(this.increment).getY()) {
                this.setDy(-1);
            } else {
                this.setDy(0);
            }
            //Log.d(TAG, "xi" + goblin.getX() + "yi" + goblin.getY());
            //Log.d(TAG, "xf" + map.getPath().get(increment) + "yf" + map.getPath().get(increment + 1));
            if (Math.abs(this.getX() - this.path.get(this.increment).getX()) < this.getSpeed().getXv() && Math.abs(this.getY() - this.path.get(this.increment).getY()) < this.getSpeed().getYv()) {
                this.setX(this.path.get(this.increment).getX());
                this.setY(this.path.get(this.increment).getY());
                this.increment++;
            }
        } catch (Exception e) {
            this.increment = 0;
            this.setX(map.getEndZoneX());
            this.setY(map.getEndZoneY());
        }
        this.update();
    }


    public void update() {
            this.setX((int) (this.getX() + this.getDx()));
            this.setY((int) (this.getY() + this.getDy()));
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getHp() {
        return hp;
    }

    public int getValue() {
        return this.value;
    }

    public int getCost(){
        return this.cost;
    }
}
