package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Brieuc on 10-11-15.
 */
public class Enemy extends Elements{

    private static final String TAG = Enemy.class.getSimpleName();

    protected Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
    protected int frameNr;		// number of frames in animation
    private int currentFrame;	// the current frame
    protected long frameTicker;	// the time of the last frame update
    protected int framePeriod;	// milliseconds between each frame (1000/fps)

    protected int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
    protected int spriteHeight;	// the height of the sprite

    protected int width;	// the width of the enemy
    protected int height;	// the height of the enemy

    private ArrayList<Elements> path;
    protected int speed;
    private int hp;
    private int armor;
    private int mr;
    private int dy;
    private int dx;
    private int increment;
    protected int cost;
    protected int value;

    public Enemy(Bitmap bitmap, int hp, int armor, int mr, ArrayList<Elements> path){
        super(path.get(0).getX(),path.get(0).getY(), bitmap);
        this.path = path;
        this.hp = hp;
        this.speed = 1;
        this.armor = armor;
        this.mr = mr;
        this.dx = 0;
        this.dy = 0;
        this.increment = 0;
        this.path = path;
        this.value = 1;
        this.cost = 10;
        this.value=0;
        this.currentFrame = 0;
        this.frameNr = 4; //framecount
        this.spriteWidth = bitmap.getWidth() / 4;
        this.spriteHeight = bitmap.getHeight() / 4;
        this.sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.width = 200;
        this.height = 200;
        this.framePeriod = 1000 / 5; // 1000/fps
        this.frameTicker = 0l;
        this.dx = 1;
        this.dy = 0;
    }

    protected void damaged(int damage){
        this.hp = this.hp - damage;
    }

   // private void slowed(int slow){
   //     this.speed = this.getSpeed()*slow;
   // }

    protected void enemyUpdate(Map map, long gameTime) {
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
            if (Math.abs(this.getX() - this.path.get(this.increment).getX()) < speed && Math.abs(this.getY() - this.path.get(this.increment).getY()) < speed) {
                this.setX(this.path.get(this.increment).getX());
                this.setY(this.path.get(this.increment).getY());
                this.increment++;
                this.setDx(0); this.setDy(0);
                Log.d(TAG, "OK !" + "x :" + this.getX() + ", y :" + this.getY());
            }
        } catch (Exception e) {
            this.setX(map.getEndZoneX());
            this.setY(map.getEndZoneY());
        }
        this.update(gameTime);
    }

    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            // increment the frame
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }
        }
        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;
        if (this.getDx() == 0){
            if (this.getDy() > 0){
                this.sourceRect.top = 0;
                this.sourceRect.bottom = spriteHeight;
            } else {
                this.sourceRect.top = 3 * spriteHeight;
                this.sourceRect.bottom = 4 * spriteHeight;
            }
        } else if (this.getDx() > 0){
            this.sourceRect.top = 2 * spriteHeight;
            this.sourceRect.bottom = 3 * spriteHeight;
        } else {
            this.sourceRect.top = spriteHeight;
            this.sourceRect.bottom = 2 * spriteHeight;
        }
        this.setX((int) (this.getX() + this.getDx()*speed));
        this.setY((int) (this.getY() + this.getDy() * speed));
    }

    public void draw(Canvas canvas) {
        // where to draw the sprite
        Rect destRect = new Rect((int) this.getX() - this.width/2, (int) this.getY() - height/2, (int) this.getX() + width/2, (int) this.getY() + height/2);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
        //drawSpriteSelector(canvas);
    }

    private void drawSpriteSelector(Canvas canvas) {
        canvas.drawBitmap(bitmap, 800, 400, null);
        Paint paint = new Paint();
        paint.setARGB(50, 0, 255, 0);
        canvas.drawRect(800 + (currentFrame * sourceRect.width()), 400 + this.sourceRect.top, 800 + (currentFrame * sourceRect.width()) + sourceRect.width(), 400 + this.sourceRect.bottom, paint);
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

    public int getSpeed() {
        return speed;
    }

}
