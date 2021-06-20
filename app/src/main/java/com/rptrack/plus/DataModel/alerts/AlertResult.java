package com.rptrack.plus.DataModel.alerts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ganesh ɐɯɹɐɥs on 4/25/2021 12:52 AM.
 */
public class AlertResult implements Serializable {
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("Data")
    @Expose
    public List<AlertDatum> data = null;


    public String getMessage() {
        return message;
    }

    public List<AlertDatum> getData() {
        return data;
    }
}
