package com.rptrack.plus.DataModel.parking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.DataModel.BaseResponse;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/10/2021.
 */
public class ParkingBaseResponse extends BaseResponse {
    public ParkingBaseResponse(Integer statusCode, String responseMessage) {
        super(statusCode, responseMessage);
    }
    @SerializedName("Result")
    @Expose
    private ParkingVehicleResult parkingVehicleResult;

    public ParkingVehicleResult getParkingVehicleResult() {
        return parkingVehicleResult;
    }
}
