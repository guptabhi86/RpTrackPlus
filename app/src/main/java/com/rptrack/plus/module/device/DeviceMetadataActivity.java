package com.rptrack.plus.module.device;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.R;
import com.rptrack.plus.utilities.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DeviceMetadataActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvVehicleNumber, tvDeviceImei, tvDeviceSim,
            tvVehiclePlateNo, tvDeviceModel, tvActivationTime, tvExpireTime,
            tvVehicleStatus, tvGpsTime, tvLatestUpdate, tvSpeed, tvCoordinate, tvEngineStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_metadata);
        getSupportActionBar().hide();

        ImageView back_icon = findViewById(R.id.back_icon);
        ImageView refreshIcon = findViewById(R.id.refresh_icon);
        TextView textTitle = findViewById(R.id.text_title);
        TextView textTitleRight = findViewById(R.id.text_title_right);
        back_icon.setOnClickListener(v -> finish());
        textTitleRight.setOnClickListener(v -> finish());
        refreshIcon.setVisibility(View.GONE);


        tvVehicleNumber = findViewById(R.id.tv_vehicle_number);
        tvDeviceImei = findViewById(R.id.tv_imei);
        tvDeviceSim = findViewById(R.id.tv_sim_card);
        tvVehiclePlateNo = findViewById(R.id.tv_vehicle_number_plate);
        tvDeviceModel = findViewById(R.id.tv_device_model);
        tvActivationTime = findViewById(R.id.tv_activation_time);
        tvExpireTime = findViewById(R.id.tv_expire_time);

        tvVehicleStatus = findViewById(R.id.tv_vehicle_status);
        tvGpsTime = findViewById(R.id.tv_gps_time);
        tvLatestUpdate = findViewById(R.id.tv_latest_update);
        tvSpeed = findViewById(R.id.tv_vehicle_speed);
        tvCoordinate = findViewById(R.id.tv_vehicle_coordinates);
        tvEngineStatus = findViewById(R.id.tv_engine_status);
        Intent intent = getIntent();
        if (!intent.hasExtra(Constant.INTENT_SERIALIZABLE)) {
            finish();
            return;
        }

        Datum datum = (Datum) intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE);
        if (datum.getStatus() == 0) {
            datum.getEventdata().setDI1(0);
            datum.getEventdata().setSpeed(0.0);
        }

        tvVehicleNumber.setText(datum.getDevice().getVehicleNo());
        tvDeviceImei.setText(datum.getDevice().getDeviceIme());


        tvVehicleStatus.setText(datum.getStatusDuration());
        tvGpsTime.setText(datum.getEventdata().getTimestamp().replace("T", " "));
        textTitle.setText(datum.getDevice().getVehicleNo());

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        tvLatestUpdate.setText(simpleDateFormat.format(currentTime));
        tvSpeed.setText(datum.getEventdata().getSpeed() + "Kph");
        tvCoordinate.setText((datum.getEventdata().getLatitude() + "," + datum.getEventdata().getLongitude()));

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");


        if (datum.getEventdata().getDI1() != null) {
            tvEngineStatus.setText(datum.getEventdata().getDI1() == 1 ? "ON" : "OFF");
        }

        if (datum.getDevice().getSimPhoneNumber() != null) {
            tvDeviceSim.setText(datum.getDevice().getSimPhoneNumber());
        }

        if (datum.getDevice().getDeviceTypeMeta() != null) {
            if (datum.getDevice().getDeviceTypeMeta().getName() != null) {
                tvDeviceModel.setText(datum.getDevice().getDeviceTypeMeta().getName());
            }
        }

        tvActivationTime.setText(datum.getActivationDate());
        tvExpireTime.setText(datum.getNextRechargeDate());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_vehicle_number:
                startActivity(new Intent(DeviceMetadataActivity.this, EditDeviceMetadataActivity.class));
                break;
            default:
                break;
        }
    }
}