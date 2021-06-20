package com.rptrack.plus.DataModel.parking;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/10/2021.
 */
public class ParkingVehicleResult implements Serializable {
    @SerializedName("Message")
    private String message;


    @SerializedName("Data")
    private ParkingVehicleData data;

    public String getMessage() {
        return message;
    }

    public ParkingVehicleData getData() {
        return data;
    }
}
