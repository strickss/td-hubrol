package com.example.towerdefense;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;

import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    private String mConnectedDeviceName = null;
    private MainGamePanel gamePanel;
    private TextView textGold;
    private TextView textYourIncome;
    private TextView textOppIncome;
    private TextView textYourLife;
    private TextView textOppLife;
    private Chronometer chronometer;
    private long a = System.currentTimeMillis();
    private boolean updateMenu;
    private PopupMenu popup;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothChatService mChatService = null;
    private StringBuffer mOutStringBuffer;
    static final int PICK_DEVICE_REQUEST = 1;  // The request code
    private View view;
    private Handler mHandler_menu;
    private List<View> text_monsters;
    private ArrayList<creationButton> monster_creationButtons;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF;
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

        textGold = (TextView) findViewById(R.id.gold);
        textYourIncome = (TextView) findViewById(R.id.yourIncomeValue);
        textOppIncome = (TextView) findViewById(R.id.oppIncomeValue);
        textYourLife = (TextView) findViewById(R.id.yourLifeValue);
        textOppLife = (TextView) findViewById(R.id.oppLifeValue);
        updateMenu = true;
        mHandler_menu = new Handler();
        mHandler_menu.post(mUpdate);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(this, "No Bluetooth on this handset", duration).show();

        }
    }


    private Runnable mUpdate = new Runnable() {
        public void run() {
            int txtGold = gamePanel.getPlayer().getGold();
            textGold.setText("" + txtGold);
            int txtYourIncome = gamePanel.getPlayer().getIncome();
            textYourIncome.setText("" + txtYourIncome);
            txtYourIncome = txtYourIncome + 200;
            sendMessage("" + txtYourIncome);
            int txtOppIncome = gamePanel.getOpponent().getIncome();
            Log.d(TAG,""+txtOppIncome);
            textOppIncome.setText("" + txtOppIncome);
            int txtYourLife = gamePanel.getPlayer().getLife();
            textYourLife.setText("" + txtYourLife);
            txtYourLife = txtYourLife + 100;
            sendMessage("" + txtYourLife);
            int txtOppLife = gamePanel.getOpponent().getLife();
            textOppLife.setText("" + txtOppLife);

            mHandler_menu.postDelayed(this, 100);
        }
    };

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }


    @Override
    public void onResume() {
        super.onResume();


        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    private void TrySendMessage(int i){
        if (gamePanel.getPlayer().getGold() >= gamePanel.MonsterType(i).getCost()){
            gamePanel.getPlayer().cost(gamePanel.MonsterType(i).getCost());
            gamePanel.getPlayer().increaseIncome(gamePanel.MonsterType(i).getValue());
            //sendMessage(""+i);
            gamePanel.create(i);
        } else {
            Toast toast = Toast.makeText(this, "Not enough gold !", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            //Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goblin:
                //gamePanel.create(1);
                showPopup1(view);
                TrySendMessage(1);
                return true;
            case R.id.eye:
                //gamePanel.create(2);
                showPopup1(view);
                TrySendMessage(2);
                return true;
            case R.id.devil:
                showPopup1(view);
                TrySendMessage(3);
                return true;
            case R.id.wolf:
                //gamePanel.create(17);
                showPopup1(view);
                TrySendMessage(4);
                return true;
            case R.id.skeleton:
                //gamePanel.create(5);
                showPopup1(view);
                TrySendMessage(5);
                return true;

            case R.id.dwarf:
                //gamePanel.create(6);
                showPopup2(view);
                TrySendMessage(6);
                return true;
            case R.id.charizard:
                //gamePanel.create(16);
                showPopup2(view);
                TrySendMessage(7);
                return true;
            case R.id.golem:
                //gamePanel.create(8);
                showPopup2(view);
                TrySendMessage(8);
                return true;
            case R.id.robot:
                //gamePanel.create(9);
                showPopup2(view);
                TrySendMessage(9);
                return true;

            case R.id.gryphon:
                //gamePanel.create(10);
                showPopup3(view);
                TrySendMessage(10);
                return true;
            case R.id.fairy:
                //gamePanel.create(11);
                showPopup3(view);
                TrySendMessage(11);
                return true;
            case R.id.dark_vador:
                //gamePanel.create(12);
                showPopup3(view);
                TrySendMessage(12);
                return true;
            case R.id.blue_dragon:
                //gamePanel.create(13);
                showPopup3(view);
                TrySendMessage(13);
                return true;

            case R.id.pikachu:
                //gamePanel.create(14);
                showPopup4(view);
                TrySendMessage(14);
                return true;
            case R.id.spider:
                //gamePanel.create(15);
                showPopup4(view);
                TrySendMessage(15);
                return true;
            case R.id.devil2:
                //gamePanel.create(7);
                showPopup4(view);
                TrySendMessage(16);
                return true;
            case R.id.eagle:
                //gamePanel.create(4);
                showPopup4(view);
                TrySendMessage(17);
                return true;

            default:
                return false;
        }
    }

    public void showPopup1(View v) {
        view = v;
        popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.monsters_normal, popup.getMenu());
        try {
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

    public void showPopup2(View v) {
        view = v;
        popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.monsters_armored, popup.getMenu());
        try {
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

    public void showPopup3(View v) {
        view = v;
        popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.monsters_magic, popup.getMenu());
        try {
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

    public void showPopup4(View v) {
        view = v;
        popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.monsters_fast, popup.getMenu());
        try {
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

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            //setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            //setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    int i = new Integer(readMessage);
                    if (i <100) {
                        gamePanel.create(i);
                    } else if (i<200){
                        gamePanel.getOpponent().setLife(i-100);
                    } else if (i<10000){
                        gamePanel.getOpponent().setIncome(i-200);
                    }
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != this) {
                        Toast.makeText(getActivity(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != this) {
                        Toast.makeText(getActivity(), msg.getData().getString(Constants.TOAST),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    public Activity getActivity() {
        return this;
    }

    public BluetoothAdapter getBluetoothAdapter(){
        return mBluetoothAdapter;
    }

    public BluetoothChatService getBTChatService(){
        return mChatService;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupCom();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }

    private void setupCom() {
        Log.d(TAG, "setupCom()");

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);
        Intent deviceIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(deviceIntent, PICK_DEVICE_REQUEST);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }


    @Override
    protected void onStart(){
        super.onStart();
        //requests that bluetooth is enabled if it is not
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupCom();
        }
    }


}


