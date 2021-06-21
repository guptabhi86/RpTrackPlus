package com.rptrack.plus.module.reports;

import android.content.Context;

import androidx.annotation.Nullable;

import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.module.commands.RelayOption;


public class VehicleFactory {

    public VehicleOption CreateBuilderObject(Context context, int id, IResult result, @Nullable Datum dataUbject, @Nullable int deviceId) {
        if (id == 0) {
            // History Replay
            return new CustomDatePicker(context, result, id, false, deviceId);
        } else if (id == 1) {
            // Relay Option
            return new RelayOption(context, result, dataUbject, id, false);
        } else if (id == 2) {
            // Relay
            return new CustomDatePicker(context, result, id, false, deviceId);
            //  return new CustomDatePicker(context, result, dataUbject, id,false);
        } else if (id == 3) {
            // Distance
            //  return new CustomDatePicker(context, result, dataUbject, id,true);
            return new CustomDatePicker(context, result, id, false, deviceId);
        } else if (id == 4) {
            // Alert
            // return new AlertOption(context, result, dataUbject, id,false);
            return new CustomDatePicker(context, result, id, false, deviceId);
        } else if (id == 5) {
            // Stoppage
            //return new CustomDatePicker(context, result, dataUbject, id,false);
            return new CustomDatePicker(context, result, id, false, deviceId);
        } else if (id == 6) {
            // Idle
            //return new CustomDatePicker(context, result, dataUbject, id,false);
            return new CustomDatePicker(context, result, id, false, deviceId);
        } else {
            // Share
            //return new ShareOption(context, result, dataUbject, id,false);
            return new CustomDatePicker(context, result, id, false, deviceId);
        }
    }
}
