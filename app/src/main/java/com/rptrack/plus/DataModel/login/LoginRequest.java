
package com.rptrack.plus.DataModel.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("fcmToken")
    private String fcmToken;
    @SerializedName("phoneModel")
    private String phoneModel;
    @SerializedName("phoneType")
    private String phoneType;
    @SerializedName("os")
    private String os;
    @SerializedName("appVersion")
    private String appVersion;


    public LoginRequest(String username, String password, String fcmToken, String phoneModel, String phoneType, String os, String appVersion) {
        this.username = username;
        this.password = password;
        this.fcmToken = fcmToken;
        this.phoneModel = phoneModel;
        this.phoneType = phoneType;
        this.os = os;
        this.appVersion = appVersion;
    }
}
