
package com.rptrack.plus.DataModel.VehicleWiseResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.DataModel.DashboardResponse.Datum;

import java.io.Serializable;

public class Result implements Serializable {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private Datum data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Datum getData() {
        return data;
    }

    public void setData(Datum data) {
        this.data = data;
    }

}
