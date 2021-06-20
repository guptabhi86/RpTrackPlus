package com.rptrack.plus.DataModel.speedLimit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/25/2021 12:16 AM.
 */
public class SpeedRequest implements Serializable {

    @SerializedName("OverSpeedLimit")
    private  int OverSpeedLimit;

    private int deviceId;

    public SpeedRequest(int overSpeedLimit) {
        OverSpeedLimit = overSpeedLimit;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}
