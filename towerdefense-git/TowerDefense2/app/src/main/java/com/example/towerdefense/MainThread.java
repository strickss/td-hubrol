package com.example.towerdefense;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Brieuc on 11-11-15.
 */
public class MainThread extends Thread {
            // flag to hold game state

    private static final String TAG = MainThread.class.getSimpleName();
    private boolean running;
    private SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;

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
        Canvas canvas; //declare the canvas on which we will draw our image. The canvas is the surface’s bitmap onto which we can draw and we can edit its pixels
        Log.d(TAG, "Starting game loop");
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
}
