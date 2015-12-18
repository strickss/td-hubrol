package com.example.towerdefense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Brieuc on 27-10-15.
 */
public class HistoryDbAdapter {

    private static final String TAG = HistoryDbAdapter.class.getSimpleName();
    private static final String DATABASE_NAME = "Match_history";
    private static final String DATABASE_TABLE = "history";
    private static final int DATABASE_VERSION = 1;

    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_OPPONENT = "opponent";
    public static final String KEY_DATE_TIME = "history_date_time";
    public static final String KEY_ROWID = "_id";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " ("+KEY_ROWID + " integer primary key autoincrement, " + KEY_TITLE + " text not null, " + KEY_BODY + " text not null, "+ KEY_OPPONENT + " text not null, "+KEY_DATE_TIME + " text not null);";

    private final Context mCtx;

    public HistoryDbAdapter(Context ctx){
        this.mCtx = ctx;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Not used, but you could upgrade the database with ALTER scripts } }
        }
    }

    public HistoryDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDbHelper.close();
    }

    public long createHistory(String title, String body, String opponent, String reminderDateTime){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);
        initialValues.put(KEY_OPPONENT, opponent);
        initialValues.put(KEY_DATE_TIME, reminderDateTime);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteReminder(long rowId){
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllHistory(){
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE, KEY_BODY, KEY_OPPONENT, KEY_DATE_TIME},null,null,null,null,null);
        }

    public Cursor fetchReminder(long rowId) throws SQLException {
        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE, KEY_BODY, KEY_OPPONENT, KEY_DATE_TIME}, KEY_ROWID + "=" + rowId, null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public int statistics(String amount){
        Log.d(TAG, "amount :  " + amount);
        int win_rate = 0;
        Cursor cursor = mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE, KEY_BODY, KEY_OPPONENT, KEY_DATE_TIME},null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                String data = cursor.getString(cursor.getColumnIndex("title"));
                Log.d(TAG, "data : " + data);
                if (data.equals(amount)){
                    win_rate = win_rate + 1;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        Log.d(TAG, "win_rate : " + win_rate);
        return win_rate;
    }

    public boolean updateReminder(long rowId, String title, String body,String opponent, String reminderDateTime){
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_BODY, body);
        args.put(KEY_OPPONENT, opponent);
        args.put(KEY_DATE_TIME, reminderDateTime);
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}