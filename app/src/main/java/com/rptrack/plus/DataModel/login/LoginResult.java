package com.rptrack.plus.DataModel.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResult implements Serializable {
    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Data")
    private String token;

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
