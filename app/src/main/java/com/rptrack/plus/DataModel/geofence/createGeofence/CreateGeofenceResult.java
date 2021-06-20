package com.rptrack.plus.DataModel.geofence.createGeofence;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/17/2021 5:51 PM.
 */
public class CreateGeofenceResult implements Serializable {
    @SerializedName("Message")
    private String Message;

    @SerializedName("Data")
    private String stringData;

    public String getMessage() {
        return Message;
    }

    public String getStringData() {
        return stringData;
    }
}
