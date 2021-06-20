package com.rptrack.plus.module.reports;

import android.content.Context;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.HistoryReplyResponse.HistoryReplyRequest;
import com.rptrack.plus.DataModel.StoppageResponse.StoppageResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.utilities.CommonUtils;


public class ResultStoppage extends Result {
    public ResultStoppage(Context _context, Object _object) {
        super(_context, _object);
    }

    @Override
    public void Process() {
        InputData inputData=(InputData)_object;
        StoppageResponse(inputData.getId(),inputData.getFromTime(),inputData.getToTime());

    }



    APIUtility apiUtility = ApplicationActivity.getApiUtility();
    void StoppageResponse(int deviceId, String fromTime, String toTime) {
        HistoryReplyRequest historyReplyRequest = new HistoryReplyRequest(deviceId, fromTime, toTime);
        apiUtility.StoppageRequest(_context, true, historyReplyRequest, new APIUtility.APIResponseListener<StoppageResponse>() {
            @Override
            public void onReceiveResponse(StoppageResponse response) {
               /* if (response.getResult().getData().getPoints().size() > 0) {
                    Intent intent = new Intent(_context, DistanceActivity.class);
                    intent.putExtra("title","Stoppage Report");
                    intent.putExtra("requestCode",12524);
                    intent.putExtra("distanceActivity", response);
                    _context.startActivity(intent);
                } else {
                    CommonUtils.alert(_context, "No Data Found");
                }*/
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
