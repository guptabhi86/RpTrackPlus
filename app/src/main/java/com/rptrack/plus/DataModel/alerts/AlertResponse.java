package com.rptrack.plus.DataModel.alerts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.DataModel.BaseResponse;


/**
 * Created by Ganesh ɐɯɹɐɥs on 4/25/2021 12:33 AM.
 */
public class AlertResponse extends BaseResponse {
    public AlertResponse(Integer statusCode, String responseMessage) {
        super(statusCode, responseMessage);
    }
    @SerializedName("Result")
    @Expose
    public AlertResult result;
    @SerializedName("Error")
    @Expose
    public Object error;

    public AlertResult getResult() {
        return result;
    }

    public Object getError() {
        return error;
    }
}
