package com.example.towerdefense;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int REQUEST_ENABLE_BT = 1;
    private ArrayAdapter<String> mArrayAdapter;
    private MainGamePanel gamePanel;
    private Handler mHandler;
    private TextView textGold;
    private TextView textYourIncome;
    private TextView textOppIncome;
    private TextView textYourLife;
    private TextView textOppLife;
    private Chronometer chronometer;
    private long a = System.currentTimeMillis();
    private boolean updateMenu;
    private PopupMenu popup;

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
        gamePanel = (MainGamePanel) findViewById(R.id.GamePanel);

        Log.d(TAG, "View added");

        final ImageButton imageButton1 = (ImageButton) findViewById(R.id.button_1);
        final ImageButton imageButton2 = (ImageButton) findViewById(R.id.button_2);
        final ImageButton imageButton3 = (ImageButton) findViewById(R.id.button_3);
        final ImageButton imageButton4 = (ImageButton) findViewById(R.id.button_4);
        this.chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();

     /*   BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
        try {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
<<<<<<< HEAD
=======

>>>>>>> origin/master
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView
                    mArrayAdapter = new ArrayAdapter<>(this, R.layout.arrays);
                    //ListView newDevicesListView = (ListView) findViewById(R.id.array);
                    //newDevicesListView.setAdapter(mArrayAdapter);
                    //newDevicesListView.setOnItemClickListener(mDeviceClickListener);
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        } catch (Exception e) {
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // If there are paired devices

        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter = new ArrayAdapter<>(this, R.layout.arrays);
                //ListView newDevicesListView = (ListView) findViewById(R.id.array);
                //newDevicesListView.setAdapter(mArrayAdapter);
<<<<<<< HEAD
=======

>>>>>>> origin/master
            }
        }*/

        textGold = (TextView) findViewById(R.id.gold);
        textYourIncome = (TextView) findViewById(R.id.yourIncomeValue);
        textOppIncome = (TextView) findViewById(R.id.oppIncomeValue);
        textYourLife = (TextView) findViewById(R.id.yourLifeValue);
        textOppLife = (TextView) findViewById(R.id.oppLifeValue);
        updateMenu = true;
        mHandler = new Handler();
        mHandler.post(mUpdate);
    }

    private Runnable mUpdate = new Runnable() {
        public void run() {
            int txtGold = gamePanel.getPlayer().getGold();
            textGold.setText(""+txtGold);
            int txtYourIncome = gamePanel.getPlayer().getIncome();
            textYourIncome.setText(""+txtYourIncome);
            int txtOppIncome = gamePanel.getPlayer().getIncome() +1;
            textOppIncome.setText(""+txtOppIncome);
            int txtYourLife = gamePanel.getPlayer().getLife();
            textYourLife.setText("" + txtYourLife);
            int txtOppLife = gamePanel.getPlayer().getLife() + 1;
            textOppLife.setText("" + txtOppLife);
            mHandler.postDelayed(this, 100);

            if (updateMenu) {
                if (System.currentTimeMillis() - a > 10000) {
                    try {
                        popup.dismiss();
                        popUpMenuUpdate();
                    } catch (Exception e){}
                    updateMenu = false;
                }
            }
        }
    };

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

    public void showPopup(View v) {
        popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.monsters, popup.getMenu());
        try {
            if (System.currentTimeMillis() - a > 10000) {
                popup.getMenu().findItem(R.id.monster2).setVisible(true);
            }
            Field field = popup.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            Object menuPopupHelper = field.get(popup);
            Class<?> cls = Class.forName("com.android.internal.view.menu.MenuPopupHelper");
            Method method = cls.getDeclaredMethod("setForceShowIcon", boolean.class);
            method.setAccessible(true);
            method.invoke(menuPopupHelper, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.monster1:
                gamePanel.CreateMonster(1);
                popUpMenuUpdate();
                return true;
            case R.id.monster2:
                gamePanel.CreateMonster(2);
                popUpMenuUpdate();
                return true;
            default:
                return false;

        }
    }

    public void popUpMenuUpdate(){
        showPopup(findViewById(R.id.button_1));
    }

}
