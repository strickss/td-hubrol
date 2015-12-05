package com.example.towerdefense;

import android.app.Activity;
<<<<<<< HEAD
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
=======
>>>>>>> origin/master
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Set;
=======
>>>>>>> origin/master


public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int REQUEST_ENABLE_BT = 1;
    private ArrayAdapter<String> mArrayAdapter;
<<<<<<< HEAD
    private ArrayAdapter<String> mArrayAdapter2;
    private ListView newDevicesListView;
    private BluetoothChatService mBluetoothChatService;
    private BluetoothSocket mBTSocket;

=======
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
>>>>>>> origin/master

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

<<<<<<< HEAD


=======
>>>>>>> origin/master
        final ImageButton imageButton1 = (ImageButton) findViewById(R.id.button_1);
        final ImageButton imageButton2 = (ImageButton) findViewById(R.id.button_2);
        final ImageButton imageButton3 = (ImageButton) findViewById(R.id.button_3);
        final ImageButton imageButton4 = (ImageButton) findViewById(R.id.button_4);
        this.chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();

<<<<<<< HEAD
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(this, "No Bluetooth on this handset", duration).show();

        }

        mBluetoothChatService = new BluetoothChatService(this, mBluetoothAdapter);


        final Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
=======
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

<<<<<<< HEAD
>>>>>>> origin/master
=======
>>>>>>> refs/remotes/origin/master
>>>>>>> refs/remotes/origin/Tower_creation
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

>>>>>>> origin/master
        if (pairedDevices.size() > 0) {

            mArrayAdapter = new ArrayAdapter<>(this, R.layout.arrays);
            final ArrayList<BluetoothDevice> mArrayDevices = new ArrayList<>();
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
<<<<<<< HEAD

                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                mArrayDevices.add(device);

=======
                mArrayAdapter = new ArrayAdapter<>(this, R.layout.arrays);
                //ListView newDevicesListView = (ListView) findViewById(R.id.array);
                //newDevicesListView.setAdapter(mArrayAdapter);
<<<<<<< HEAD
=======

<<<<<<< HEAD
>>>>>>> origin/master
=======
>>>>>>> refs/remotes/origin/master
>>>>>>> refs/remotes/origin/Tower_creation
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
>>>>>>> origin/master
            }
            ListView newDevicesListView = (ListView) findViewById(R.id.array);
            newDevicesListView.setAdapter(mArrayAdapter);

            AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BluetoothDevice device = mArrayDevices.get(position);
                    mBluetoothChatService.start();
                    mBTSocket = mBluetoothChatService.connect(device);
                    mBluetoothChatService.connected(mBTSocket, device);

                }
            };
            newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        }

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
<<<<<<< HEAD



        // Create a BroadcastReceiver for ACTION_FOUND
        mArrayAdapter2 = new ArrayAdapter<>(this, R.layout.bluetooth_model);

        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device2 = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView

                    mArrayAdapter2.add(device2.getName() + "\n" + device2.getAddress());

                }
                ListView newDevicesListView2 = (ListView) findViewById(R.id.array2);
                newDevicesListView2.setAdapter(mArrayAdapter2);


            }
        };

        // Register the BroadcastReceiver
        mBluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy


        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(discoverableIntent);// Make the device discoverable to others








    }
=======
    };
>>>>>>> origin/master

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
                popup.getMenu().findItem(R.id.robot).setVisible(true);
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
            case R.id.goblin:
                gamePanel.create(1);
                popUpMenuUpdate();
                return true;
            case R.id.robot:
                gamePanel.create(2);
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
