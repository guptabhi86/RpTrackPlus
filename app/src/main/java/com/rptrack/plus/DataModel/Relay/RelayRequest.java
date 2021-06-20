package com.rptrack.plus.DataModel.Relay;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RelayRequest implements Serializable {

    @SerializedName("Token")
    private String Token;

    @SerializedName("DeviceId")
    private int deviceid;

    @SerializedName("CommandType")
    private int commandType;

    public RelayRequest(String token, int deviceid, int commandType) {
        Token = token;
        this.deviceid = deviceid;
        this.commandType = commandType;
    }
}
