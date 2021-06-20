package com.rptrack.plus.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.util.concurrent.Monitor;
import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.DashboardResponse.DashboardResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.module.device.DeviceViewModel;
import com.rptrack.plus.module.monitor.MonitorViewModel;
import com.rptrack.plus.module.monitor.RefreshCalled;
import com.rptrack.plus.module.notifications.NotificationsViewModel;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class DashboardActivity extends AppCompatActivity implements RefreshCalled {

    APIUtility apiUtility;
    MonitorViewModel trackingViewModel;
    NotificationsViewModel notificationsViewModel;
    DeviceViewModel vehicleViewModel;
    DashboardResponse dashboardResponse;

    private int deviceId = 0;
    private Handler handler;
    private Runnable runnable;
    int refreshTime = 10000;
   // private ItemViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.nav_view);

        vehicleViewModel = new ViewModelProvider(this).get(DeviceViewModel.class);
        vehicleViewModel.init();

        trackingViewModel = new ViewModelProvider(this).get(MonitorViewModel.class);
        trackingViewModel.init();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_monitor, R.id.navigation_device, R.id.navigation_alert,R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        apiUtility = ApplicationActivity.getApiUtility();
        getDashboardData();

        handler = new Handler();
        runnable = () -> {
            getDashboardData();
            handler.postDelayed(runnable, refreshTime);
        };
        refreshTime = CommonUtils.setRefreshTime(DashboardActivity.this);
    }


    void getDashboardData() {
        apiUtility.DashboardResponseData(DashboardActivity.this, false, Constant.DASHBOARD_DATA_URL, new APIUtility.APIResponseListener<DashboardResponse>() {
            @Override
            public void onReceiveResponse(DashboardResponse response) {
                vehicleViewModel.setData(response);
                trackingViewModel.setData(response);
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {

            }

            @Override
            public void onFailureResponse(String string) {

            }
        });
    }


    private void startHandler() {
        Log.d("Handler State", "START");
        handler.postDelayed(runnable, refreshTime);
    }

    private void stopHandler() {
        Log.d("Handler State", "STOP");
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startHandler();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopHandler();
    }

    @Override
    public void onRefreshAttempt() {
        getDashboardData();
    }
}