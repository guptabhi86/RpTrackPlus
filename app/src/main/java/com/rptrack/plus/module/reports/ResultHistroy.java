package com.rptrack.plus.module.reports;

import android.content.Context;
import android.content.Intent;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.HistoryReplyResponse.HistoryReplyRequest;
import com.rptrack.plus.DataModel.HistoryReplyResponse.HistoryReplyResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.module.device.HistoryReplayActivity;
import com.rptrack.plus.utilities.CommonUtils;


public class ResultHistroy extends Result {
    public ResultHistroy(Context _context, Object _object) {
        super(_context, _object);
    }

    @Override
    public void Process() {
        InputData data = (InputData) _object;
        HistroyReplyResponse(data.id, data.fromTime, data.getToTime());
    }
    APIUtility apiUtility = ApplicationActivity.getApiUtility();
    void HistroyReplyResponse(int deviceId, String fromTime, String toTime) {
        HistoryReplyRequest historyReplyRequest = new HistoryReplyRequest(deviceId, fromTime, toTime);
        apiUtility.HistoryReplyRequest(_context, true, historyReplyRequest, new APIUtility.APIResponseListener<HistoryReplyResponse>() {
            @Override
            public void onReceiveResponse(HistoryReplyResponse response) {
                if (response.getResult().getData().getPosition().size() > 0) {
                    Intent intent = new Intent(_context, HistoryReplayActivity.class);
                    intent.putExtra("historyReplay", response);
                    intent.putExtra("vehicleNumber", response.getResult().getData().getVehicle());
                    _context.startActivity(intent);
                } else {
                    CommonUtils.alert(_context, "No Data Found");
                }
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(_context, errorResponse.getErrorMessage().getStringData());
            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(_context, string);
            }
        });
    }
}
