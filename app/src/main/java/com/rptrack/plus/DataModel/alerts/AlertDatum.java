package com.rptrack.plus.DataModel.alerts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 4/25/2021 12:54 AM.
 */
public class AlertDatum implements Serializable {
    @SerializedName("DeviceId")
    @Expose
    public Integer deviceId;
    @SerializedName("VehicleNo")
    @Expose
    public String vehicleNo;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("Timestamp")
    @Expose
    public String timestamp;
    @SerializedName("AlertTye")
    @Expose
    public Integer alertTye;
    @SerializedName("Latitude")
    @Expose
    public Double latitude;
    @SerializedName("Longitude")
    @Expose
    public Double longitude;
    @SerializedName("Speed")
    @Expose
    public Integer speed;
    @SerializedName("Heading")
    @Expose
    public Double heading;
    @SerializedName("Di1")
    @Expose
    public Integer di1;
    @SerializedName("Di2")
    @Expose
    public Integer di2;
    @SerializedName("Epc")
    @Expose
    public Integer epc;
    @SerializedName("GeofenceId")
    @Expose
    public Integer geofenceId;
    @SerializedName("GeofenceName")
    @Expose
    public Object geofenceName;
    @SerializedName("GeofenceStatus")
    @Expose
    public Integer geofenceStatus;
    @SerializedName("Tampor")
    @Expose
    public Integer tampor;
    @SerializedName("CreationTime")
    @Expose
    public String creationTime;


    public Integer getDeviceId() {
        return deviceId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Integer getAlertTye() {
        return alertTye;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Double getHeading() {
        return heading;
    }

    public Integer getDi1() {
        return di1;
    }

    public Integer getDi2() {
        return di2;
    }

    public Integer getEpc() {
        return epc;
    }

    public Integer getGeofenceId() {
        return geofenceId;
    }

    public Object getGeofenceName() {
        return geofenceName;
    }

    public Integer getGeofenceStatus() {
        return geofenceStatus;
    }

    public Integer getTampor() {
        return tampor;
    }

    public String getCreationTime() {
        return creationTime;
    }
}
