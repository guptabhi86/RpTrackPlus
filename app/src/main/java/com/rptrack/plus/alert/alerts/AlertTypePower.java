package com.rptrack.plus.alert.alerts;

import android.content.Context;

import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.R;
import com.rptrack.plus.alert.AlertBasic;
import com.rptrack.plus.alert.AlertType;

public class AlertTypePower extends AlertBasic {
    public AlertTypePower(Context context) {
        super(context);
    }

    @Override
    public AlertType getAlertType() {
        return AlertType.POWER;
    }

    @Override
    public String getMessage(AlertDatum alertObj) {
        if (alertObj.getEpc() == 1) {
            // External Power Restore
            return "External Power Restored";
        } else {
            // External Power Cut
            return "External Power Stopped";
        }
    }

    @Override
    public int getAlertIcon(AlertDatum alertObj) {
        return R.drawable.rp_alert_power;
    }
}
