package com.rptrack.plus.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.rptrack.plus.alert.AlertSettings;
import com.rptrack.plus.alert.AlertType;
import com.rptrack.plus.alert.Notification;
import com.rptrack.plus.R;
import com.rptrack.plus.databinding.NotificationSettingsLayoutBinding;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.MyTimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AlertSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    AlertSettings alertSettings;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    NotificationSettingsLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.notification_settings_layout);
        // ---------------------------Action Bar---------------------------------------- //
        // Hide default action bar
        getSupportActionBar().hide();

        // Initialized Title bar back button and hide right side refresh button
        ((ImageView) findViewById(R.id.refresh_icon)).setVisibility(View.GONE);
        ((ImageView) findViewById(R.id.back_icon)).setOnClickListener(this::onClick);

        // Enable right side text view, set the click listener
        TextView tvSave = (TextView) findViewById(R.id.text_title_right);
        tvSave.setText("Save");
        tvSave.setVisibility(View.VISIBLE);
        tvSave.setOnClickListener(this::onClick);
        // --------------------------------------------------------------------------- //

        binding.rlAlertOptionSettings.setOnClickListener(this::onClick);
        // binding.tvFromTime.setOnClickListener(this::onClick);
        // binding.tvToTime.setOnClickListener(this::onClick);
        alertSettings = new AlertSettings().readSettings(this);
        binding.setSetting(alertSettings);
    }

    MyTimePicker.IChange change = new MyTimePicker.IChange() {
        @Override
        public void change(Calendar calender, View view) {
            String time = simpleDateFormat.format(calender.getTime());
            switch (view.getId()) {
                case R.id.tv_from_time:
                    alertSettings.setFromTime(time);
                    break;
                case R.id.tv_to_time:
                    alertSettings.setToTime(time);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            HashMap<AlertType, Boolean> result = (HashMap<AlertType, Boolean>) data.getSerializableExtra(Constant.INTENT_SERIALIZABLE);
            alertSettings.setTypes(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_icon:
                onBackPressed();
                break;
            case R.id.text_title_right:
                alertSettings.modifySettings(this);
                finish();
                break;
            case R.id.rl_alert_option_settings:
                Intent intent = new Intent(AlertSettingsActivity.this, AlertTypesSettingsActivity.class);
                intent.putExtra(Constant.INTENT_SERIALIZABLE, alertSettings.getTypes());
                startActivityForResult(intent, 200);
                break;
            case R.id.tv_from_time:
                showTimePicker(binding.tvFromTime);
                break;
            case R.id.tv_to_time:
                showTimePicker(binding.tvToTime);
                break;
            default:
                break;
        }
    }

    public void showTimePicker(View view) {
        MyTimePicker picker = new MyTimePicker(this, view, change);
        try {
            picker.setFormat("HH:mm");
            picker.setDefaultTime(((TextView) view).getText().toString());
        } catch (ParseException ex) {

        }
        picker.showDialog();
    }
}