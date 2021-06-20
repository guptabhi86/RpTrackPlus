
package com.rptrack.plus.DataModel.VehicleWiseResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Device implements Serializable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("DeviceType")
    @Expose
    private Integer deviceType;
    @SerializedName("VehicleType")
    @Expose
    private Integer vehicleType;
    @SerializedName("VehicleNo")
    @Expose
    private String vehicleNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

}
