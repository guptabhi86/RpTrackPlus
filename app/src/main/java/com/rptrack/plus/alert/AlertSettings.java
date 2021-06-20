package com.rptrack.plus.alert;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.Gson;
import com.rptrack.plus.BR;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AlertSettings extends BaseObservable implements Serializable {

    private Boolean notification = false;
    private String fromTime = "00:00";
    private String toTime = "23:59";
    private Boolean allDay = false;
    private HashMap<AlertType, Boolean> types = new HashMap<AlertType, Boolean>();

    public AlertSettings() {

    }

    @Bindable
    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
        notifyPropertyChanged(BR.notification);
    }

    @Bindable
    public String getFromTime() {
        return fromTime;
    }

    public Calendar getCalenderFromTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Calendar fromT = Calendar.getInstance();
            fromT.setTime(sdf.parse(fromTime));
            fromT.add(Calendar.DATE, 1);
            return fromT;
        } catch (ParseException e) {
            return null;
        }
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
        notifyPropertyChanged(BR.fromTime);
    }

    @Bindable
    public String getToTime() {
        return toTime;
    }

    public Calendar getCalenderToTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Calendar fromT = Calendar.getInstance();
            fromT.setTime(sdf.parse(toTime));
            fromT.add(Calendar.DATE, 1);
            return fromT;
        } catch (ParseException e) {
            return null;
        }
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
        notifyPropertyChanged(BR.toTime);
    }

    @Bindable
    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
        this.fromTime = "00:00";
        this.toTime = "23:59";
        notifyPropertyChanged(BR._all);
    }

    public HashMap<AlertType, Boolean> getTypes() {
        return types;
    }

    public void setTypes(HashMap<AlertType, Boolean> types) {
        this.types = types;

    }

    public AlertSettings readSettings(Context context) {
        String ruleAsString = Preferences.getPreference(context, Constant.NOTIFICATION_RULE);
        if (ruleAsString.isEmpty()) {

            this.notification = true;
            for (int i = 1; i <= AlertType.getAllAlerts().size(); i++) {
                this.types.put(AlertType.castIntToEnum(i), true);
            }
            this.allDay = true;

            String string = new Gson().toJson(this);
            Preferences.setPreference(context, Constant.NOTIFICATION_RULE, string);
            ruleAsString = Preferences.getPreference(context, Constant.NOTIFICATION_RULE);
        }
        return new Gson().fromJson(ruleAsString, AlertSettings.class);
    }

    public void modifySettings(Context context) {
        String string = new Gson().toJson(this);
        Preferences.setPreference(context, Constant.NOTIFICATION_RULE, string);
    }
}
