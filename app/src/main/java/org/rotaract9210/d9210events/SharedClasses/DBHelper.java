package org.rotaract9210.d9210events.SharedClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Leo on 9/8/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db143.db";
    public static final String PHOTOS_TABLE_NAME = "photos";
    public static final String PHOTOS_COLUMN_ID = "id";
    public static final String PHOTOS_COLUMN_PHOTO_NAME = "name";
    public static final String PHOTOS_COLUMN_EVENT_NAME = "event";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE events (id integer primary key, event_name text, event_date text, event_program text, event_time, event_details text, event_about text, event_cost text, event_location text, event_picture text)");
        db.execSQL("CREATE TABLE speakers (id integer primary key, name text, profession text, bio text, picture text, event text)");
        //db.execSQL("CREATE TABLE events_speakers (id integer primary key, event_id text, speaker_id text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS events");
        db.execSQL("DROP TABLE IF EXISTS speakers");
        //db.execSQL("DROP TABLE IF EXISTS events_speakers");
        onCreate(db);
    }

    public boolean insertEvent(String name, String date, String program, String time, String details, String about, String cost, String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("event_name", name);
        contentValues.put("event_program", program);
        contentValues.put("event_date", date);
        contentValues.put("event_time", time);
        contentValues.put("event_details", details);
        contentValues.put("event_about", about);
        contentValues.put("event_cost", cost);
        contentValues.put("event_location", location);
        db.insert("events", null, contentValues);
        return true;
    }

    public boolean insertSpeaker(String name,String profession,String bio,String picture,String event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("speaker_name",name);
        contentValues.put("profession",profession);
        contentValues.put("bio",bio);
        contentValues.put("picture",picture);
        contentValues.put("event",event);
        db.insert("speakers",null,contentValues);
        return true;
    }

    public Cursor getSpeakers(String event){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM speakers WHERE event= "+event,null);
        return res;
    }
    public Cursor getEvent(String event){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM events where event = "+event,null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,PHOTOS_TABLE_NAME);
        return numRows;
    }

    public boolean updateSpeaker(String name,String profession,String bio,String picture,String event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("profession",profession);
        contentValues.put("bio",bio);
        contentValues.put("picture",picture);
        contentValues.put("event", event);
        db.update("speakers", contentValues, "name = ?", new String[]{name});
        return true;
    }

    public Integer deleteSpeaker(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("speakers","name = ?",new String[]{name});
    }

    public ArrayList<Speakers> getAllSpeakers(String event){
        ArrayList<Speakers>arrayList = new ArrayList<>();
        Speakers speaker;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM speakers WHERE event ="+event,null);
        res.moveToFirst();

        while(res.isAfterLast() == false){

            speaker = new Speakers(
                    res.getString(res.getColumnIndex("name"))
                    ,res.getString(res.getColumnIndex("profession"))
                    ,res.getString(res.getColumnIndex("bio"))
                    ,res.getString(res.getColumnIndex("name"))
                    ,res.getString(res.getColumnIndex("name")));
                    //,res.getString(res.getColumnIndex("name")));
            arrayList.add(speaker);
            res.moveToNext();
        }
        return  arrayList;
    }

    public ArrayList<String> getAllEvents(){
        ArrayList<String>arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM events",null);
        res.moveToFirst();

        while(res.isAfterLast() == false){

            //,res.getString(res.getColumnIndex("name")));
            arrayList.add(res.getString(res.getColumnIndex("name")));
            res.moveToNext();
        }
        return  arrayList;
    }

    public ArrayList<ProgramItem> getEventProgram(String eventID){

        ArrayList<ProgramItem> arraylist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM program WHERE event_id="+getEventID(eventID),null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            arraylist.add(new ProgramItem(
                    res.getString(res.getColumnIndex("day")),
                    res.getString(res.getColumnIndex("date")),
                    res.getString(res.getColumnIndex("time")),
                    res.getString(res.getColumnIndex("session")),
                    res.getString(res.getColumnIndex("facilitator")),
                    res.getString(res.getColumnIndex("SaA"))
            ));
        }
        return arraylist;
    }

    public int getEventID(String eventName){
        int id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT id FROM events WHERE event_name="+eventName,null);
        res.moveToFirst();

        id = res.getInt(0);

        return id;
    }
}
