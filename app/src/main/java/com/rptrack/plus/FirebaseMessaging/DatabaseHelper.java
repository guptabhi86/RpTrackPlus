package com.rptrack.plus.FirebaseMessaging;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "go_track_db";
    private GPSIndiaData gpsIndiaData;
    private Context mContext;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GPSIndiaData.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion==1 && newVersion==2){
            mContext.deleteDatabase("go_track_db");
            onCreate(db);
        }else {
            db.execSQL("DROP TABLE IF EXISTS " + GPSIndiaData.TABLE_NAME);
            onCreate(db);
        }


    }

    public long insertNote(int deviceId, String body, String title, String calling, String isVoice, String vehicleNo, String image, String date, int duration, String currentTime, int isDate, int alertType)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GPSIndiaData.DEVICE_ID, deviceId);
        values.put(GPSIndiaData.BODY, body);
        values.put(GPSIndiaData.TITLE, title);
        values.put(GPSIndiaData.CALLING, calling);
        values.put(GPSIndiaData.ISVOICE, isVoice);
        values.put(GPSIndiaData.VEHICLENO, vehicleNo);
        values.put(GPSIndiaData.IMAGE, image);
        values.put(GPSIndiaData.DATE, date);
        values.put(GPSIndiaData.DURATION, duration);
        values.put(GPSIndiaData.CURRENT_TIME, currentTime);
        values.put(GPSIndiaData.ISDATE, isDate);
        values.put(GPSIndiaData.NOTIFICATION_TYPE, alertType);
        long id = db.insert(GPSIndiaData.TABLE_NAME, null, values);

        db.close();
        return id;
    }


    public List<GPSIndiaData> getAllNotifications() {
        List<GPSIndiaData> GPSIndiaDataList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + GPSIndiaData.TABLE_NAME + " ORDER BY " + GPSIndiaData.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                GPSIndiaData GPSIndiaData = new GPSIndiaData();
                GPSIndiaData.setPrimaryKey(cursor.getInt(cursor.getColumnIndex(GPSIndiaData.COLUMN_ID)));
                GPSIndiaData.setDeviceId(cursor.getInt(cursor.getColumnIndex(GPSIndiaData.DEVICE_ID)));
                GPSIndiaData.setBody(cursor.getString(cursor.getColumnIndex(GPSIndiaData.BODY)));
                GPSIndiaData.setTitle(cursor.getString(cursor.getColumnIndex(GPSIndiaData.TITLE)));
                GPSIndiaData.setCalling(cursor.getString(cursor.getColumnIndex(GPSIndiaData.CALLING)));
                GPSIndiaData.setIsVoice(cursor.getString(cursor.getColumnIndex(GPSIndiaData.ISVOICE)));
                GPSIndiaData.setVehicleNo(cursor.getString(cursor.getColumnIndex(GPSIndiaData.VEHICLENO)));
                GPSIndiaData.setImage(cursor.getString(cursor.getColumnIndex(GPSIndiaData.IMAGE)));
                GPSIndiaData.setDate(cursor.getString(cursor.getColumnIndex(GPSIndiaData.DATE)));
                GPSIndiaData.setDuration(cursor.getInt(cursor.getColumnIndex(GPSIndiaData.DURATION)));
                GPSIndiaData.setCurrentTime(cursor.getString(cursor.getColumnIndex(GPSIndiaData.CURRENT_TIME)));
                GPSIndiaData.setIsDate(cursor.getInt(cursor.getColumnIndex(GPSIndiaData.ISDATE)));
                GPSIndiaData.setNotificationType(cursor.getInt(cursor.getColumnIndex(GPSIndiaData.NOTIFICATION_TYPE)));
                GPSIndiaDataList.add(GPSIndiaData);
            } while (cursor.moveToNext());
        }

        db.close();

        return GPSIndiaDataList;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + GPSIndiaData.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public  int getVehicleNumber(String vehicleNum){
        String countQuery = "SELECT * FROM "+ GPSIndiaData.TABLE_NAME +" WHERE "+ GPSIndiaData.VEHICLENO+"='"+ vehicleNum + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        return count;
    }


    public int updateOneData(int primarykey, int deviceId, String body, String title, String calling,
                             String image, int duration, String notificationDate, String currentTime, int isDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GPSIndiaData.DEVICE_ID, deviceId);
        values.put(GPSIndiaData.BODY, body);
        values.put(GPSIndiaData.TITLE, title);
        values.put(GPSIndiaData.CALLING, calling);
        values.put(GPSIndiaData.ISVOICE, calling);
        values.put(GPSIndiaData.VEHICLENO, calling);
        values.put(GPSIndiaData.IMAGE, image);
        values.put(GPSIndiaData.DATE, duration);
        values.put(GPSIndiaData.DURATION, notificationDate);
        values.put(GPSIndiaData.CURRENT_TIME, currentTime);
        values.put(GPSIndiaData.ISDATE, isDate);
        return db.update(GPSIndiaData.TABLE_NAME, values, GPSIndiaData.COLUMN_ID + " = ?",
                new String[]{String.valueOf(primarykey)});
    }

    public void deleteAllRow()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + GPSIndiaData.TABLE_NAME );
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name="+"'"+ GPSIndiaData.TABLE_NAME+"'");
        db.close();
    }

    public void deleteRow(int primarykey)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + GPSIndiaData.TABLE_NAME + " WHERE " + GPSIndiaData.COLUMN_ID+" = "+primarykey);
        db.close();
    }

    public GPSIndiaData getNote(long primarykey) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GPSIndiaData.TABLE_NAME,
                new String[]{GPSIndiaData.COLUMN_ID,
                        GPSIndiaData.DEVICE_ID,
                        GPSIndiaData.BODY,
                        GPSIndiaData.TITLE,
                        GPSIndiaData.CALLING,
                        GPSIndiaData.ISVOICE,
                        GPSIndiaData.VEHICLENO,
                        GPSIndiaData.IMAGE,
                        GPSIndiaData.DATE,
                        GPSIndiaData.DURATION,
                        GPSIndiaData.CURRENT_TIME,
                        GPSIndiaData.ISDATE
                },
                GPSIndiaData.COLUMN_ID + "=?",
                new String[]{String.valueOf(primarykey)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        assert cursor != null;

        gpsIndiaData = new GPSIndiaData(
                cursor.getInt(cursor.getColumnIndex(GPSIndiaData.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(GPSIndiaData.DEVICE_ID)),
                cursor.getString(cursor.getColumnIndex(GPSIndiaData.BODY)),
                cursor.getString(cursor.getColumnIndex(GPSIndiaData.TITLE)),
                cursor.getString(cursor.getColumnIndex(GPSIndiaData.CALLING)),
                cursor.getString(cursor.getColumnIndex(GPSIndiaData.ISVOICE)),
                cursor.getString(cursor.getColumnIndex(GPSIndiaData.VEHICLENO)),
                cursor.getString(cursor.getColumnIndex(GPSIndiaData.IMAGE)),
                cursor.getString(cursor.getColumnIndex(GPSIndiaData.DATE)),
                cursor.getInt(cursor.getColumnIndex(GPSIndiaData.DURATION)),
                cursor.getString(cursor.getColumnIndex(GPSIndiaData.CURRENT_TIME)),
                cursor.getInt(cursor.getColumnIndex(GPSIndiaData.ISDATE)),
                cursor.getInt(cursor.getColumnIndex(GPSIndiaData.NOTIFICATION_TYPE)));

        cursor.close();

        return gpsIndiaData;
    }

    public List<GPSIndiaData> getVehicleByDeviceId(int deviceId) {

        List<GPSIndiaData> GPSIndiaDataList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + GPSIndiaData.TABLE_NAME + " WHERE "+ GPSIndiaData.DEVICE_ID+" = "+deviceId+" ORDER BY " + GPSIndiaData.COLUMN_ID + " DESC";
        Log.d("SELECTQUERY",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                GPSIndiaData GPSIndiaData = new GPSIndiaData();
                GPSIndiaData.setPrimaryKey(cursor.getInt(cursor.getColumnIndex(GPSIndiaData.COLUMN_ID)));
                GPSIndiaData.setDeviceId(cursor.getInt(cursor.getColumnIndex(GPSIndiaData.DEVICE_ID)));
                GPSIndiaData.setBody(cursor.getString(cursor.getColumnIndex(GPSIndiaData.BODY)));
                GPSIndiaData.setTitle(cursor.getString(cursor.getColumnIndex(GPSIndiaData.TITLE)));
                GPSIndiaData.setCalling(cursor.getString(cursor.getColumnIndex(GPSIndiaData.CALLING)));
                GPSIndiaData.setIsVoice(cursor.getString(cursor.getColumnIndex(GPSIndiaData.ISVOICE)));
                GPSIndiaData.setVehicleNo(cursor.getString(cursor.getColumnIndex(GPSIndiaData.VEHICLENO)));
                GPSIndiaData.setImage(cursor.getString(cursor.getColumnIndex(GPSIndiaData.IMAGE)));
                GPSIndiaData.setDate(cursor.getString(cursor.getColumnIndex(GPSIndiaData.DATE)));
                GPSIndiaData.setDuration(cursor.getInt(cursor.getColumnIndex(GPSIndiaData.DURATION)));
                GPSIndiaData.setCurrentTime(cursor.getString(cursor.getColumnIndex(GPSIndiaData.CURRENT_TIME)));
                GPSIndiaData.setIsDate(cursor.getInt(cursor.getColumnIndex(GPSIndiaData.ISDATE)));
                GPSIndiaData.setNotificationType(cursor.getInt(cursor.getColumnIndex(GPSIndiaData.NOTIFICATION_TYPE)));
                GPSIndiaDataList.add(GPSIndiaData);
            } while (cursor.moveToNext());
        }

        db.close();

        return GPSIndiaDataList;
    }


}
