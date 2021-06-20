package com.rptrack.plus.DataModel.geofence;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.DataModel.BaseResponse;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/17/2021 3:11 PM.
 */
public class GeofenceListBaseResponse extends BaseResponse implements Serializable {


    @SerializedName("Result")
    @Expose
    private GeofenceResult result;
    @SerializedName("Error")
    @Expose
    private Object error;

    public GeofenceResult getResult() {
        return result;
    }

    public Object getError() {
        return error;
    }

    public GeofenceListBaseResponse(Integer statusCode, String responseMessage) {
        super(statusCode, responseMessage);
    }
}
