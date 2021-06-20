package com.rptrack.plus.alert.alerts;

import android.content.Context;

import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.R;
import com.rptrack.plus.alert.AlertBasic;
import com.rptrack.plus.alert.AlertType;

public class AlertTypeAC extends AlertBasic {
    public AlertTypeAC(Context context) {
        super(context);
    }

    @Override
    public AlertType getAlertType() {
        return AlertType.AC;
    }

    @Override
    public String getMessage(AlertDatum alertObj) {
        if (alertObj.getDi2() == 1) {
            return "AC ON";
        } else {
            return "AC OFF";
        }
    }

    @Override
    public int getAlertIcon(AlertDatum alertObj) {
        if (alertObj.getDi2() == 1) {
            return R.drawable.rp_alert_ac_on;
        } else {
            return R.drawable.rp_alert_ac_off;
        }
    }
}
