package com.example.towerdefense;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Brieuc on 12-12-15.
 */
public class LifeBar extends Elements {


    private final int life_max;
    private int hp;
    private Paint paint_life = new Paint();
    private Paint paint_no_life = new Paint();
    private final int width_bar = 80;
    private final int height_bar = 10;

    public LifeBar(Enemy enemy, Context context){
        super(enemy.getX(), enemy.getY() - 150, BitmapFactory.decodeResource(context.getResources(), R.drawable.missile));
        this.hp = enemy.getHp();
        this.life_max = hp;
        paint_life.setARGB(255, 0, 255, 0);
        paint_no_life.setARGB(255, 255, 0, 0);
    }


    public void update(Enemy enemy) {
        this.x = enemy.getX();
        this.y = enemy.getY() - 80;
    }

    public void draw(Canvas canvas){
        float stopX = (float) this.x - width_bar/2 + (width_bar*((float) this.hp)/((float)this.life_max));
        canvas.drawRect((float) this.x - width_bar/2, (float) this.y - height_bar/2, stopX, (float) this.y + height_bar/2, paint_life);
        canvas.drawRect(stopX, (float) this.y - height_bar/2, (float) this.x + width_bar/2, (float) this.y + height_bar/2, paint_no_life);
    }

    public void damaged(int i) {
        this.hp = this.hp - i;
    }
}
