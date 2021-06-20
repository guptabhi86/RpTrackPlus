package com.rptrack.plus.utilities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyTimePicker {
    private Context context;
    private View view;
    private IChange change;
    private int _hour = 12;
    private int _minute = 0;
    private String format = "HH:mm";

    public void setFormat(String format) {
        this.format = format;
    }

    public void setHour(int hour) {
        this._hour = hour;
    }

    public void setMinute(int minute) {
        this._minute = minute;
    }

    public void setDefaultTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(time));
        this._hour = calendar.get(Calendar.HOUR_OF_DAY);
        this._minute = calendar.get(Calendar.MINUTE);
    }

    public MyTimePicker(Context context, View view, IChange change) {
        this.context = context;
        this.view = view;
        this.change = change;
    }

    public interface IChange {
        void change(Calendar calender, View view);
    }

    public void showDialog() {
        TimePickerDialog dialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                _hour = hourOfDay;
                _minute = minute;
                Calendar calendar = Calendar.getInstance();
                calendar.set(0, 0, 0, _hour, _minute);
                change.change(calendar, view);
            }
        }, _hour, _minute, true);
        dialog.show();
    }
}
