package com.rptrack.plus.module.reportUi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.DataModel.DashboardResponse.DashboardResponse;
import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.R;
import com.rptrack.plus.module.reportUi.reportAdapter.IconItem;
import com.rptrack.plus.module.reportUi.reportAdapter.ReportTypeAdapter;
import com.rptrack.plus.module.reportUi.reportAdapter.SelectVehicleAdapter;
import com.rptrack.plus.module.reportUi.reportAdapter.VehicleTransformerTransfarmer;



public class VehicleListDialogFragment extends DialogFragment implements SelectVehicleAdapter.VehicleSelected, ReportTypeAdapter.ReportTypeSelected {

    public static VehileListDialog listener1;

    RecyclerView vehicleListRecycler;
    EditText searchEditText;
    public static DashboardResponse dashboardResponse;
    SelectVehicleAdapter SelectVehicleAdapter;
    ReportTypeAdapter reportTypeAdapter;
    public VehicleListDialogFragment() {
        // Required empty public constructor
    }


    public static VehicleListDialogFragment newInstance(DashboardResponse dashboardResponse1, VehileListDialog listener) {
        VehicleListDialogFragment fragment = new VehicleListDialogFragment();
        listener1 = listener;
        dashboardResponse = dashboardResponse1;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  parkingViewModel = new ViewModelProvider(this).get(ParkingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_parking, container, false);
        ImageView imageView = (ImageView) root.findViewById(R.id.parkingBannner);
        imageView.setVisibility(View.GONE);
        vehicleListRecycler = (RecyclerView) root.findViewById(R.id.vehicle_list_recycler);
        searchEditText = (EditText) root.findViewById(R.id.input_search);
        LinearLayoutManager vehicleListLayout = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        vehicleListRecycler.setLayoutManager(vehicleListLayout);
      // ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        if (dashboardResponse != null) {
            SelectVehicleAdapter = new SelectVehicleAdapter(getActivity(), dashboardResponse.getResult().getData(), this);
            vehicleListRecycler.setAdapter(SelectVehicleAdapter);
        } else {
            reportTypeAdapter = new ReportTypeAdapter(getActivity(), VehicleTransformerTransfarmer.getAllReportType(), this);
            vehicleListRecycler.setAdapter(reportTypeAdapter);
        }
        addTextListener();
        return root;
    }

    private void addTextListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dashboardResponse != null)
                    SelectVehicleAdapter.getFilter().filter(s);
                else {
                    reportTypeAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void seletedVehicle(Datum datum) {
        listener1.onSelectedVehicle(datum);
        this.dismiss();
    }

    @Override
    public void seletedReport(IconItem report) {
        listener1.onSlectedReportType(report);
        this.dismiss();
    }


    public interface VehileListDialog {
        void onSelectedVehicle(Datum datum);

        void onSlectedReportType(IconItem report);
    }
}