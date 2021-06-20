package com.rptrack.plus.FirebaseMessaging;

import java.io.Serializable;

public class GPSIndiaData implements Serializable {
    public static final String TABLE_NAME = "notification";
    public static final String COLUMN_ID = "primaryKey";
    public static final String DEVICE_ID = "deviceId";
    public static final String BODY = "body";
    public static final String TITLE = "title";
    public static final String CALLING = "calling";
    public static final String ISVOICE = "isVoice";
    public static final String VEHICLENO = "vehicleNo";
    public static final String IMAGE = "image";
    public static final String DATE = "date";
    public static final String DURATION = "duration";
    public static final String CURRENT_TIME = "currentTime";
    public static final String ISDATE = "isDate";
    public static final String NOTIFICATION_TYPE="noti_type";

    private int primaryKey;
    private int deviceId;
    private String body;
    private String title;
    private String calling;
    private String isVoice;
    private String vehicleNo;
    private String image;
    private String date;
    private int duration;
    private String currentTime;
    private int isDate;
    private int notificationType;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DEVICE_ID + " INTEGER,"
                    + BODY + " TEXT,"
                    + TITLE + " TEXT,"
                    + CALLING + " TEXT,"
                    + ISVOICE + " TEXT,"
                    + VEHICLENO + " TEXT,"
                    + IMAGE + " TEXT,"
                    + DATE + " TEXT,"
                    + DURATION + " INTEGER,"
                    + CURRENT_TIME + " TEXT,"
                    + ISDATE + " INTEGER,"
                    + NOTIFICATION_TYPE + " INTEGER"
                    + ")";


    public GPSIndiaData(int primaryKey, int deviceId, String body, String title, String calling, String isVoice, String vehicleNo, String image, String date, int duration, String currentTime, int isDate, int notificationType) {
        this.primaryKey = primaryKey;
        this.deviceId = deviceId;
        this.body = body;
        this.title = title;
        this.calling = calling;
        this.isVoice = isVoice;
        this.vehicleNo = vehicleNo;
        this.image = image;
        this.date = date;
        this.duration = duration;
        this.isDate = isDate;
        this.currentTime = currentTime;
        this.notificationType=notificationType;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCalling() {
        return calling;
    }

    public void setCalling(String calling) {
        this.calling = calling;
    }

    public String getIsVoice() {
        return isVoice;
    }

    public void setIsVoice(String isVoice) {
        this.isVoice = isVoice;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIsDate(int isDate) {
        this.isDate = isDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public int getIsDate() {
        return isDate;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public GPSIndiaData() {
    }
}
