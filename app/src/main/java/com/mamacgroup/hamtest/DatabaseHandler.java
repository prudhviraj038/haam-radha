package com.mamacgroup.hamtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    Context context;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "downloads";

    // Contacts table name
    private static final String TABLE_PLAYLIST = "playlist";
    private static final String NEWS_VIEWED = "newslog";


    // Contacts Table Columns names

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NOTIFY = "name_ar";

    private static final String KEY_NAME_FR = "name_fr";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_PID = "pid";

    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

                String CREATE_PLAYLIST_TABLE = "CREATE TABLE " + TABLE_PLAYLIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME +  " TEXT," + KEY_NOTIFY + " TEXT " + ")";
        db.execSQL(CREATE_PLAYLIST_TABLE);

        String CREATE_NEWS_TABLE = "CREATE TABLE " + NEWS_VIEWED + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME +  " TEXT" + ")";
        db.execSQL(CREATE_NEWS_TABLE);


    }

    // Upgrading database


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
        db.execSQL("DROP TABLE IF EXISTS " + NEWS_VIEWED);

        // Create tables again
        onCreate(db);


    }

    void addPlaylist(String id,String name,String notify) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id); // Contact Phone
        values.put(KEY_NAME, name); // Contact Name
        values.put(KEY_NOTIFY, notify);
         // Contact Phone
        // Inserting Row
        db.insert(TABLE_PLAYLIST, null, values);
        db.close(); // Closing database connection

    }

    void updatenotify(String id,String notify) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NOTIFY, notify);
        db.update(TABLE_PLAYLIST, cv, KEY_ID+"="+id, null);
        db.close();
    }


    // Getting single contact




    boolean is_following(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLAYLIST, new String[]{KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if(cursor.getCount()>0){
            cursor.close();
            db.close();
            return true;
        }
        else{
            cursor.close();
            db.close();
            return false;
        }

    }


    boolean is_notification_enabled(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLAYLIST, new String[]{KEY_ID,
                        KEY_NAME}, KEY_ID + "=?" + " and " + KEY_NOTIFY + "=?",
                new String[]{id, "1"}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }


    public void deletePlaylist(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYLIST, KEY_ID + " = ?",
                new String[] { id });
        db.close();
    }

    String  selected_channels(String pid){
        if(!pid.equals(MainActivity.world_id)) {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_PLAYLIST, new String[]{KEY_ID,
                            KEY_NAME}, KEY_NAME + "=?",
                    new String[]{pid}, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();

                if (cursor.getCount() > 0) {
                    String append = "";
                    for (int i = 0; i < cursor.getCount(); i++) {
                       // if( !cursor.getString(cursor.getColumnIndex(KEY_NAME)).equals(MainActivity.live_id) )

                            if (i == 0)
                            append = cursor.getString(cursor.getColumnIndex(KEY_ID));
                        else
                            append = append + "," + cursor.getString(cursor.getColumnIndex(KEY_ID));

                        cursor.moveToNext();
                    }
                    cursor.close();
                    db.close();
                    return append;
                }
                cursor.close();
            }
            db.close();
            return "0";
        }else
            return all_selected_channels(pid);
    }

    String  all_selected_channels(String pid){
        SQLiteDatabase db = this.getReadableDatabase();

        /*Cursor cursor = db.query(TABLE_PLAYLIST, new String[]{KEY_ID,
                        KEY_NAME}, KEY_NAME + "=?",
                new String[]{pid}, null, null, null, null);
        */

        Cursor  cursor = db.rawQuery("select * from "+TABLE_PLAYLIST,null);

        if (cursor != null) {
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {

                String append="";

                for (int i = 0; i <cursor.getCount();i++) {

                    if( !cursor.getString(cursor.getColumnIndex(KEY_NAME)).equals( MainActivity.sports_id) && !cursor.getString(cursor.getColumnIndex(KEY_NAME)).equals(MainActivity.economy_id) && !cursor.getString(cursor.getColumnIndex(KEY_NAME)).equals(MainActivity.live_id) )
                    if(i==0)
                        append=cursor.getString(cursor.getColumnIndex(KEY_ID));
                    else
                        append=append+","+cursor.getString(cursor.getColumnIndex(KEY_ID));

                    cursor.moveToNext();
                }
                if(append.equals("")){
                    cursor.close();
                    db.close();
                    return  "0";
                }

                cursor.close();
                db.close();
                return append;
            }

           cursor.close();
        }
        db.close();
        return "0";
    }

    String  all_selected_channels_new(String pid){
        SQLiteDatabase db = this.getReadableDatabase();

        /*Cursor cursor = db.query(TABLE_PLAYLIST, new String[]{KEY_ID,
                        KEY_NAME}, KEY_NAME + "=?",
                new String[]{pid}, null, null, null, null);
        */

        Cursor  cursor = db.rawQuery("select * from "+TABLE_PLAYLIST,null);

        if (cursor != null) {
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                String append="";
                for (int i = 0; i <cursor.getCount();i++) {

                    if( !cursor.getString(cursor.getColumnIndex(KEY_NAME)).equals(MainActivity.live_id) )
                        if(i==0)
                            append=cursor.getString(cursor.getColumnIndex(KEY_ID));
                        else
                            append=append+","+cursor.getString(cursor.getColumnIndex(KEY_ID));

                    cursor.moveToNext();
                }
                cursor.close();
                db.close();
                return append;
            }

            cursor.close();
        }
        db.close();
        return "0";
    }


    String  notification_enabled_chanels(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLAYLIST, new String[]{KEY_ID,
                        KEY_NAME}, KEY_NOTIFY + "=?",
                new String[]{"1"}, null, null, null, null);


        if (cursor != null) {
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                String append="";
                for (int i = 0; i <cursor.getCount();i++) {
                    if( !cursor.getString(cursor.getColumnIndex(KEY_NAME)).equals(MainActivity.live_id) )
                    if(i==0)
                        append=cursor.getString(cursor.getColumnIndex(KEY_ID));
                    else
                        append=append+","+cursor.getString(cursor.getColumnIndex(KEY_ID));

                    cursor.moveToNext();
                }
                cursor.close();
                db.close();
                return append;
            }
            cursor.close();
        }
        db.close();
        return "0";
    }


    //for news table


    void addnews(String id,String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id); // Contact Phone
        values.put(KEY_NAME, name); // Contact Name
        // Contact Phone
        // Inserting Row
        db.insert(NEWS_VIEWED, null, values);
        db.close(); // Closing database connection
    }



    String  news_opened(String pid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NEWS_VIEWED, new String[]{KEY_ID,
                        KEY_NAME}, KEY_NAME + "=?",
                new String[]{pid}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                String append="";
                for (int i = 0; i <cursor.getCount();i++) {
                    if(i==0)
                        append=cursor.getString(cursor.getColumnIndex(KEY_ID));
                    else
                        append=append+","+cursor.getString(cursor.getColumnIndex(KEY_ID));

                    cursor.moveToNext();
                }
                cursor.close();
                db.close();
                return append;
            }
            cursor.close();
        }
        db.close();
        return "0";
    }


    public void select_all_channels(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NOTIFY, "1");
        db.update(TABLE_PLAYLIST, cv, KEY_NOTIFY+"=0", null);
        db.close();
    }

    public void deselect_all_channels(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NOTIFY, "0");
        db.update(TABLE_PLAYLIST, cv, KEY_NOTIFY+"=1", null);
        db.close();
    }




}
