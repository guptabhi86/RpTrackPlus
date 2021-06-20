package com.rptrack.plus.alert;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class AlertBasic implements IAlert {
    Context context = null;
    AlertSettings alertSettings;

    public AlertBasic(Context context) {
        this.context = context;
    }

    @Override
    public boolean showAlert() {
        alertSettings = new AlertSettings().readSettings(context);
        if (!alertSettings.getNotification())
            return false;

        if (!alertSettings.getTypes().get(this.getAlertType()))
            return false;

        Date currentTime = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
            currentTime = sf.parse(sf.format(Calendar.getInstance().getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar currentT = Calendar.getInstance();
        currentT.setTime(currentTime);
        currentT.add(Calendar.DATE, 1);

        if (currentT.getTime().after(alertSettings.getCalenderFromTime().getTime()) && currentT.getTime().before(alertSettings.getCalenderToTime().getTime()))
            return true;
        return false;
    }
}
