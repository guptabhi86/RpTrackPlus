package com.rptrack.plus.module.device;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.R;
import com.rptrack.plus.module.device.adapter.DeviceListAdapter;
import com.rptrack.plus.module.device.adapter.DevicesListAdapter;
import com.rptrack.plus.module.monitor.MonitorViewModel;
import com.rptrack.plus.module.monitor.RefreshCalled;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

import java.util.ArrayList;
import java.util.List;

public class DeviceFragment extends Fragment implements View.OnClickListener {

    private DeviceViewModel deviceViewModel;
    private RecyclerView deviceListRecycler;
    TabItem tabAll, tabOnline, tabOffline;
    TabLayout tabLayout;
    EditText searchableEditText;
    DevicesListAdapter deviceListAdapter;
    RefreshCalled refreshCalled;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceViewModel = new ViewModelProvider(this).get(DeviceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_device, container, false);
        TextView textTitle = (TextView) root.findViewById(R.id.text_title);
        ImageView back_icon=(ImageView)root.findViewById(R.id.back_icon);
        ImageView refres_icon=(ImageView)root.findViewById(R.id.refresh_icon);
        back_icon.setVisibility(View.GONE);
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        tabAll = (TabItem) root.findViewById(R.id.tab_all);
        tabOnline = (TabItem) root.findViewById(R.id.tab_online);
        tabOffline = (TabItem) root.findViewById(R.id.tab_offline);
        searchableEditText = (EditText) root.findViewById(R.id.input_search);

     refres_icon.setOnClickListener(this);

        textTitle.setText(Preferences.getPreference(getActivity(), Constant.USER_NAME));
        deviceListRecycler = (RecyclerView) root.findViewById(R.id.deviceListRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        deviceListRecycler.setLayoutManager(linearLayoutManager);
         deviceListAdapter = new DevicesListAdapter(getActivity());
        deviceListRecycler.setAdapter(deviceListAdapter);
        List<Datum> vehicleOnline = new ArrayList<>();
        List<Datum> vehicleOffline = new ArrayList<>();
        List<Datum> vehicleAll = new ArrayList<>();
        new ViewModelProvider(this.getActivity()).get(MonitorViewModel.class).getData().observe(getViewLifecycleOwner(), dashboardResponse -> {
            vehicleOnline.clear();
            vehicleOffline.clear();
            vehicleAll.clear();
            for (int i = 0; dashboardResponse.getResult().getData().size() > i; i++) {
                vehicleAll.add(dashboardResponse.getResult().getData().get(i));
                if (dashboardResponse.getResult().getData().get(i).getStatus() == 0) {
                    vehicleOffline.add(dashboardResponse.getResult().getData().get(i));
                } else {
                    vehicleOnline.add(dashboardResponse.getResult().getData().get(i));
                }
            }
            tabLayout.getTabAt(0).setText("ALL(" + vehicleAll.size() + ")");
            tabLayout.getTabAt(1).setText("ONLINE(" + vehicleOnline.size() + ")");
            tabLayout.getTabAt(2).setText("OFFLINE(" + vehicleOffline.size() + ")");


                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        deviceListAdapter.updateDeviceListData(vehicleAll);
                        break;
                    case 1:
                        deviceListAdapter.updateDeviceListData(vehicleOnline);
                        break;
                    case 2:
                        deviceListAdapter.updateDeviceListData(vehicleOffline);
                        break;
                    default:
                }

            Toast.makeText(getActivity(), dashboardResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                deviceListRecycler.setAdapter(deviceListAdapter);
                switch (tab.getPosition()) {

                    case 0:
                        if (deviceListAdapter != null)
                            deviceListAdapter.updateDeviceListData(vehicleAll);
                        break;
                    case 1:
                        if (deviceListAdapter != null)
                            deviceListAdapter.updateDeviceListData(vehicleOnline);
                        break;
                    case 2:
                        if (deviceListAdapter != null)
                            deviceListAdapter.updateDeviceListData(vehicleOffline);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    addTextListener();
        return root;
    }

    private void addTextListener(){
        searchableEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deviceListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshCalled = (RefreshCalled) getActivity();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refresh_icon:
                refreshCalled.onRefreshAttempt();
        }
    }
}