package com.rptrack.plus.module.commands;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.DataModel.Relay.RelayRequest;
import com.rptrack.plus.DataModel.Relay.RelayResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.module.reports.IResult;
import com.rptrack.plus.module.reports.VehicleFactory;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

import java.util.function.Predicate;

public class CommandsActivity extends AppCompatActivity implements CommandsListAdapter.commandListener, IResult {
    Datum datum;
    APIUtility apiUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);

        getSupportActionBar().hide();
        TextView textTitle = (TextView) findViewById(R.id.text_title);
        TextView textImei = (TextView) findViewById(R.id.text_imei_value);
        ImageView backIcon = (ImageView) findViewById(R.id.back_icon);
        ImageView refreshIcon = (ImageView) findViewById(R.id.refresh_icon);
        refreshIcon.setVisibility(View.GONE);
        backIcon.setOnClickListener(v -> {
            finish();
        });
        apiUtility = ApplicationActivity.getApiUtility();
        textTitle.setText("Commands");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();

        String[] stringArray = {"Stop Engine", "Restore Engine"};
        CommandsListAdapter cmdAdapter = new CommandsListAdapter(this, stringArray, this);
        recyclerView.setAdapter(cmdAdapter);
        datum = (Datum) intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE);
    }

    @Override
    public void commandd(String label, int position) {
        if (position == 0) {
            // new VehicleFactory().CreateBuilderObject(this, 1, this, datum, 1).showDialog();
            RELAYRequest("", datum.getDevice().getId(), 1);
        } else {
            // new VehicleFactory().CreateBuilderObject(this, 1, this, datum, 2).showDialog();
            RELAYRequest("", datum.getDevice().getId(), 2);
        }
    }

    @Override
    public void Response(Object data, int id) {

    }

    void RELAYRequest(String fcmToken, int deviceId, int commandType) {
        RelayRequest relayRequest = new RelayRequest(fcmToken, deviceId, commandType);
        apiUtility.RELAYRequest(CommandsActivity.this, true, relayRequest, new APIUtility.APIResponseListener<RelayResponse>() {
            @Override
            public void onReceiveResponse(RelayResponse response) {
                CommonUtils.alert(CommandsActivity.this, response.getResult().getData());
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(CommandsActivity.this, errorResponse.getErrorMessage().getStringData());
            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(CommandsActivity.this, string);
            }
        });
    }

}