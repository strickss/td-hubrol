package com.example.towerdefense;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import java.lang.reflect.Field;
import java.lang.reflect.Method;


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

    //private List<View> text_monsters;
    //private ArrayList<creationButton> monster_creationButtons;

    private Handler mHandler_menu;

    private List<View> text_monsters;
    private ArrayList<creationButton> monster_creationButtons;
    //private List<View> text_monsters;
    //private ArrayList<creationButton> monster_creationButtons;


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

        mHandler_menu = new Handler();
        mHandler_menu.post(mUpdate);

    }


    private Runnable mUpdate = new Runnable() {
        public void run() {
            int txtGold = gamePanel.getPlayer().getGold();
            textGold.setText("" + txtGold);
            int txtYourIncome = gamePanel.getPlayer().getIncome();
            textYourIncome.setText("" + txtYourIncome);
            int txtOppIncome = gamePanel.getPlayer().getIncome() + 1;
            textOppIncome.setText("" + txtOppIncome);
            int txtYourLife = gamePanel.getPlayer().getLife();
            textYourLife.setText("" + txtYourLife);
            int txtOppLife = gamePanel.getPlayer().getLife() + 1;
            textOppLife.setText("" + txtOppLife);


            //for (int i=0; i < text_monsters.size(); i++){
            //    monster_creationButtons.get(i).update(a);
            //    ((TextView) text_monsters.get(i)).setText("" + monster_creationButtons.get(i).getNumber());
            //}
  //for (int i=0; i < text_monsters.size(); i++){
            //    monster_creationButtons.get(i).update(a);
            //    ((TextView) text_monsters.get(i)).setText("" + monster_creationButtons.get(i).getNumber());
            //}


            mHandler_menu.postDelayed(this, 100);

            /*
            if (updateMenu) {
                if (System.currentTimeMillis() - a > 10000) {
                    try {
                        popup.dismiss();
                        showPopup1(view);
                    } catch (Exception e) {
                    }
                    updateMenu = false;
                }


            }
>>>>>>> refs/remotes/origin/Tower_creation
            */

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



    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
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
                gamePanel.create(1);
                showPopup1(view);
                //sendMessage("1");
                return true;
            case R.id.eye:

                sendMessage("2");

                gamePanel.create(2);
                showPopup1(view);
                //sendMessage("2");
                return true;
            case R.id.devil:
                gamePanel.create(3);
                showPopup1(view);
                //sendMessage("3");
                return true;
            case R.id.eagle:
                gamePanel.create(4);
                showPopup1(view);
                //sendMessage("4");
                return true;
            case R.id.skeleton:
                gamePanel.create(5);
                showPopup1(view);
                //sendMessage("5");
                return true;

            case R.id.dwarf:
                gamePanel.create(6);
                showPopup2(view);
                //sendMessage("6");
                return true;
            case R.id.devil2:
                gamePanel.create(7);
                showPopup2(view);
                //sendMessage("7");
                return true;
            case R.id.golem:
                gamePanel.create(8);
                showPopup2(view);
                //sendMessage("8");
                return true;
            case R.id.robot:
                gamePanel.create(9);
                showPopup2(view);
                //sendMessage("9");
                return true;

            case R.id.gryphon:
                gamePanel.create(10);
                showPopup3(view);
                //sendMessage("10");
                return true;
            case R.id.fairy:
                gamePanel.create(11);
                showPopup3(view);
                //sendMessage("11");
                return true;
            case R.id.dark_vador:
                gamePanel.create(12);
                showPopup3(view);
                //sendMessage("12");
                return true;
            case R.id.blue_dragon:
                gamePanel.create(13);
                showPopup3(view);
                //sendMessage("13");
                return true;

            case R.id.pikachu:
                gamePanel.create(14);
                showPopup4(view);
                //sendMessage("14");
                return true;
            case R.id.spider:
                gamePanel.create(15);
                showPopup4(view);
                //sendMessage("15");
                return true;
            case R.id.unicorn:
                gamePanel.create(16);
                showPopup4(view);
                //sendMessage("16");
                return true;
            case R.id.wolf:
                gamePanel.create(17);
                showPopup4(view);
                //sendMessage("17");
                return true;

            default:
                return false;
        }
    }

    //@Override
    public boolean onMenuItemClick_t(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goblin:
                //gamePanel.create(1);
                showPopup1(view);
                sendMessage("1");
                gamePanel.PayMonsterType(1);
                return true;
            case R.id.eye:

                //gamePanel.create(2);
                showPopup1(view);
                sendMessage("2");
                gamePanel.PayMonsterType(2);
                return true;
            case R.id.devil:

                
                gamePanel.create(3);

                //gamePanel.create(3);

                showPopup1(view);
                sendMessage("3");
                gamePanel.PayMonsterType(3);
                return true;
            case R.id.eagle:



                //gamePanel.create(4);
                showPopup1(view);
                sendMessage("4");
                gamePanel.PayMonsterType(4);
                return true;
            case R.id.skeleton:



                //gamePanel.create(5);
                showPopup1(view);
                sendMessage("5");
                gamePanel.PayMonsterType(5);
                return true;

            case R.id.dwarf:



                //gamePanel.create(6);
                showPopup2(view);
                sendMessage("6");
                gamePanel.PayMonsterType(6);
                return true;
            case R.id.devil2:



                //gamePanel.create(7);
                showPopup2(view);
                sendMessage("7");
                gamePanel.PayMonsterType(7);
                return true;
            case R.id.golem:



                //gamePanel.create(8);
                showPopup2(view);
                sendMessage("8");
                gamePanel.PayMonsterType(8);
                return true;
            case R.id.robot:



                //gamePanel.create(9);
                showPopup2(view);
                sendMessage("9");
                gamePanel.PayMonsterType(9);
                return true;

            case R.id.gryphon:



                //gamePanel.create(10);
                showPopup3(view);
                sendMessage("10");
                gamePanel.PayMonsterType(10);
                return true;
            case R.id.fairy:



                //gamePanel.create(11);
                showPopup3(view);
                sendMessage("11");
                gamePanel.PayMonsterType(11);
                return true;
            case R.id.dark_vador:



                //gamePanel.create(12);
                showPopup3(view);
                sendMessage("12");
                gamePanel.PayMonsterType(12);
                return true;
            case R.id.blue_dragon:



                //gamePanel.create(13);
                showPopup3(view);
                sendMessage("13");
                gamePanel.PayMonsterType(13);
                return true;

            case R.id.pikachu:
                sendMessage("14");

                //gamePanel.create(14);
                showPopup4(view);
                sendMessage("14");
                gamePanel.PayMonsterType(14);
                return true;
            case R.id.spider:

                //gamePanel.create(15);
                showPopup4(view);
                sendMessage("15");
                gamePanel.PayMonsterType(15);
                return true;
            case R.id.unicorn:

                //gamePanel.create(16);
                showPopup4(view);
                sendMessage("16");
                gamePanel.PayMonsterType(16);
                return true;
            case R.id.wolf:
                //gamePanel.create(17);
                showPopup4(view);
                sendMessage("17");
                gamePanel.PayMonsterType(17);
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
                    gamePanel.create(i);
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



    public Activity getActivity() {
        return this;
    }

}


