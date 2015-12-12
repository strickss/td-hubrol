package com.example.towerdefense;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class HomeScreenActivity extends Activity {


    private static final String TAG = HomeScreenActivity.class.getSimpleName();
    private static final int GAME_ON = 1;
    public MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "View not added");
        setContentView(R.layout.activity_home_screen);
        Log.d(TAG, "View added");

        final ImageButton imageButton1 = (ImageButton) findViewById(R.id.play);
        final ImageButton imageButton2 = (ImageButton) findViewById(R.id.history);
    }



    public void play_game(View v){
        Intent intentGame = new Intent(this, MainActivity.class);
        startActivityForResult(intentGame,GAME_ON);
    }

    public void show_history(View v){
        return;
    }
}