package com.example.towerdefense;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class HistoryActivity extends ListActivity {

    private static final int ACTIVITY_CREATE = 0;
    private HistoryDbAdapter mDbHelper;
    private TextView textWinRate;
    private TextView textLossRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mDbHelper = new HistoryDbAdapter(this);
        mDbHelper.open();
        textWinRate = (TextView) findViewById(R.id.winRate);
        textLossRate = (TextView) findViewById(R.id.lossRate);
        fillData();
        registerForContextMenu(getListView());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        //Intent i = new Intent(this, ReminderEditActivity.class);
        //i.putExtra(HistoryDbAdapter.KEY_ROWID, id);
        //startActivityForResult(i, ACTIVITY_CREATE);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.list_menu_item_longpress, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item){
        switch(item.getItemId()) {
            case R.id.menu_insert:
                createReminder();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void createReminder(){
        //Intent i = new Intent(this, ReminderEditActivity.class);
        //startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.menu_delete:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteReminder(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void fillData(){
        Cursor historyCursor = mDbHelper.fetchAllHistory();
        if (historyCursor != null){
            String[] from = new String[]{HistoryDbAdapter.KEY_TITLE, HistoryDbAdapter.KEY_BODY, HistoryDbAdapter.KEY_OPPONENT, HistoryDbAdapter.KEY_DATE_TIME};
            int[] to = new int[]{R.id.text_hist1, R.id.text_hist2, R.id.text_hist3, R.id.text_hist4};
            SimpleCursorAdapter reminders = new SimpleCursorAdapter(this, R.layout.history_row, historyCursor, from, to, 0);
            setListAdapter(reminders);
        }
        int win_rate = mDbHelper.statistics("Victory");
        int loss_rate = mDbHelper.statistics("Defeat");
        textWinRate.setText("Win : " + win_rate);
        textLossRate.setText("Loss : " + loss_rate);
    }

    public void resumeMenu(View v){
        Intent intentBT = new Intent(this, HomeScreenActivity.class);
        //Intent intentBT = new Intent(this, EnsureConnectActivity.class);
        startActivity(intentBT);
    }

}
