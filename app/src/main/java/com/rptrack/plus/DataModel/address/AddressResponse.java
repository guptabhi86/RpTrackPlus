package com.rptrack.plus.DataModel.address;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressResponse implements Serializable {
    @SerializedName("add")
    private String address;

    public String getAddress() {
        return address;
    }
}
