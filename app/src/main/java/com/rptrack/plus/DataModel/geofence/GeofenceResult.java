package com.rptrack.plus.DataModel.geofence;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/17/2021 3:15 PM.
 */
public class GeofenceResult implements Serializable {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<GeofenceDatum> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GeofenceDatum> getData() {
        return data;
    }

    public void setData(List<GeofenceDatum> data) {
        this.data = data;
    }

}
