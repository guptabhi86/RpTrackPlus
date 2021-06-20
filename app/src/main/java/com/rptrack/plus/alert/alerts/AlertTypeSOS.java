package com.rptrack.plus.alert.alerts;

import android.content.Context;

import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.R;
import com.rptrack.plus.alert.AlertBasic;
import com.rptrack.plus.alert.AlertType;

public class AlertTypeSOS extends AlertBasic {
    public AlertTypeSOS(Context context) {
        super(context);
    }

    @Override
    public AlertType getAlertType() {
        return AlertType.SOS;
    }

    @Override
    public String getMessage(AlertDatum alertObj) {
        return "Panic Pressed";
    }

    @Override
    public int getAlertIcon(AlertDatum alertObj) {
        return R.drawable.rp_alert_sos;
    }
}
