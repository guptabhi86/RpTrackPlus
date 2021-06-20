package com.rptrack.plus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rptrack.plus.alert.AlertType;
import com.rptrack.plus.R;
import com.rptrack.plus.module.notifications.adapter.AlertTypesAdapter;
import com.rptrack.plus.utilities.Constant;

import java.util.HashMap;

public class AlertTypesSettingsActivity extends AppCompatActivity implements AlertTypesAdapter.SettingChanged, View.OnClickListener {

    HashMap<AlertType, Boolean> value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_types_settings);

        getSupportActionBar().hide();

        ((ImageView) findViewById(R.id.refresh_icon)).setVisibility(View.GONE);
        ((ImageView) findViewById(R.id.back_icon)).setOnClickListener(this::onClick);

        Intent intent = getIntent();
        if (intent == null)
            return;

        value = (HashMap<AlertType, Boolean>) intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE);

        RecyclerView recyclerView = findViewById(R.id.alert_types_setting_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        AlertTypesAdapter alertTypesAdapter = new AlertTypesAdapter(this, value, this::changed);
        recyclerView.setAdapter(alertTypesAdapter);
    }

    @Override
    public void changed(AlertType alertType, Boolean status) {
        value.replace(alertType, status);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra(Constant.INTENT_SERIALIZABLE, value);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}