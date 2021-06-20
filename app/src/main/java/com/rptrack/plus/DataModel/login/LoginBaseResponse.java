package com.rptrack.plus.DataModel.login;

import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.DataModel.BaseResponse;
import com.rptrack.plus.DataModel.error_response.ErrorObj;

import java.io.Serializable;

public class LoginBaseResponse extends BaseResponse implements Serializable {

    public LoginBaseResponse(Integer statusCode, String responseMessage) {
        super(statusCode, responseMessage);
    }

    @SerializedName("Result")
    private LoginResult result=null;
    @SerializedName("Error")
    private ErrorObj error=null;

    public LoginResult getResult() {
        return result;
    }

    public ErrorObj getError() {
        return error;
    }
}
