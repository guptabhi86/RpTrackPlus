package com.rptrack.plus.alert.alerts;

import android.content.Context;

import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.R;
import com.rptrack.plus.alert.AlertBasic;
import com.rptrack.plus.alert.AlertType;

public class AlertTypeGeofence extends AlertBasic {
    public AlertTypeGeofence(Context context) {
        super(context);
    }

    @Override
    public AlertType getAlertType() {
        return AlertType.GEO_FENCE;
    }

    @Override
    public String getMessage(AlertDatum alertObj) {
        if (alertObj.geofenceStatus == 1) {
            // Vehicle In
            return "Geofence IN " + alertObj.getGeofenceName();
        } else {
            // Vehicle Out
            return "Geofence OUT " + alertObj.getGeofenceName();
        }
    }

    @Override
    public int getAlertIcon(AlertDatum alertObj) {
        return R.drawable.icon_geofence;
    }
}
