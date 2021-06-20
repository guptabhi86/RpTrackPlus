
package com.rptrack.plus.DataModel.VehicleWiseResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Peripherial implements Serializable {

    @SerializedName("ACC")
    @Expose
    private Object aCC;
    @SerializedName("Door")
    @Expose
    private Object door;
    @SerializedName("AC")
    @Expose
    private Object aC;
    @SerializedName("Fuel")
    @Expose
    private Object fuel;

    public Object getACC() {
        return aCC;
    }

    public void setACC(Object aCC) {
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
