package com.example.towerdefense;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        //setContentView(new MainGamePanel(this));
        setContentView(R.layout.activity_main);


        Log.d(TAG, "View added");

        //final ImageButton imageButton = (ImageButton) findViewById(R.id.button_1);

        final ImageButton imageButton1 = (ImageButton) findViewById(R.id.button_1);
        final ImageButton imageButton2 = (ImageButton) findViewById(R.id.button_2);
        final ImageButton imageButton3 = (ImageButton) findViewById(R.id.button_3);
        final ImageButton imageButton4 = (ImageButton) findViewById(R.id.button_4);

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }


}