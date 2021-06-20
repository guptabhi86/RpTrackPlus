package com.rptrack.plus.DataModel.error_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ErrorObj implements Serializable {

    @SerializedName("Message")
    private String stringMessage;

    @SerializedName("Data")
    private String stringData;

    public String getStringMessage() {
        return stringMessage;
    }

    public String getStringData() {
        return stringData;
    }
}
