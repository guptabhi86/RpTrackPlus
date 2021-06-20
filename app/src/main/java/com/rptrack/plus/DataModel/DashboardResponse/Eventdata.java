
package com.rptrack.plus.DataModel.DashboardResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Eventdata implements Serializable {

    @SerializedName("Timestamp")
    @Expose
    private String timestamp;
    @SerializedName("GpsStatus")
    @Expose
    private Integer gpsStatus;
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
    @SerializedName("DO1")
    @Expose
    private Integer dO1;
    @SerializedName("EPC")
    @Expose
    private Integer ePC;
    @SerializedName("IPV")
    @Expose
    private Double iPV;
    @SerializedName("ServerSocket")
    @Expose
    private String serverSocket;

    @SerializedName("DayDistance")
    private String DayDistance;

    @SerializedName("SoftOdometer")
    private String SoftOdometer;

    public String getDayDistance() {
        return DayDistance;
    }

    public String getSoftOdometer() {
        return SoftOdometer;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getGpsStatus() {
        return gpsStatus;
    }

    public void setGpsStatus(Integer gpsStatus) {
        this.gpsStatus = gpsStatus;
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

    public Integer getDO1() {
        return dO1;
    }

    public void setDO1(Integer dO1) {
        this.dO1 = dO1;
    }

    public Integer getEPC() {
        return ePC;
    }

    public void setEPC(Integer ePC) {
        this.ePC = ePC;
    }

    public Double getIPV() {
        return iPV;
    }

    public void setIPV(Double iPV) {
        this.iPV = iPV;
    }

    public String getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(String serverSocket) {
        this.serverSocket = serverSocket;
    }

}
