package com.rptrack.plus.module.reports;

import android.content.Context;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.DistanceReport.DistanceResponse;
import com.rptrack.plus.DataModel.HistoryReplyResponse.HistoryReplyRequest;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.utilities.CommonUtils;


public class ResultDistance extends Result {
    public ResultDistance(Context _context, Object _object) {
        super(_context, _object);
    }

    @Override
    public void Process() {
        InputData inputData=(InputData)_object;
        DistanceResponse(inputData.getId(),inputData.getFromTime(),inputData.getToTime());

    }



    APIUtility apiUtility = ApplicationActivity.getApiUtility();
    void DistanceResponse(int deviceId, String fromTime, String toTime) {
        HistoryReplyRequest historyReplyRequest = new HistoryReplyRequest(deviceId, fromTime, toTime);
        apiUtility.DistanceRequest(_context, true, historyReplyRequest, new APIUtility.APIResponseListener<DistanceResponse>() {
            @Override
            public void onReceiveResponse(DistanceResponse response) {
                if (response.getResult().getData().getDistance().size() > 0) {
                    /*Intent intent = new Intent(_context, DistanceActivity.class);
                    intent.putExtra("title","Distance Report");
                    intent.putExtra("requestCode",12525);
                    intent.putExtra("distanceActivity", response);
                    _context.startActivity(intent);*/
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
