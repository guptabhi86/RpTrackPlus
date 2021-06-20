
package com.rptrack.plus.DataModel.VehicleWiseResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Eventdata implements Serializable {

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
    @SerializedName("DO1")
    @Expose
    private Integer dO1;
    @SerializedName("ServerSocket")
    @Expose
    private String serverSocket;

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

    public Integer getDO1() {
        return dO1;
    }

    public void setDO1(Integer dO1) {
        this.dO1 = dO1;
    }

    public String getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(String serverSocket) {
        this.serverSocket = serverSocket;
    }

}
