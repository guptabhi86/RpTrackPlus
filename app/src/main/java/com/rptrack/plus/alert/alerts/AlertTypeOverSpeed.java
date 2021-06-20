package com.rptrack.plus.alert.alerts;

import android.content.Context;

import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.R;
import com.rptrack.plus.alert.AlertBasic;
import com.rptrack.plus.alert.AlertType;

public class AlertTypeOverSpeed extends AlertBasic {

    public AlertTypeOverSpeed(Context context) {
        super(context);
    }

    @Override
    public AlertType getAlertType() {
        return AlertType.OVER_SPEED;
    }

    @Override
    public String getMessage(AlertDatum alertObj) {
        return "Over Speed (Speed: " + alertObj.getSpeed() + ")";
    }

    @Override
    public int getAlertIcon(AlertDatum alertObj) {
        return R.drawable.rp_alert_overspeed;
    }
}
