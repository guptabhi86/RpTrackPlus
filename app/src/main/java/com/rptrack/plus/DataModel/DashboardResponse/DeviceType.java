package com.rptrack.plus.DataModel.DashboardResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeviceType implements Serializable {
    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("Name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
