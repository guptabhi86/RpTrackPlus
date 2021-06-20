package com.rptrack.plus.DataModel.error_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ErrorResponse implements Serializable {


    @SerializedName("Error")
    private ErrorObj errorMessage;

    public ErrorObj getErrorMessage() {
        return errorMessage;
    }
}
