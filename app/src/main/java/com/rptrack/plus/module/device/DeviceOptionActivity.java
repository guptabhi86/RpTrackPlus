package com.rptrack.plus.module.device;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.DataModel.TripReport.Device;
import com.rptrack.plus.R;
import com.rptrack.plus.activity.TrackingActivity;
import com.rptrack.plus.module.commands.CommandsActivity;
import com.rptrack.plus.module.notifications.AlertsActivity;
import com.rptrack.plus.module.reports.IResult;
import com.rptrack.plus.module.reports.ResultFactory;
import com.rptrack.plus.module.reports.VehicleFactory;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

public class DeviceOptionActivity extends AppCompatActivity implements View.OnClickListener {
    Datum datum=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_option);
        getSupportActionBar().hide();
        TextView textTitle = (TextView)findViewById(R.id.text_title);
        TextView textImei = (TextView)findViewById(R.id.text_imei_value);
        ImageView backIcon = (ImageView) findViewById(R.id.back_icon);
        ImageView refreshIcon = (ImageView) findViewById(R.id.refresh_icon);
        refreshIcon.setVisibility(View.GONE);
        backIcon.setOnClickListener(v -> {
          finish();
        });
        Intent intent=getIntent();
        if (intent!=null){
            datum=(Datum)intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE);
            textImei.setText(datum.getDevice().getDeviceIme());
            textTitle.setText(datum.getDevice().getVehicleNo());

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_imei:
                Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
                ClipboardManager clipboardManager = (ClipboardManager)clipboardService;
                ClipData clipData = ClipData.newPlainText("Source Text", datum.getDevice().getDeviceIme());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(DeviceOptionActivity.this,"Copied",Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_tracking:
                startActivity(new Intent(DeviceOptionActivity.this, TrackingActivity.class).putExtra(Constant.INTENT_SERIALIZABLE,datum));
                break;
            case R.id.layout_playback:
                new VehicleFactory().CreateBuilderObject(DeviceOptionActivity.this, 0, new IResult() {
                    @Override
                    public void Response(Object data, int id) {
                        new ResultFactory().CreateInstance(id, data, DeviceOptionActivity.this).Process();
                    }
                },null,datum.getDevice().getId()).showDialog();
                break;
            case R.id.layout_details:
                Intent metaData=new Intent(DeviceOptionActivity.this,DeviceMetadataActivity.class);
                metaData.putExtra(Constant.INTENT_SERIALIZABLE,datum);
                startActivity(metaData);

                break;
            case R.id.layout_commands:
                Intent commands=new Intent(DeviceOptionActivity.this, CommandsActivity.class);
                commands.putExtra(Constant.INTENT_SERIALIZABLE,datum);
                startActivity(commands);
                break;
            case R.id.layout_alerts:
                Intent alerts=new Intent(DeviceOptionActivity.this, AlertsActivity.class);
                alerts.putExtra(Constant.INTENT_SERIALIZABLE,datum);
                startActivity(alerts);
                break;

            case R.id.layout_share_location:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String shareMessage= "Hey , \nHere I am sending you the current location of Vehicle("+datum.getDevice().getVehicleNo()+"):\n";
                shareMessage = shareMessage + "http://maps.google.com/maps/?q=loc:"+
                        String.valueOf(datum.getEventdata().getLatitude())+","+
                        String.valueOf(datum.getEventdata().getLongitude());
                //  shareMessage = shareMessage + String.valueOf("https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID);
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(intent, "Choose app for Sharing application."));
                break;
            case R.id.layout_settings:
                Intent settings=new Intent(DeviceOptionActivity.this, SettingsActivity.class);
                settings.putExtra(Constant.INTENT_SERIALIZABLE,datum);
                startActivity(settings);
                break;
            default:
                break;
        }
    }
}