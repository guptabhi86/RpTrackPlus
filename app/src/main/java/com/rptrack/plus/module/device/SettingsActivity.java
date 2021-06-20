package com.rptrack.plus.module.device;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.DataModel.speedLimit.SpeedRequest;
import com.rptrack.plus.DataModel.speedLimit.SpeedResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

import io.reactivex.observers.TestObserver;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    SwitchCompat speedLimitSwitch;
    TextView speedText;
    APIUtility apiUtility;
    Datum datum;
    int intSpeedLimit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        TextView textTitle = (TextView) findViewById(R.id.text_title);
        textTitle.setText("Settings");
        ImageView backIcon = (ImageView) findViewById(R.id.back_icon);
        ImageView refreshIcon = (ImageView) findViewById(R.id.refresh_icon);
        refreshIcon.setVisibility(View.GONE);
        backIcon.setOnClickListener(v -> {
            finish();
        });
        apiUtility = ApplicationActivity.getApiUtility();
        speedText = findViewById(R.id.speedText);


        speedLimitSwitch = (SwitchCompat) findViewById(R.id.switchSpeeding);
        speedLimitSwitch.setOnClickListener(this::onClick);

        Intent intent = getIntent();
        if (intent != null) {
            datum = (Datum) intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE);
            if (datum.getPeripherial().getOverSpeedLimit() != 0) {
                speedText.setText(datum.getPeripherial().getOverSpeedLimit() + "KM/H");
                speedLimitSwitch.setChecked(true);
            } else {
                speedLimitSwitch.setChecked(false);
            }

        }
    }


    void setSpeedDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppDialogTheme);
        LayoutInflater layoutInflater = ((Activity) this).getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.dialog_set_speed_limit, null);
        TextView button_submit = (TextView) customView.findViewById(R.id.submit_btn);
        TextView button_cancel = (TextView) customView.findViewById(R.id.cancel_btn);
        EditText speedLimit = (EditText) customView.findViewById(R.id.speed_limit);

        builder.setView(customView);
        final AlertDialog alert = builder.create();
        alert.setCancelable(false);

        alert.show();

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!speedLimit.getText().toString().isEmpty()) {
                    updateSpeed(Integer.parseInt(speedLimit.getText().toString()), datum.getDevice().getId());
                    alert.dismiss();
                } else {
                    Toast.makeText(SettingsActivity.this, "Please enter a valid speed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_cancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedLimitSwitch.setChecked(false);
                alert.dismiss();
            }
        }));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switchSpeeding:
                if (speedLimitSwitch.isChecked()) {
                    setSpeedDialog();
                } else {
                    updateSpeed(0, datum.getDevice().getId());


                }
                break;
        }
    }

    void updateSpeed(int updateValue, int deviceid) {
        SpeedRequest speedRequest = new SpeedRequest(updateValue);
        speedRequest.setDeviceId(deviceid);
        apiUtility.updateSpeedLimit(this, true, speedRequest, new APIUtility.APIResponseListener<SpeedResponse>() {
            @Override
            public void onReceiveResponse(SpeedResponse response) {
                if (updateValue==0){
                    speedLimitSwitch.setChecked(false);
                }else {
                    speedLimitSwitch.setChecked(true);
                    CommonUtils.alert(SettingsActivity.this, response.getSpeedResult().getStringData());

                }

                speedText.setText(String.valueOf(updateValue) + "KM/H");
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(SettingsActivity.this, errorResponse.getErrorMessage().getStringData());

            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(SettingsActivity.this, string);

            }
        });

    }
}