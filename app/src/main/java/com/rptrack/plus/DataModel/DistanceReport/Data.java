
package com.rptrack.plus.DataModel.DistanceReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data  implements Serializable {

    @SerializedName("Device")
    @Expose
    private Device device;
    @SerializedName("FromTime")
    @Expose
    private String fromTime;
    @SerializedName("ToTime")
    @Expose
    private String toTime;
    @SerializedName("Distance")
    @Expose
    private List<Distance> distance = null;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public List<Distance> getDistance() {
        return distance;
    }

    public void setDistance(List<Distance> distance) {
        this.distance = distance;
    }

}
