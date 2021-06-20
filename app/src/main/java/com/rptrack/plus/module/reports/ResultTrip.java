package com.rptrack.plus.module.reports;

import android.content.Context;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.HistoryReplyResponse.HistoryReplyRequest;
import com.rptrack.plus.DataModel.TripReport.TripReportResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.utilities.CommonUtils;


public class ResultTrip extends Result {
    public ResultTrip(Context _context, Object _object) {
        super(_context, _object);
    }

    @Override
    public void Process() {
        InputData inputData=(InputData)_object;
        TripResultResponse(inputData.getId(),inputData.getFromTime(),inputData.getToTime());
    }

    APIUtility apiUtility = ApplicationActivity.getApiUtility();
    void TripResultResponse(int deviceId, String fromTime, String toTime) {
        HistoryReplyRequest historyReplyRequest = new HistoryReplyRequest(deviceId, fromTime, toTime);
        apiUtility.TripReportRequest(_context, true, historyReplyRequest, new APIUtility.APIResponseListener<TripReportResponse>() {
            @Override
            public void onReceiveResponse(TripReportResponse response) {
//                if (response.getResult().getData().getPoints().size() > 0) {
//                    Intent intent = new Intent(_context, DistanceActivity.class);
//                    intent.putExtra("title","Trip Report");
//                    intent.putExtra("requestCode",12520);
//                    intent.putExtra("distanceActivity", response);
//                    _context.startActivity(intent);
//                } else {
//                    CommonUtils.alert(_context, "No Data Found");
//                }
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
