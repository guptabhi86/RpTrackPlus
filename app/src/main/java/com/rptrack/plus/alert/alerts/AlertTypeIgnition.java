package com.rptrack.plus.alert.alerts;

import android.content.Context;

import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.R;
import com.rptrack.plus.alert.AlertBasic;
import com.rptrack.plus.alert.AlertType;

public class AlertTypeIgnition extends AlertBasic {
    public AlertTypeIgnition(Context context) {
        super(context);
    }

    @Override
    public AlertType getAlertType() {
        return AlertType.IGNITION;
    }

    @Override
    public String getMessage(AlertDatum alertObj) {
        if (alertObj.getDi1() == 1) {
            return "Engine On";
        } else {
            return "Engine Off";
        }
    }

    @Override
    public int getAlertIcon(AlertDatum alertObj) {
        if (alertObj.getDi1() == 1) {
            return R.drawable.rp_alert_ignition_on;
        } else {
            return R.drawable.rp_alert_ignition_off;
        }
    }
}
