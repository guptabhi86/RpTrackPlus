package com.rptrack.plus.alert.alerts;

import android.content.Context;

import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.R;
import com.rptrack.plus.alert.AlertBasic;
import com.rptrack.plus.alert.AlertType;

public class AlertTypeParking extends AlertBasic {
    public AlertTypeParking(Context context) {
        super(context);
    }

    @Override
    public AlertType getAlertType() {
        return AlertType.PARKING;
    }

    @Override
    public String getMessage(AlertDatum alertObj) {
        return "Vehicle Moved (Parking Alert)";
    }

    @Override
    public int getAlertIcon(AlertDatum alertObj) {
        return R.drawable.rp_alert_parking;
    }

    @Override
    public boolean showAlert() {
        return true;
    }
}
