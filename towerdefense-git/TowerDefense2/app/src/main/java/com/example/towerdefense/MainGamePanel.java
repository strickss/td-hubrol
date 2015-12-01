package com.example.towerdefense;

/**
 * Created by Brieuc on 11-11-15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();
    private Player player1;
    private MainThread thread;
    //final ImageButton imageButton;
    private List<Towers> towers;
    private List<Buttons> buttons;
    private List<Shot> shots;
    private List<Enemy> enemies;
    private Map map;
    private String avgFps; //the fps to be displayed
    private Gryphon gryphon;
    private float x1,y1;

    private long a =System.currentTimeMillis();
    private float canvasX =0;
    private float canvasY=0;

    public MainGamePanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this); //  sets the current class (MainGamePanel) as the handler for the events happening on the actual surface
        map = new Map(context, 0);

        // create tower and load bitmap
        this.player1 = new Player(50,10,20);

        towers = new ArrayList<Towers>();
        buttons = new ArrayList<Buttons>();
        shots = new ArrayList<Shot>();
        enemies = new ArrayList<Enemy>();

        enemies.add(new Gryphon(context, map.getLogicPath()));
        //goblin = new Gobelin(150,0, context, 1, 1, 1);


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
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (thread.getCanvasMoved() == false) {
                if (buttons.size() != 0) {
                    for (int i = 0; i < buttons.size(); i++) {
                        //if ((event.getX() < buttons.get(i).getX() + buttons.get(i).getBitmap().getWidth()) && (event.getX() > buttons.get(i).getX() - buttons.get(i).getBitmap().getWidth())) {
                        //if ((event.getY() < buttons.get(i).getY() + buttons.get(i).getBitmap().getHeight()) && (event.getY() > buttons.get(i).getY() - buttons.get(i).getBitmap().getHeight())) {
                        if ((event.getX() < buttons.get(i).getX() + buttons.get(i).getBitmap().getWidth() / 2) && (event.getX() > buttons.get(i).getX() - buttons.get(i).getBitmap().getWidth() / 2)) {
                            if ((event.getY() < buttons.get(i).getY() + buttons.get(i).getBitmap().getHeight() / 2) && (event.getY() > buttons.get(i).getY() - buttons.get(i).getBitmap().getHeight() / 2)) {
                                buttons.get(i).getEvent(event, towers, getContext());
                            }
                        }
                    }
                    buttons = new ArrayList<Buttons>();
                } else {
                    // check if in the lower part of the screen we exit
                    if (event.getY() > getHeight() - 50) {
                        thread.setRunning(false);
                        ((Activity) getContext()).finish();
                    } else {
                        Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
                    }
                    // delegating event handling to the towers
                    towerOnClickEvent(event);
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.abs(event.getX()-x1)>map.getBlockSizeX()/2 && Math.abs(event.getY()-y1)>map.getBlockSizeY()/2) {
                thread.setCanvasMoved(true);
                thread.getCanvas().translate(event.getX() - x1, event.getY() - y1);
                canvasX = canvasX + (event.getX() - x1);
                canvasY = canvasY + (event.getY() - y1);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            thread.setCanvasMoved(false);
            x1 = event.getX();
            y1 = event.getY();
        }
        return true;
    }

    private void towerOnClickEvent(MotionEvent event) {
        boolean create = true;
        for (int i = 0; i < towers.size(); i++) {
            towers.get(i).handleActionDown((int) event.getX(), (int) event.getY());
            if (towers.get(i).isTouched()) {
                create = false;
                towerUpgrade(event, i);
                //towers.get(i).info();
            }
            if (!create) {
                //break;
            }
        }
        if (create) {
            towerCreation(event);
        }
    }

    private void towerUpgrade(MotionEvent event, int towerIndex) {
        buttons.add(new Buttons_tower_upgrade((int) towers.get(towerIndex).getX(),(int) towers.get(towerIndex).getY() - 200, getContext(), towerIndex));
        buttons.add(new Buttons_tower_stop((int)towers.get(towerIndex).getX(),(int) towers.get(towerIndex).getY(), getContext()));
        buttons.add(new Buttons_tower_delete((int)towers.get(towerIndex).getX(), (int)towers.get(towerIndex).getY() + 200, getContext(), towerIndex));
    }

    private void towerCreation(final MotionEvent event) {
        buttons.add(new Buttons_tower_creation((int) event.getX(), (int) event.getY() - 200, getContext()));
        buttons.add(new Buttons_tower_stop((int) event.getX(), (int) event.getY(), getContext()));
    }

    protected void render(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        map.draw(canvas);
        //goblin.draw(canvas);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(canvas);
        }

        for (int i = 0; i < towers.size(); i++) {
            towers.get(i).draw(canvas);
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(canvas);
        }
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.goblin),10,10,null);
        displayFps(canvas, avgFps);

        if (buttons.size() != 0) {
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getType() == 2){
                    Paint paint = new Paint();
                    paint.setARGB(50, 0, 255, 0);
                    canvas.drawCircle((int)towers.get(buttons.get(i).getTowerIndex()).getX(), (int)towers.get(buttons.get(i).getTowerIndex()).getY(), towers.get(buttons.get(i).getTowerIndex()).getRange(), paint);
                }

            }
        }

        for (int i = 0; i < shots.size(); i++) {
            shots.get(i).draw(canvas);
        }

    }


    public void update() {// check collision with right wall if heading right
        if (System.currentTimeMillis() - a > 10000){
            player1.getFunding();
            a = System.currentTimeMillis();
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).enemyUpdate(map, System.currentTimeMillis());
        }
        missileUpdate();
        missileCreation();
        enemiesUpdate();
        }

    private void enemiesUpdate() {
        for (int j = 0; j < enemies.size(); j++) {
            if (enemies.get(j).getHp() ==0){
                player1.increaseGold(enemies.get(j).getValue());
                enemies.remove(j);
            }else if(enemies.get(j).getX() == map.getEndZoneX() && enemies.get(j).getY() == map.getEndZoneY()){
                enemies.remove(j);
                player1.looseLife();
            }
        }
    }

    private void missileCreation() {
        for (int i = 0; i < towers.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                double dx =towers.get(i).getX()- enemies.get(j).getX();
                double dy = towers.get(i).getY()- enemies.get(j).getY();
                double dist = Math.sqrt(dx*dx + dy*dy);
                //Log.d(TAG, "dist:" + dist);
                if (towers.get(i).getRange() >= dist){
                    if (towers.get(i).canFire()) {
                        towers.get(i).fire();
                        //Log.d(TAG, "1 missile created");
                        shots.add(new Missile((int)towers.get(i).getX(), (int)towers.get(i).getY(), getContext(), enemies.get(j)));
                    }
                }
            }
        }
    }


    private void missileUpdate() {
        for (int i = 0; i < shots.size(); i++) {
            shots.get(i).update();
            if (shots.get(i).hasHit()){
                for (int j = 0; j < enemies.size(); j++) {
                    if ((Math.abs(enemies.get(j).getX() - shots.get(i).getX()) < 10) && (Math.abs(enemies.get(j).getY() - shots.get(i).getY()) < 10)){
                        enemies.get(j).damaged(1);
                    }
                }
                shots.remove(i);
            }
        }
    }

    public void setAvgFps(String avgFps){
        this.avgFps = avgFps;
    }

    private void displayFps(Canvas canvas, String fps) {
        if (canvas != null && fps != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            canvas.drawText(fps, this.getWidth() - 100, 20, paint);
        }
    }

    public void CreateMonster(int i) {
        if (i ==1) {
            if (player1.getGold() >= 10){
                enemies.add(new Gobelin(getContext(), 1, 1, 1, map.getLogicPath()));
                player1.cost(enemies.get(enemies.size()-1).getCost());
                player1.increaseIncome(enemies.get(enemies.size() - 1).getValue());
            } else {
                Toast toast = Toast.makeText(getContext(), "Not enough gold !", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        if (i ==2) {
            if (player1.getGold() >= 20){
                enemies.add(new Gobelin(getContext(), 2, 1, 1, map.getLogicPath()));
                player1.cost(enemies.get(enemies.size() - 1).getCost());
                player1.increaseIncome(enemies.get(enemies.size()-1).getValue());
            } else {
                Toast toast = Toast.makeText(getContext(), "Not enough gold !", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public Player getPlayer() {
        return this.player1;
    }

    public float getCanvasX() {
        return canvasX;
    }

    public float getCanvasY() {
        return canvasY;
    }
}
