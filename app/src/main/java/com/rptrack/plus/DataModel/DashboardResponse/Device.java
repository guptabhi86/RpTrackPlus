
package com.rptrack.plus.DataModel.DashboardResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.utilities.ApplicationDate;
import com.rptrack.plus.utilities.CommonUtils;


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

    @SerializedName("DeviceUId")
    @Expose
    private String uuid;

    @SerializedName("BLESupport")
    @Expose
    private int bleSupport;
    @SerializedName("DeviceImei")
    @Expose
    private String deviceIme;

    @SerializedName("InstallationDate")
    @Expose
    private String installationDate;

    public String getDeviceIme() {
        return deviceIme;
    }
    @SerializedName("NextRechargeDate")
    @Expose
    private String nextRechargeDate;

    public ApplicationDate getNextRechargeDate() {
        return CommonUtils.isValidDate(nextRechargeDate);
    }

    public ApplicationDate getInstallationDate() {
        return CommonUtils.isValidDate(installationDate);
    }

    public int getBleSupport() {
        return bleSupport;
    }

    public Integer getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
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
