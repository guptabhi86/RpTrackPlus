package com.rptrack.plus.DataModel.geofence;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/17/2021 3:16 PM.
 */
public class GeofenceDatum implements Serializable {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("GeomType")
    @Expose
    private Integer geomType;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Points")
    @Expose
    private List<GeofencePoints> points = null;
    @SerializedName("Radius")
    @Expose
    private Double radius;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getGeomType() {
        return geomType;
    }

    public void setGeomType(Integer geomType) {
        this.geomType = geomType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeofencePoints> getPoints() {
        return points;
    }

    public void setPoints(List<GeofencePoints> points) {
        this.points = points;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

}
