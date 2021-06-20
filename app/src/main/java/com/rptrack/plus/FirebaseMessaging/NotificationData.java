package com.rptrack.plus.FirebaseMessaging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationData implements Serializable {
    @SerializedName("deviceId")
    @Expose
    private int deviceId;

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("alertType")
    private int alertType;

    @SerializedName("calling")
    @Expose
    private boolean calling;

    @SerializedName("isVoice")
    @Expose
    private boolean isVoice;

    @SerializedName("vehicleNo")
    @Expose
    private String vehicleNo;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("duration")
    @Expose
    private int duration;

    public NotificationData(int deviceId, String body, String title, boolean calling, boolean isVoice, String vehicleNo, String image, String date, int duration, int alertType) {
        this.deviceId = deviceId;
        this.body = body;
        this.title = title;
        this.calling = calling;
        this.isVoice = isVoice;
        this.vehicleNo = vehicleNo;
        this.image = image;
        this.date = date;
        this.duration = duration;
        this.alertType=alertType;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public boolean isCalling() {
        return calling;
    }

    public boolean isVoice() {
        return isVoice;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    public int getAlertType() {
        return alertType;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


