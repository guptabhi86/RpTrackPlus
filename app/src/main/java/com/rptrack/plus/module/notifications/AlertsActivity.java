package com.rptrack.plus.module.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.DataModel.alerts.AlertResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.activity.AlertSettingsActivity;
import com.rptrack.plus.module.notifications.adapter.NotificationAdapter;
import com.rptrack.plus.module.notifications.adapter.PaginationScrollListener;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

import java.util.ArrayList;

public class AlertsActivity extends AppCompatActivity {
    RecyclerView notification_list;
    APIUtility apiUtility;
    NotificationAdapter notificationAdapter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    ArrayList<AlertDatum> alertData = new ArrayList<>();
    Datum datum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        getSupportActionBar().hide();
        TextView textTitle = (TextView) findViewById(R.id.text_title);
        ImageView back_icon = (ImageView) findViewById(R.id.back_icon);
        ImageView refresh_icon = (ImageView) findViewById(R.id.refresh_icon);
        refresh_icon.setVisibility(View.GONE);
        apiUtility = ApplicationActivity.getApiUtility();
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        refresh_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        back_icon.setColorFilter(ContextCompat.getColor(AlertsActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
        refresh_icon.setImageResource(R.drawable.operation);
        Intent intent=getIntent();
        if (intent!=null){
            datum=(Datum)intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE);
            textTitle.setText(datum.getDevice().getVehicleNo());


        }
        notification_list = (RecyclerView) findViewById(R.id.notification_list);
        notificationAdapter = new NotificationAdapter(AlertsActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AlertsActivity.this, RecyclerView.VERTICAL, false);
        notification_list.setLayoutManager(linearLayoutManager);
        notification_list.setAdapter(notificationAdapter);
        getNotificationListAttempt(datum.getDevice().getId(), null);

        notification_list.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems(int totalItemCount) {
                isLoading = true;
                Log.d("ENDOFPOSIT", "end");
                getNotificationListAttempt(0, alertData.get(totalItemCount - 1).getCreationTime());
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    void getNotificationListAttempt(int id, String lastTime) {

        String url = Constant.BASE_URL+"Alerts?id="+id;
        if (lastTime != null) {
            url = Constant.BASE_URL+"Alerts?lastTime=" + lastTime+"&id="+id;
        }

        apiUtility.getAllAlerts(AlertsActivity.this, true, url, new APIUtility.APIResponseListener<AlertResponse>() {
            @Override
            public void onReceiveResponse(AlertResponse response) {
                for (AlertDatum alertDatum : response.getResult().getData()) {
                    if (!alertData.contains(alertDatum)) {
                        alertData.add(alertDatum);
                    }
                }
                notificationAdapter.updateData(alertData);
                isLoading = false;
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(AlertsActivity.this, errorResponse.getErrorMessage().getStringData());
            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(AlertsActivity.this, string);
            }
        });
    }
}