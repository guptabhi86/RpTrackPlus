
package com.rptrack.plus.DataModel.DashboardResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Peripherial implements Serializable {

    @SerializedName("ACC")
    @Expose
    private Integer aCC;
    @SerializedName("Door")
    @Expose
    private Object door;
    @SerializedName("AC")
    @Expose
    private Object aC;
    @SerializedName("Fuel")
    @Expose
    private Object fuel;

    @SerializedName("OverSpeedLimit")
    @Expose
    private int overSpeedLimit=0;

    @SerializedName("Parking")
    @Expose
    private Parking parking;

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public int getOverSpeedLimit() {
        return overSpeedLimit;
    }

    public Integer getACC() {
        return aCC;
    }

    public void setACC(Integer aCC) {
        this.aCC = aCC;
    }

    public Object getDoor() {
        return door;
    }

    public void setDoor(Object door) {
        this.door = door;
    }

    public Object getAC() {
        return aC;
    }

    public void setAC(Object aC) {
        this.aC = aC;
    }

    public Object getFuel() {
        return fuel;
    }

    public void setFuel(Object fuel) {
        this.fuel = fuel;
    }

}
