package com.rptrack.plus.module.reports;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;

import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.R;
import com.rptrack.plus.utilities.RadioGridGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class CustomDatePicker extends VehicleOption implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    View _selectedText = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public CustomDatePicker(Context _context, IResult _result, int _id, boolean isHideTime, int deviceId) {
        super(_context, _result, _id, isHideTime, deviceId);
    }


    @Override
    public void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(_context, R.style.AppDialogTheme);
        LayoutInflater layoutInflater = ((Activity) _context).getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.dialog_date_time_picker, null);
        TextView button_submit = (TextView) customView.findViewById(R.id.submit_btn);
        TextView button_cancel = (TextView) customView.findViewById(R.id.cancel_btn);
        EditText from_date = (EditText) customView.findViewById(R.id.from_date);
        EditText from_time = (EditText) customView.findViewById(R.id.from_time);
        EditText to_date = (EditText) customView.findViewById(R.id.to_date);
        EditText to_time = (EditText) customView.findViewById(R.id.to_time);
        RadioGridGroup radioGroup = (RadioGridGroup) customView.findViewById(R.id.radioGroup);
        RadioButton radioToday = (RadioButton) customView.findViewById(R.id.radio_today);
        RadioButton radioYestarday = (RadioButton) customView.findViewById(R.id.radio_Yesterday);
        RadioButton radioOneHr = (RadioButton) customView.findViewById(R.id.radio_one_hr);
        RadioButton radioCustom = (RadioButton) customView.findViewById(R.id.radio_custome);
        LinearLayout dateTimeLayout=(LinearLayout)customView.findViewById(R.id.layout_custom);
        dateTimeLayout.setVisibility(View.GONE);
        radioToday.setChecked(true);
        from_date.setOnClickListener(this);
        to_date.setOnClickListener(this);
        to_time.setOnClickListener(this);
        from_time.setOnClickListener(this);



        if (isHideTime) {
            from_time.setVisibility(View.GONE);
            to_time.setVisibility(View.GONE);
        }

        builder.setView(customView);
        final AlertDialog alert = builder.create();
        alert.setCancelable(true);

        Date obj = new Date();
        from_date.setText(dateFormat.format(obj));
        //from_time.setText(timeFormat.format(obj));
        from_time.setText("00:00");
        to_time.setText(timeFormat.format(obj));
        to_date.setText(dateFormat.format(obj));
        alert.show();

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromTime, toTime;
                fromTime = "00:00";
                toTime = "23:59";
                InputData data = new InputData();
                //{"DeviceId":48,"FromTime":"2021-May-23 00:00","ToTime":"2021-May-23 22:37"}
                //                            2021-May-23 22:47
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm", Locale.getDefault());
                SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy-MMM-dd ", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                Log.d("FROMTIME1", currentDateandTime);
                if (radioToday.isChecked()) {
                    data.fromTime=dateOnly.format((new Date()))+"00:00";
                    data.toTime=currentDateandTime;
                    data.id = _deviceId;
                    _result.Response(data, _id);

                } else if (radioYestarday.isChecked()) {
                    data.fromTime=getYesterdayDateString()+"00:00";
                    data.toTime=getYesterdayDateString()+"23:59";
                    data.id = _deviceId;
                    _result.Response(data, _id);

                } else if (radioOneHr.isChecked()) {

                    Date date = null;
                    try {
                        date = sdf.parse(currentDateandTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.HOUR, -1);

                    data.fromTime=sdf.format(calendar.getTime());
                    data.toTime=currentDateandTime;
                    data.id = _deviceId;
                    _result.Response(data, _id);

                } else {
                    if (!isHideTime) {
                        fromTime = from_time.getText().toString();
                        toTime = to_time.getText().toString();
                    }
                    data.fromTime = from_date.getText() + " " + fromTime;
                    data.toTime = to_date.getText() + " " + toTime;
                    data.id = _deviceId;
                    _result.Response(data, _id);
                    Log.d("FROMTIME", data.getFromTime());

                }

                alert.dismiss();
            }
        });

        button_cancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        }));
        radioGroup.setOnCheckedChangeListener(new RadioGridGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGridGroup group, int checkedId) {
                if (checkedId==R.id.radio_custome){
                    dateTimeLayout.setVisibility(View.VISIBLE);
                }else {
                    dateTimeLayout.setVisibility(View.GONE);
                }
            }
        });



    }

    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd ");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date date = new GregorianCalendar(year, month, dayOfMonth).getTime();
        EditText et = (EditText) _selectedText.findViewById(_selectedText.getId());
        et.setText(dateFormat.format(date));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Date date = new GregorianCalendar(0, 0, 0, hourOfDay, minute).getTime();
        EditText et = (EditText) _selectedText.findViewById(_selectedText.getId());
        et.setText(timeFormat.format(date));
    }

    @Override
    public void onClick(View v) {
        _selectedText = v;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        if (v.getId() == R.id.from_date || v.getId() == R.id.to_date) {
            DatePickerDialog dialog = new DatePickerDialog(_context,this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMaxDate(new Date().getTime());
            dialog.show();
        } else {
            TimePickerDialog dialog = new TimePickerDialog(_context, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            dialog.show();
        }
    }


}


