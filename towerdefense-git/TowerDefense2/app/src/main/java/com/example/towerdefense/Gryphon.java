package com.example.towerdefense;

/**
 * Created by Brieuc on 21-11-15.
 */

    import android.content.Context;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.graphics.Paint;
    import android.graphics.Rect;

    import java.util.ArrayList;

public class Gryphon extends Enemy {

    private static final String TAG = Gryphon.class.getSimpleName();

    public Gryphon(Context context, ArrayList<Elements> path) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.gryphon_sprite), 5, 2, 2, path);
        this.value = 4;
        this.cost = 40;
        this.frameNr = 4; //framecount
        this.spriteWidth = bitmap.getWidth() / 4;
        this.spriteHeight = bitmap.getHeight() / 4;
        this.sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.width = 200;
        this.height = 200;
        this.framePeriod = 1000 / 5; // 1000/fps
        this.speed = 2;
    }
}
