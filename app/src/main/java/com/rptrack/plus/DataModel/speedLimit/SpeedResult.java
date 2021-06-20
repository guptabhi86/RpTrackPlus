package com.rptrack.plus.DataModel.speedLimit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by Ganesh ɐɯɹɐɥs on 3/25/2021 8:19 AM.
 */
public class SpeedResult implements Serializable {

    @SerializedName("Message")@Expose
    private String message;

    @SerializedName("Data")@Expose
    private String stringData;

    public String getMessage() {
        return message;
    }

    public String getStringData() {
        return stringData;
    }
}
