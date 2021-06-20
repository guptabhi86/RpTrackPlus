package com.rptrack.plus.module.commands;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.DataModel.Relay.RelayRequest;
import com.rptrack.plus.DataModel.Relay.RelayResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.module.reports.IResult;
import com.rptrack.plus.module.reports.VehicleOption;
import com.rptrack.plus.utilities.CommonUtils;


public class RelayOption extends VehicleOption {


    APIUtility apiUtility = ApplicationActivity.getApiUtility();

    public RelayOption(Context _context, IResult _result, Datum _datum, int _id, boolean isHideTime) {
        super(_context, _result, _datum, _id, isHideTime);
    }

    @Override
    public void showDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(_context, R.style.AppDialogTheme);
        LayoutInflater layoutInflater = ((Activity) _context).getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_ignition_dialog_layot, null);
        TextView title = (TextView) customView.findViewById(R.id.title);
        Button on_btn = (Button) customView.findViewById(R.id.on_btn);
        Button off_btn = (Button) customView.findViewById(R.id.off_btn);
        Button cancel_btn = (Button)customView.findViewById(R.id.cancel_btn);
        title.setText(_datum.getDevice().getVehicleNo());
        builder.setView(customView);
        final AlertDialog alert = builder.create();
        alert.setCancelable(false);

        on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RELAYRequest("", _datum.getDevice().getId(), 1);
                alert.dismiss();
            }
        });
        off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RELAYRequest("", _datum.getDevice().getId(), 2);
                alert.dismiss();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();


    }


    void RELAYRequest(String fcmToken, int deviceId, int commandType) {
        RelayRequest relayRequest = new RelayRequest(fcmToken, deviceId, commandType);
        apiUtility.RELAYRequest(_context, true, relayRequest, new APIUtility.APIResponseListener<RelayResponse>() {
            @Override
            public void onReceiveResponse(RelayResponse response) {
                CommonUtils.alert(_context, response.getResult().getData());
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
