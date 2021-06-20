package com.rptrack.plus.alert.alerts;

import android.content.Context;

import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.R;
import com.rptrack.plus.alert.AlertBasic;
import com.rptrack.plus.alert.AlertType;

public class AlertTypeImmobilizer extends AlertBasic {
    public AlertTypeImmobilizer(Context context) {
        super(context);
    }

    @Override
    public AlertType getAlertType() {
        return AlertType.IMMOBILIZER;
    }

    @Override
    public String getMessage(AlertDatum alertObj) {
        if (alertObj.getGeofenceStatus() == 1) {
            // Stop Engine
            return "Stop(Immobilizer) Engine";
        } else {
            // Restore Engine
            return "Restore(Immobilizer) Engine";
        }
    }

    @Override
    public int getAlertIcon(AlertDatum alertObj) {
        if (alertObj.getGeofenceStatus() == 1) {
            // Stop Engine
            return R.drawable.rp_alert_ignition_off;
        } else {
            // Restore Engine
            return R.drawable.rp_alert_ignition_on;
        }
    }
}
