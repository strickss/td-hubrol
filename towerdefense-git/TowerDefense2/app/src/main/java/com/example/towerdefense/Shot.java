package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Brieuc on 22-11-15.
 */
public class Shot extends Elements {

    private static final String TAG = Shot.class.getSimpleName();

    private final double targetX;
    private final double targetY;
    private final int dirX;
    private final int dirY;
    private double vx;
    private double vy;

    //shot speed : pixel per update
    public Shot(double x, double y, Bitmap bitmap, Enemy enemy, double shot_speed) {
        super(x, y, bitmap);
        double a = shot_speed*shot_speed - enemy.getDx()* enemy.getDx() - enemy.getDy()* enemy.getDy();
        double b = 2*(enemy.getDx()*(x- enemy.getX()) + enemy.getDy()*(y- enemy.getDy()));
        double c = -((x - enemy.getX())*(x - enemy.getX()) + (y - enemy.getY())*(y - enemy.getY()));
        double delta = b*b - 4*a*c;
        double dt = (-b + Math.sqrt(delta))/2/a; // Number of updates for the missile to reach the monster
        this.targetX = enemy.getX() + enemy.getDx()*dt;
        this.targetY = enemy.getY() + enemy.getDy()*dt;
        if (targetX > x){
            this.dirX = 1;
        } else if (targetX < x){
            this.dirX =-1;
        } else {
            this.dirX = 0;
        }
        if (targetY > y){
            this.dirY = 1;
        } else if (targetY < y){
            this.dirY =-1;
        } else {
            this.dirY = 0;
        }
        this.vx = ((this.targetX - x)/(Math.sqrt((this.targetX - x)*(this.targetX - x) + (this.targetY - y)*(this.targetY - y)))*shot_speed);
        this.vy = ((this.targetY - y)/(Math.sqrt((this.targetX - x)*(this.targetX - x) + (this.targetY - y)*(this.targetY - y)))*shot_speed);
    }

    public void update() {
        this.setX((this.getX() + this.vx));
        this.setY((this.getY() + this.vy));
    }

    public boolean hasHit(){
        if (this.dirX >= 0){
            if (this.dirY >= 0){
                if ((this.getX() >= this.targetX) && (this.getY()>=this.targetY)){
                    return true;
                }else{
                    return false;
                }
            } else {
                if ((this.getX() >= this.targetX) && (this.getY()<this.targetY)){
                    return true;
                }else{
                    return false;
                }
            }
        } else{
            if (this.dirY >=0){
                if ((this.getX() < this.targetX) && (this.getY()>=this.targetY)){
                    return true;
                }else{
                    return false;
                }
            } else {
                if ((this.getX() < this.targetX) && (this.getY()<this.targetY)){
                    return true;
                }else{
                    return false;
                }
            }
        }

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(this.bitmap,(int) this.x- (this.bitmap.getWidth()/2), (int)this.y -(this.bitmap.getHeight()/2), null);
        Paint paint1 = new Paint();
        paint1.setARGB(255, 0, 50, 0);
        canvas.drawCircle((int)this.x,(int) this.y, 10, paint1);
        Paint paint2 = new Paint();
        paint2.setARGB(50, 0, 255, 0);
        canvas.drawCircle((int)this.targetX,(int) this.targetY, 10, paint2);
    }

}
