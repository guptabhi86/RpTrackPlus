package com.rptrack.plus.DataModel.parking;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/10/2021.
 */
public class ParkingListenerRequest implements Serializable {

    @SerializedName("DeviceIdPk")
    private int DeviceIdPk;

    @SerializedName("RequestFor")
    private int RequestFor;

    public ParkingListenerRequest(int deviceIdPk, int requestFor) {
        DeviceIdPk = deviceIdPk;
        RequestFor = requestFor;
    }
}
