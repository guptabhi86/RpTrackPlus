package com.rptrack.plus.utilities;

import com.rptrack.plus.FirebaseMessaging.NotificationData;
import com.rptrack.plus.bluetooth.ConnectedDeviceResponse;

import java.util.ArrayList;

public class MySingleton {
    private static MySingleton instance;


    ArrayList<ConnectedDeviceResponse> list;
    NotificationData notificationData;


    public static void clearInstance(){
        MySingleton.setInstance(null);
    }


    public static synchronized MySingleton getInstance(){
        return instance;
    }

    public static void setInstance(MySingleton instance) {
        MySingleton.instance = instance;
    }

    public ArrayList<ConnectedDeviceResponse> getList() {
        return list;
    }

    public void setList(ArrayList<ConnectedDeviceResponse> list) {
        this.list = list;
    }

    public MySingleton(){

    }

    public NotificationData getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(NotificationData notificationData) {
        this.notificationData = notificationData;
    }


}
