
package com.rptrack.plus.DataModel.StoppageResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoppageResponse implements Serializable {

    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Result")
    @Expose
    private StoppageResult result;
    @SerializedName("Error")
    @Expose
    private Object error;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public StoppageResult getResult() {
        return result;
    }

    public void setResult(StoppageResult result) {
        this.result = result;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

}
