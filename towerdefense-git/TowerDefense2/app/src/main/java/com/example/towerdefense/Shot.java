package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Brieuc on 22-11-15.
 */
public class Shot extends Elements {
    private final int targetX;
    private final int targetY;
    private final int dt;
    private final int x0;
    private final int y0;

    public Shot(int x, int y, Bitmap bitmap, int targetX, int targetY) {
        super(x, y, bitmap);
        this.targetX = targetX;
        this.targetY = targetY;
        this.dt = 200;
        this.x0 = x;
        this.y0 = y;
    }

    public void update() {
        this.setX((int) (this.getX()+ (targetX - x0)/dt));
        this.setY((int) (this.getY() + (targetY - y0)/dt));

    }
}
