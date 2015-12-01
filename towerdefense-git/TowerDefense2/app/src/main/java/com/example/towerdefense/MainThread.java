package com.example.towerdefense;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.TextView;

import java.text.CollationElementIterator;
import java.text.DecimalFormat;

/**
 * Created by Brieuc on 11-11-15.
 */
public class MainThread extends Thread {
            // flag to hold game state

    private static final String TAG = MainThread.class.getSimpleName();
    private boolean running;
    private SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;

    private final static int MAX_FPS = 50; //desired FPS
    private final static int MAX_FRAME_SKIPS = 5;  //maximum number of frames to be skipped
    private final static int FRAME_PERIOD = 1000/MAX_FPS; // the frame period

    // Stuff for stats */
    private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
    private final static int STAT_INTERVAL = 1000; //ms // we'll be reading the stats every second
    private final static int FPS_HISTORY_NR = 10;// the average will be calculated by storing the last n FPSs
    private long lastStatusStore = 0;// last time the status was stored
    private long totalFramesSkipped = 0l;// number of frames skipped since the game started
    private long framesSkippedPerStatCycle = 0l;// number of frames skipped in a store cycle (1 sec)
    private int frameCountPerStatCycle = 0;// number of rendered frames in an interval
    private long totalFrameCount = 0l;
    private double fpsStore[];// the last FPS values
    private long statsCount = 0;// the number of times the stat has been read
    private double averageFps = 0.0;// the average FPS since the game started
    private Canvas canvas;
    private boolean canvasMoved=false;

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        //Canvas canvas; //declare the canvas on which we will draw our image. The canvas is the surface’s bitmap onto which we can draw and we can edit its pixels
        Log.d(TAG, "Starting game loop");
        initTimingElements();
        long timeDiff; //the time it took for the cycle to execute
        int sleepTime=0; //ms to sleep (<0 if we're behind the update time)
        int framesSkipped; //number of frames being skipped
        long beginTime =0;
        long endTime;

        while (running) {
            canvas = null;
            // try locking the canvas for exclusive pixel editing on the surface
            try { //try to get hold of it
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    // update game state
                    // draws the canvas on the panel
                    this.gamePanel.update(); //we trigger the panel’s onDraw event to which we pass the obtained canvas
                    this.gamePanel.render(canvas);
                    framesSkipped = 0; //resetting the frames skipped
                    this.gamePanel.update(); // update game state
                    this.gamePanel.render(canvas); // draws the canvas on the panel
                    endTime = System.currentTimeMillis();
                    timeDiff = endTime - beginTime - sleepTime;
                    //Log.d(TAG, "Time:" + timeDiff);
                    beginTime = endTime;
                    sleepTime = (int) (FRAME_PERIOD - timeDiff);
                    //Log.d(TAG, "Sleep time:" + sleepTime);
                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }
                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        this.gamePanel.update(); // update without rendering
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                    if (framesSkipped >0){
                        //Log.d(TAG, "Skipped:" + framesSkipped);
                        sleepTime = framesSkipped*FRAME_PERIOD;
                    }
                    framesSkippedPerStatCycle += framesSkipped;//for statistics
                    storeStats();// calling the routine to store the gathered statistics
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            } // end finally
        }
    }

    /**
     * The statistics - it is called every cycle, it checks if time since last
     * store is greater than the statistics gathering period (1 sec) and if so
     * it calculates the FPS for the last period and stores it.
     *
     *  It tracks the number of frames per period. The number of frames since
     *  the start of the period are summed up and the calculation takes part
     *  only if the next period and the frame count is reset to 0.
     */

    private void storeStats() {
        frameCountPerStatCycle++;
        totalFrameCount++;
        // check the actual time
        if (System.currentTimeMillis() >= lastStatusStore + STAT_INTERVAL) {
            // calculate the actual frames pers status check interval
            double actualFps = (double)(frameCountPerStatCycle / (STAT_INTERVAL / 1000));
            //Log.d(TAG, "actualFPS:" + actualFps);
            //double actualUps = (double)((frameCountPerStatCycle+framesSkippedPerStatCycle) / (STAT_INTERVAL / 1000));
            //Log.d(TAG, "actualUPS:" + actualUps);
            //stores the latest fps in the array
            fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps;
            // increase the number of times statistics was calculated
            statsCount++;
            double totalFps = 0.0;
            // sum up the stored fps values
            for (int i = 0; i < FPS_HISTORY_NR; i++) {
                totalFps += fpsStore[i];
            }
            // obtain the average
            if (statsCount < FPS_HISTORY_NR) {
                // in case of the first 10 triggers
                averageFps = totalFps / statsCount;
            } else {
                averageFps = totalFps / FPS_HISTORY_NR;
            }
            // saving the number of total frames skipped
            totalFramesSkipped += framesSkippedPerStatCycle;
            // resetting the counters after a status record (1 sec)
            framesSkippedPerStatCycle = 0;
            frameCountPerStatCycle = 0;
            lastStatusStore = System.currentTimeMillis();
            //Log.d(TAG, "Average FPS:" + df.format(averageFps));
            gamePanel.setAvgFps("FPS: " + df.format(averageFps));
        }
    }

    private void initTimingElements() {
        // initialise timing elements
        fpsStore = new double[FPS_HISTORY_NR];
        for (int i = 0; i < FPS_HISTORY_NR; i++) {
            fpsStore[i] = 0.0;
        }
        Log.d(TAG + ".initTimingElements()", "Timing elements for stats initialised");
    }

    public Canvas getCanvas() {
        return canvas;
    }
    public void setCanvasMoved(boolean bool){
        canvasMoved = bool;
    }
    public boolean getCanvasMoved(){return canvasMoved;}
}
