
package com.rptrack.plus.DataModel.VehicleWiseResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ResultCode")
    @Expose
    private Integer resultCode;
    @SerializedName("ResultMessage")
    @Expose
    private Object resultMessage;
    @SerializedName("Device")
    @Expose
    private Device device;
    @SerializedName("Eventdata")
    @Expose
    private Eventdata eventdata;
    @SerializedName("Peripherial")
    @Expose
    private Peripherial peripherial;
    @SerializedName("Battery")
    @Expose
    private Battery battery;
    @SerializedName("StatusDuration")
    @Expose
    private String statusDuration;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("SubStatus")
    @Expose
    private Integer subStatus;

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public Object getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(Object resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Eventdata getEventdata() {
        return eventdata;
    }

    public void setEventdata(Eventdata eventdata) {
        this.eventdata = eventdata;
    }

    public Peripherial getPeripherial() {
        return peripherial;
    }

    public void setPeripherial(Peripherial peripherial) {
        this.peripherial = peripherial;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public String getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(String statusDuration) {
        this.statusDuration = statusDuration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

}
