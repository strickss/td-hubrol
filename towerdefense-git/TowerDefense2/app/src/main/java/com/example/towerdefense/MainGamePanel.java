package com.example.towerdefense;

/**
 * Created by Brieuc on 11-11-15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.widget.ImageButton;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();
    private MainThread thread;
    private Ennemy goblin;
    //final ImageButton imageButton;
    private List<Tower> towers;
    private List<Ennemy> buttons;
    private Map map;
    private int increment;

    public MainGamePanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this); //  sets the current class (MainGamePanel) as the handler for the events happening on the actual surface

        map = new Map(1);
        increment = 0;

        // create tower and load bitmap
        goblin = new Ennemy(10, 10, BitmapFactory.decodeResource(getResources(), R.drawable.goblin), 1, 1, 1);

        towers = new ArrayList<Tower>();
        buttons = new ArrayList<Ennemy>();

        //create the game loop thread
        thread = new MainThread(getHolder(), this);

        //imageButton = (ImageButton) findViewById(R.id.button_1);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true; // Ensure that the thread shuts down cleanly
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (buttons.size() != 0) {
                //for (int i = 0; i < towers.size(); i++) {
                int i = 0;
                if ((event.getX() < buttons.get(i).getX() + buttons.get(i).getBitmap().getWidth()) && (event.getX() > buttons.get(i).getX() - buttons.get(i).getBitmap().getWidth())) {
                    if ((event.getY() < buttons.get(i).getY() + buttons.get(i).getBitmap().getHeight()) && (event.getY() > buttons.get(i).getY() - buttons.get(i).getBitmap().getHeight())) {
                        towers.add(new Tower((int) event.getX(), (int) event.getY() + 200, BitmapFactory.decodeResource(getResources(), R.drawable.tower), 1, 1, 1));
                    }
                }
                buttons.remove(i);
                //}
            } else {
                // check if in the lower part of the screen we exit
                if (event.getY() > getHeight() - 50) {
                    thread.setRunning(false);
                    ((Activity) getContext()).finish();
                } else {
                    Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
                }
                // delegating event handling to the towers
                boolean create = true;
                for (int i = 0; i < towers.size(); i++) {
                    towers.get(i).handleActionDown((int) event.getX(), (int) event.getY());
                    if (towers.get(i).isTouched()) {
                        create = false;
                        //towers.get(i).info();
                    }
                    if (!create) {
                        break;
                    }
                }
                if (create) {
                    towerCreation(event);
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // the gestures
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
        }
        return true;
    }

    private void towerCreation(final MotionEvent event) {
        buttons.add(new Ennemy((int) event.getX(), (int) event.getY() - 200, BitmapFactory.decodeResource(getResources(), R.drawable.play_now_green), 1, 1, 1));
    }

    protected void render(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        goblin.draw(canvas);

        for (int i = 0; i < towers.size(); i++) {
            towers.get(i).draw(canvas);
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(canvas);
        }
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.goblin),10,10,null); //Pla soldier on (10,10) ((0,0) is the upper right cornn
    }

    public void update() {// check collision with right wall if heading right
        if (goblin.getX() < map.getPath().get(increment).get(0)) {
            goblin.getSpeed().setxDirection(1);
        } else if (goblin.getX() > map.getPath().get(increment).get(0)) {
            goblin.getSpeed().setxDirection(-1);
        } else {
            goblin.getSpeed().setxDirection(0);
        }
        // check collision with left wall if heading left
        if (goblin.getY() < map.getPath().get(increment).get(1)) {
            goblin.getSpeed().setyDirection(1);
        } else if (goblin.getY() > map.getPath().get(increment).get(1)) {
            goblin.getSpeed().setyDirection(-1);
        } else {
            goblin.getSpeed().setyDirection(0);
        }
        // Update the lone droid
        goblin.update();
        Log.d(TAG, "xi" + goblin.getX() + "yi" + goblin.getY());
        Log.d(TAG, "xf" + map.getPath().get(increment).get(0) + "yf" + map.getPath().get(increment).get(1));
        if (goblin.getX() == map.getPath().get(increment).get(0) && goblin.getY() == map.getPath().get(increment).get(1)) {
            increment++;
            Log.d(TAG, "i" + increment);
        }
    }
}
