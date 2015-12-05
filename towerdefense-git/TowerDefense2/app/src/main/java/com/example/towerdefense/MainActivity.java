package com.example.towerdefense;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int REQUEST_ENABLE_BT = 1;
    private ArrayAdapter<String> mArrayAdapter;
    private ArrayAdapter<String> mArrayAdapter2;
    private ListView newDevicesListView;
    private BluetoothChatService mBluetoothChatService;
    private BluetoothSocket mBTSocket;

    private MainGamePanel gamePanel;
    private Handler mHandler_menu;
    private TextView textGold;
    private TextView textYourIncome;
    private TextView textOppIncome;
    private TextView textYourLife;
    private TextView textOppLife;
    private Chronometer chronometer;
    private long a = System.currentTimeMillis();
    private boolean updateMenu;
    private PopupMenu popup;
    private View view;
    private List<View> text_monsters;
    private ArrayList<creationButton> monster_creationButtons;

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

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(this, "No Bluetooth on this handset", duration).show();

        }
        textGold = (TextView) findViewById(R.id.gold);
        textYourIncome = (TextView) findViewById(R.id.yourIncomeValue);
        textOppIncome = (TextView) findViewById(R.id.oppIncomeValue);
        textYourLife = (TextView) findViewById(R.id.yourLifeValue);
        textOppLife = (TextView) findViewById(R.id.oppLifeValue);
        //text_monsters = Arrays.asList(findViewById(R.id.goblin),findViewById(R.id.eye),findViewById(R.id.devil),findViewById(R.id.eagle),findViewById(R.id.skeleton),findViewById(R.id.dwarf),findViewById(R.id.devil2),findViewById(R.id.golem),findViewById(R.id.robot),findViewById(R.id.gryphon),findViewById(R.id.fairy),findViewById(R.id.dark_vador),findViewById(R.id.blue_dragon),findViewById(R.id.pikachu),findViewById(R.id.spider),findViewById(R.id.unicorn),findViewById(R.id.wolf));
        //monster_creationButtons = new ArrayList<creationButton>(Arrays.asList(new creationButton(1000), new creationButton(2000),new creationButton(3000),new creationButton(4000),new creationButton(5000),new creationButton(1000),new creationButton(1000),new creationButton(1000),new creationButton(1000),new creationButton(1000),new creationButton(1000),new creationButton(1000),new creationButton(1000),new creationButton(1000),new creationButton(1000),new creationButton(1000),new creationButton(1000)));
        updateMenu = true;
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
            */
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goblin:
                gamePanel.create(1);
                showPopup1(view);
                return true;
            case R.id.eye:
                gamePanel.create(2);
                showPopup1(view);
                return true;
            case R.id.devil:
                gamePanel.create(3);
                showPopup1(view);
                return true;
            case R.id.eagle:
                gamePanel.create(4);
                showPopup1(view);
                return true;
            case R.id.skeleton:
                gamePanel.create(5);
                showPopup1(view);
                return true;

            case R.id.dwarf:
                gamePanel.create(6);
                showPopup2(view);
                return true;
            case R.id.devil2:
                gamePanel.create(7);
                showPopup2(view);
                return true;
            case R.id.golem:
                gamePanel.create(8);
                showPopup2(view);
                return true;
            case R.id.robot:
                gamePanel.create(9);
                showPopup2(view);
                return true;

            case R.id.gryphon:
                gamePanel.create(10);
                showPopup3(view);
                return true;
            case R.id.fairy:
                gamePanel.create(11);
                showPopup3(view);
                return true;
            case R.id.dark_vador:
                gamePanel.create(12);
                showPopup3(view);
                return true;
            case R.id.blue_dragon:
                gamePanel.create(13);
                showPopup3(view);
                return true;

            case R.id.pikachu:
                gamePanel.create(14);
                showPopup4(view);
                return true;
            case R.id.spider:
                gamePanel.create(15);
                showPopup4(view);
                return true;
            case R.id.unicorn:
                gamePanel.create(16);
                showPopup4(view);
                return true;
            case R.id.wolf:
                gamePanel.create(17);
                showPopup4(view);
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

}
