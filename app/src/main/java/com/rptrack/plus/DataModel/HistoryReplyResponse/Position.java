
package com.rptrack.plus.DataModel.HistoryReplyResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.utilities.ApplicationDate;
import com.rptrack.plus.utilities.CommonUtils;
import java.io.Serializable;

public class Position implements Serializable {

    @SerializedName("Timestamp")
    @Expose
    private String timestamp;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Heading")
    @Expose
    private Double heading;
    @SerializedName("Speed")
    @Expose
    private Double speed;
    @SerializedName("DI1")
    @Expose
    private Integer dI1;
    @SerializedName("VehicleStatus")
    @Expose
    private Integer vehicleStatus;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getDI1() {
        return dI1;
    }

    public void setDI1(Integer dI1) {
        this.dI1 = dI1;
    }

    public Integer getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(Integer vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public ApplicationDate getAppDate(){
        return CommonUtils.isValidDate(getTimestamp());
    }


}
