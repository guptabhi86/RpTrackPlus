package com.rptrack.plus.DataModel.error_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ErrorListResponse implements Serializable {

    @SerializedName("propertyName")
    private String propertyName;

    @SerializedName("errorMessage")
    private String errorMessage;

    public String getPropertyName() {
        return propertyName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
