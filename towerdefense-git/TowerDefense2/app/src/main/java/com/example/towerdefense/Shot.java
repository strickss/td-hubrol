package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
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
    protected int bitmapAngle;
    private double vx;
    private double vy;

    //shot speed : pixel per update
    public Shot(double x, double y, Bitmap bitmap, Enemy enemy, double shot_speed) {
        super(x, y, bitmap);
        double a = shot_speed*shot_speed - enemy.getDx()*enemy.getSpeed()* enemy.getDx()*enemy.getSpeed() - enemy.getDy()*enemy.getSpeed()* enemy.getDy()*enemy.getSpeed();
        double b = 2*(enemy.getDx()*enemy.getSpeed()*(x- enemy.getX()) + enemy.getDy()*enemy.getSpeed()*(y- enemy.getY()));
        double c = -((x - enemy.getX())*(x - enemy.getX()) + (y - enemy.getY())*(y - enemy.getY()));
        double delta = b*b - 4*a*c;
        double dt = (-b + Math.sqrt(delta))/2/a; // Number of updates for the missile to reach the monster
        this.targetX = enemy.getX() + enemy.getDx()*enemy.getSpeed()*dt;
        this.targetY = enemy.getY() + enemy.getDy()*enemy.getSpeed()*dt;
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

        Matrix matrix = new Matrix();
        int rotation = (int) (Math.atan((targetY - y) / ( targetX - x))*180/Math.PI) ;
        if (targetX - x < 0) { //Monster at the left
            matrix.setRotate(rotation + bitmapAngle, this.getBitmap().getWidth() / 2, this.getBitmap().getHeight() / 2);
        } else { //Monster at the right
            matrix.setRotate(rotation + bitmapAngle + 180, this.getBitmap().getWidth() / 2, this.getBitmap().getHeight() / 2);
            matrix.preScale(1, -1); //Flip the bitmap
        }

        this.setBitmap(Bitmap.createBitmap(bitmap, 0, 0, this.getBitmap().getWidth(), this.getBitmap().getHeight(), matrix, true));
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
        //Paint paint1 = new Paint();
        //paint1.setARGB(255, 0, 50, 0);
        //canvas.drawCircle((int)this.x,(int) this.y, 10, paint1);
        //Paint paint2 = new Paint();
        //paint2.setARGB(50, 0, 255, 0);
        //canvas.drawCircle((int)this.targetX,(int) this.targetY, 10, paint2);
    }

}
