package com.example.towerdefense;

/**
 * Created by Brieuc on 11-11-15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();
    private final Paint paint_canvas;
    private int[] sound_pop;
    private int[] sound_death;
    private Player player;
    private Player opponent;
    private MainThread thread;
    private List<Towers> towers;
    private List<Buttons> buttons;
    private List<Shot> shots;
    private List<Enemy> enemies;
    private ArrayList<LifeBar> life_bars;
    private Map map;
    private MediaPlayer mediaPlayer;
    private String avgFps; //the fps to be displayed
    private float x1,y1;
    private SoundPool sp;
    ArrayList<Integer> buildingZone;

    private long a = System.currentTimeMillis() + 100;
    private float canvasX =0;
    private float canvasY=0;
    private Activity activity;
    private boolean pause = false;
    private boolean newButtons = true;
    private boolean finish = false;

    public MainGamePanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this); //  sets the current class (MainGamePanel) as the handler for the events happening on the actual surface
        map = new Map(context, 0);
        paint_canvas = new Paint();
        paint_canvas.setARGB(255, 10, 160, 50);
        mediaPlayer = MediaPlayer.create(context, R.raw.song);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 1);//(#Stream, don't touch, don't touch)

        //create tower and load bitmap
        this.player = new Player(1000,10,20);
        this.opponent = new Player(100,10,20);

        towers = new ArrayList<Towers>();
        buttons = new ArrayList<Buttons>();
        shots = new ArrayList<Shot>();
        enemies = new ArrayList<Enemy>();
        life_bars = new ArrayList<LifeBar>();

        /*sound_pop = {sp.load(getContext(), R.raw.gobelin_pop, 1),
        sp.load(getContext(), R.raw.eye_pop, 1),
        sp.load(getContext(), R.raw.devil_pop, 1),
        sp.load(getContext(), R.raw.wolf_pop, 1),
        sp.load(getContext(), R.raw.skeleton_pop, 1),
        sp.load(getContext(), R.raw.dwarf_pop, 1),
        sp.load(getContext(), R.raw.charizard_pop, 1),
        sp.load(getContext(), R.raw.golem_pop, 1),
        sp.load(getContext(), R.raw.robot_pop, 1),
        sp.load(getContext(), R.raw.gryphon_pop, 1),
        sp.load(getContext(), R.raw.fairy_pop, 1),
        sp.load(getContext(), R.raw.darth_vader_pop, 1),
        sp.load(getContext(), R.raw.blue_dragon_pop, 1),
        sp.load(getContext(), R.raw.pikachu_pop, 1),
        sp.load(getContext(), R.raw.spider_pop, 1),
        sp.load(getContext(), R.raw.devil2_pop, 1),
        sp.load(getContext(), R.raw.eagle_pop, 1)};*/

        /*sound_death = {sp.load(getContext(), R.raw.gobelin_death, 1),
        sp.load(getContext(), R.raw.eye_death, 1),
        sp.load(getContext(), R.raw.devil_death, 1),
        sp.load(getContext(), R.raw.wolf_death, 1),
        sp.load(getContext(), R.raw.skeleton_death, 1),
        sp.load(getContext(), R.raw.dwarf_death, 1),
        sp.load(getContext(), R.raw.charizard_death, 1),
        sp.load(getContext(), R.raw.golem_death, 1),
        sp.load(getContext(), R.raw.robot_death, 1),
        sp.load(getContext(), R.raw.gryphon_death, 1),
        sp.load(getContext(), R.raw.fairy_death, 1),
        sp.load(getContext(), R.raw.darth_vader_death, 1),
        sp.load(getContext(), R.raw.blue_dragon_death, 1),
        sp.load(getContext(), R.raw.pikachu_death, 1),
        sp.load(getContext(), R.raw.spider_death, 1),
        sp.load(getContext(), R.raw.devil2_death, 1),
        sp.load(getContext(), R.raw.eagle_death, 1)};*/

        //create the game loop thread
        thread = new MainThread(getHolder(), this);

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
                    checkButtonClick(event);
                } else {
                    buildingZone = map.BuildingZone(event.getX() - canvasX,event.getY() - canvasY);
                    //Log.d(TAG, "BZ: x=" + buildingZone.get(0) + ",y=" + buildingZone.get(1));
                    // check if in the lower part of the screen we exit
                    if (event.getY() > getHeight() - 50) {
                        thread.setRunning(false);
                        ((Activity) getContext()).finish();
                    } else {
                        //Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
                    }
                    // delegating event handling to the towers
                    if(buildingZone.get(0) > 0) {
                        towerOnClickEvent(buildingZone.get(0), buildingZone.get(1));
                    }
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.abs(event.getX()-x1)>map.getBlockSizeX()/2 && Math.abs(event.getY()-y1)>map.getBlockSizeY()/2) {
                thread.setCanvasMoved(true);
                if((canvasX + event.getX() - x1) <= 0 && (map.getMapsizeX() - getWidth()) > Math.abs(canvasX + event.getX() - x1)) {
                    canvasX = canvasX + (event.getX() - x1);
                }
                if((canvasY + event.getY() - y1) < 0 && (map.getMapsizeY() - getHeight()) > Math.abs(canvasY + event.getY() - y1)){
                    canvasY = canvasY + (event.getY() - y1);
                }
                x1 = event.getX();
                y1 = event.getY();
            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            thread.setCanvasMoved(false);
            x1 = event.getX();
            y1 = event.getY();
        }
        return true;
    }

    private void checkButtonClick(MotionEvent event){
        for (int i = 0; i < buttons.size(); i++) {
            if (((event.getX() - canvasX) < buttons.get(i).getX() + buttons.get(i).getBitmap().getWidth() / 2) && ((event.getX() - canvasX) > buttons.get(i).getX() - buttons.get(i).getBitmap().getWidth() / 2)) {
                if (((event.getY() - canvasY) < buttons.get(i).getY() + buttons.get(i).getBitmap().getHeight() / 2) && ((event.getY() - canvasY) > buttons.get(i).getY() - buttons.get(i).getBitmap().getHeight() / 2)) {
                    buttons.get(i).getEvent(towers, getContext(), player);
                }
            }
        }
        if (newButtons){
            buttons.clear();//buttons = new ArrayList<Buttons>();
        }
    }

    private void towerOnClickEvent(long x, long y) {
        boolean create = true;
        for (int i = 0; i < towers.size(); i++) {
            towers.get(i).handleActionDown((int) x, (int) y);
            if (towers.get(i).isTouched()) {
                create = false;
                towerUpgrade(i);
                //towers.get(i).info();
            }
            if (!create) {
                //break;
            }
        }
        if (create) {
            towerCreation(x, y);
        }
    }

    private void towerUpgrade(int towerIndex) {
        buttons.add(new Buttons_tower_upgrade((int) towers.get(towerIndex).getX(), (int) towers.get(towerIndex).getY() - 200, getContext(), towerIndex));
        buttons.add(new Buttons_tower_stop((int) towers.get(towerIndex).getX(), (int) towers.get(towerIndex).getY(), getContext()));
        buttons.add(new Buttons_tower_delete((int) towers.get(towerIndex).getX(), (int) towers.get(towerIndex).getY() + 200, getContext(), towerIndex));
    }

    private void towerCreation(long x, long y) {
        buttons.add(new Buttons_arcane_tower_creation((int) (x), (int) (y - 200), getContext()));
        buttons.add(new Buttons_mortar_tower_creation((int) (x - 200), (int) (y), getContext()));
        buttons.add(new Buttons_archer_tower_creation((int) (x), (int) (y + 200), getContext()));
        buttons.add(new Buttons_magma_tower_creation((int) (x + 200), (int) (y), getContext()));
        buttons.add(new Buttons_tower_stop((int) (x), (int) (y), getContext()));
    }

    protected void render(Canvas canvas) {
        canvas.drawPaint(paint_canvas);
        map.draw(canvas);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(canvas);
        }
        for (int i = 0; i < life_bars.size(); i++) {
            life_bars.get(i).draw(canvas);
        }

        for (int i = 0; i < towers.size(); i++) {
            towers.get(i).draw(canvas);
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
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(canvas);
        }
    }


    public void update() {// check collision with right wall if heading right
        if (player.getLife() <= 0) {
            if (!finish){
                ((MainActivity) activity).saveState("Defeat");
                finish = true;
            }
            if (thread.getCanvasMoved() || newButtons){
                newButtons = false;
                buttons.clear();
                buttons.add(new Defeat((int) (this.getWidth() / 2 - canvasX), (int) (this.getHeight() / 2 - canvasY - 100), getContext()));
                buttons.add(new Buttons_continue((int) (this.getWidth() / 2 - canvasX), (int) (this.getHeight() / 2 - canvasY + 400), getContext(), this.activity));
            }
        } else if (opponent.getLife() <= 0) {
            if (!finish){
                ((MainActivity) activity).saveState("Victory");
                finish = true;
            }
            if (thread.getCanvasMoved() || newButtons){
                newButtons = false;
                buttons.clear();
                buttons.add(new Victory((int) (this.getWidth() / 2 - canvasX), (int) (this.getHeight() / 2 - canvasY - 100), getContext()));
                buttons.add(new Buttons_continue((int) (this.getWidth() / 2 - canvasX), (int) (this.getHeight() / 2 - canvasY + 400), getContext(), this.activity));
            }
        } else if (pause) {
            if (thread.getCanvasMoved() || newButtons){
                newButtons = false;
                buttons.clear();
                buttons.add(new Pause((int) (this.getWidth() / 2 - canvasX), (int) (this.getHeight() / 2 - canvasY - 150), getContext()));
                buttons.add(new Buttons_resume((int) (this.getWidth() / 2 - canvasX - 400), (int) (this.getHeight() / 2 - canvasY + 400), getContext(), this.activity));
                buttons.add(new Buttons_leave((int) (this.getWidth() / 2 - canvasX + 400), (int) (this.getHeight() / 2 - canvasY + 400), getContext(), this.activity));
            }
        } else {
            if (System.currentTimeMillis() - a > 10000) {
                player.getFunding();
                a = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - a > 10000) {
                player.getFunding();
                a = System.currentTimeMillis();
            }
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).enemyUpdate(map, System.currentTimeMillis());
            }
            for (int i = 0; i < life_bars.size(); i++) {
                life_bars.get(i).update(enemies.get(i));
            }
            missileUpdate();
            missileCreation();
            enemiesUpdate();
        }
    }

    private void enemiesUpdate() {
        for (int j = 0; j < enemies.size(); j++) {
            if (enemies.get(j).getHp() <=0){
                player.increaseGold(enemies.get(j).getValue());
                //sp.play(sound_death[enemies.get(j).getType()],1,1,0,0,1);
                enemies.remove(j);
                life_bars.remove(j);
            }else if(enemies.get(j).getX() == map.getEndZoneX() && enemies.get(j).getY() == map.getEndZoneY()){
                enemies.remove(j);
                life_bars.remove(j);
                player.looseLife();
            }
        }
    }

    private void missileCreation() {
        for (int i = 0; i < towers.size(); i++) {
            if (towers.get(i).canFire()) {
                double dist_aim = 1000000;
                int aim = 0;
                for (int j = 0; j < enemies.size(); j++) {
                    double dx = towers.get(i).getX()- enemies.get(j).getX();
                    double dy = towers.get(i).getY()- enemies.get(j).getY();
                    double dist = Math.sqrt(dx*dx + dy*dy);
                    //Log.d(TAG, "dist:" + dist);
                    if (towers.get(i).getRange() >= dist){
                        dx = map.getEndZoneX() - enemies.get(j).getX();
                        dy = map.getEndZoneY() - enemies.get(j).getY();
                        dist = Math.sqrt(dx*dx + dy*dy);
                        if (dist < dist_aim) {
                            aim = j;
                            dist_aim = dist;
                        }
                    }
                }
                if (dist_aim != 1000000) {
                    towers.get(i).fire();
                    //Log.d(TAG, "1 missile created");
                    towers.get(i).shot(shots, getContext(), enemies.get(aim));
                }
            }
        }
    }


    private void missileUpdate() {
        for (int i = 0; i < shots.size(); i++) {
            shots.get(i).update();
            if (shots.get(i).hasHit()){
                Log.d(TAG, "Has Hit");
                for (int j = 0; j < enemies.size(); j++) {
                    if ((Math.abs(enemies.get(j).getX() - shots.get(i).getX()) < 15) && (Math.abs(enemies.get(j).getY() - shots.get(i).getY()) < 15)){
                        int damage = shots.get(i).getDamage();
                        Log.d(TAG, "DAMAGE");
                        enemies.get(j).damaged(damage);
                        life_bars.get(j).damaged(damage);
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
            canvas.drawText(fps, this.getWidth() - 100- canvasX, 20 - canvasY, paint);
        }
    }

    public void create(int i){
        //sp.play(sound_pop[i],1,1,0,0,1);
        switch (i){
            case 1:
                CreateMonster(new Gobelin(getContext(), map.getLogicPath()));
                //sp.play(sp.load(getContext(), R.raw.test,1),1,1,0,0,1);
                return;
            case 2 :
                CreateMonster(new Eye(getContext(),map.getLogicPath()));
                return;
            case 3:
                CreateMonster(new Devil(getContext(), map.getLogicPath()));
                return;
            case 4 :
                CreateMonster(new Wolf(getContext(),map.getLogicPath()));
                return;
            case 5:
                CreateMonster(new Skeleton(getContext(), map.getLogicPath()));
                return;
            case 6:
                CreateMonster(new Dwarf(getContext(), map.getLogicPath()));
                return;
            case 7 :
                CreateMonster(new Charizard(getContext(), map.getLogicPath()));
                return;
            case 8:
                CreateMonster(new Golem(getContext(), map.getLogicPath()));
                return;
            case 9 :
                CreateMonster(new Robot(getContext(),map.getLogicPath()));
                return;
            case 10:
                CreateMonster(new Gryphon(getContext(), map.getLogicPath()));
                return;
            case 11:
                CreateMonster(new Fairy(getContext(),map.getLogicPath()));
                return;
            case 12:
                CreateMonster(new DarkVador(getContext(), map.getLogicPath()));
                return;
            case 13 :
                CreateMonster(new BlueDragon(getContext(),map.getLogicPath()));
                return;
            case 14:
                CreateMonster(new Pikachu(getContext(), map.getLogicPath()));
                return;
            case 15:
                CreateMonster(new Spider(getContext(),map.getLogicPath()));
                return;
            case 16:
                CreateMonster(new Devil2(getContext(),map.getLogicPath()));
                return;
            case 17:
                CreateMonster(new Eagle(getContext(),map.getLogicPath()));
                return;
            default:
                return;
        }
    }


    public Enemy MonsterType(int i){
        switch (i){
            case 1:
                return new Gobelin(getContext(), map.getLogicPath());
            case 2 :
                return new Eye(getContext(),map.getLogicPath());
            case 3:
                return new Devil(getContext(), map.getLogicPath());
            case 4 :
                return new Eagle(getContext(),map.getLogicPath());
            case 5:
                return new Skeleton(getContext(), map.getLogicPath());
            case 6:
                return new Dwarf(getContext(), map.getLogicPath());
            case 7 :
                return new Devil2(getContext(),map.getLogicPath());
            case 8:
                return new Golem(getContext(), map.getLogicPath());
            case 9 :
                return new Robot(getContext(),map.getLogicPath());
            case 10:
                return new Gryphon(getContext(), map.getLogicPath());
            case 11:
                return new Fairy(getContext(),map.getLogicPath());
            case 12:
                return new DarkVador(getContext(), map.getLogicPath());
            case 13 :
                return new BlueDragon(getContext(),map.getLogicPath());
            case 14:
                return new Pikachu(getContext(), map.getLogicPath());
            case 15:
                return new Spider(getContext(),map.getLogicPath());
            case 16:
                return new Charizard(getContext(), map.getLogicPath());
            case 17:
                return new Wolf(getContext(),map.getLogicPath());
            default:
                return new Wolf(getContext(),map.getLogicPath());
        }
    }

    public void CreateMonster(Enemy enemy){
        enemies.add(enemy);
        life_bars.add(new LifeBar(enemy, getContext()));
    }

    public Player getPlayer() {
        return this.player;
    }

    public float getCanvasX() {return canvasX;}

    public float getCanvasY() {return canvasY;}

    public Player getOpponent() {
        return opponent;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public MainThread getThread() {
        return thread;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setNewButtons(boolean newButtons) {
        this.newButtons = newButtons;
    }

    public void delayTime(long time) {
        this.a = this.a + time;
    }
}
