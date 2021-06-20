package com.rptrack.plus.DataModel.geofence.createGeofence;

import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.DataModel.BaseResponse;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/17/2021 5:49 PM.
 */
public class CreateGeofenceResponse extends BaseResponse implements Serializable {
    public CreateGeofenceResponse(Integer statusCode, String responseMessage) {
        super(statusCode, responseMessage);
    }
    @SerializedName("Result")
    private CreateGeofenceResult geofenceResult;

    public CreateGeofenceResult getGeofenceResult() {
        return geofenceResult;
    }


}
