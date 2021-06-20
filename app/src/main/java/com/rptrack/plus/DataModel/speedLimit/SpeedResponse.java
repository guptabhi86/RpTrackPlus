package com.rptrack.plus.DataModel.speedLimit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.DataModel.BaseResponse;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/25/2021 12:17 AM.
 */
public class SpeedResponse extends BaseResponse implements Serializable {

    public SpeedResponse(Integer statusCode, String responseMessage) {
        super(statusCode, responseMessage);
    }

    @SerializedName("Result")@Expose
    private SpeedResult speedResult;

    public SpeedResult getSpeedResult() {
        return speedResult;
    }
}
