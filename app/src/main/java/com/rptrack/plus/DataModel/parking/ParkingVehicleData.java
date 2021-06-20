package com.rptrack.plus.DataModel.parking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/10/2021.
 */
public class ParkingVehicleData implements Serializable {
    @SerializedName("EnableParking")
    @Expose
    private int enableParking;

    @SerializedName("Id")
    @Expose
    private int id;


    public int getEnableParking() {
        return enableParking;
    }

    public int getId() {
        return id;
    }
}
