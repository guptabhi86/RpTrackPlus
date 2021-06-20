package com.rptrack.plus.module.reportUi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.DashboardResponse.DashboardResponse;
import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.DataModel.DistanceReport.DistanceResponse;
import com.rptrack.plus.DataModel.HistoryReplyResponse.HistoryReplyRequest;
import com.rptrack.plus.DataModel.StoppageResponse.StoppageResponse;
import com.rptrack.plus.DataModel.TripReport.TripReportResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.module.reportUi.reportAdapter.DistanceReportAdapter;
import com.rptrack.plus.module.reportUi.reportAdapter.IconItem;
import com.rptrack.plus.module.reportUi.reportAdapter.StoppageReportAdapter;
import com.rptrack.plus.module.reportUi.reportAdapter.TripReportAdapter;
import com.rptrack.plus.module.reports.IResult;
import com.rptrack.plus.module.reports.InputData;
import com.rptrack.plus.module.reports.VehicleFactory;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener, IResult , VehicleListDialogFragment.VehileListDialog{

    private FrameLayout layoutVehicle, layoutReport, layoutSearch;
    DashboardResponse dashboardResponse;
    VehicleListDialogFragment.VehileListDialog selectedVehicle1;
    TextView vehicleNumer, txtSelcetedReport;
    Datum datum = null;
    IconItem iconItem = null;
    APIUtility apiUtility;
    Context _context = ReportActivity.this;
    RecyclerView recyclerView;
    ImageView imageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().hide();

        TextView textTitle = (TextView)findViewById(R.id.text_title);
        textTitle.setText("Reports");
        ImageView backIcon = (ImageView) findViewById(R.id.back_icon);
        ImageView refreshIcon = (ImageView) findViewById(R.id.refresh_icon);
        refreshIcon.setVisibility(View.GONE);
        backIcon.setOnClickListener(v -> {
            finish();
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ReportActivity.this, RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        layoutVehicle = (FrameLayout) findViewById(R.id.layout_select_vehicle);
        layoutReport = (FrameLayout) findViewById(R.id.layout_select_report);
        layoutSearch = (FrameLayout) findViewById(R.id.layout_search);
        vehicleNumer = (TextView) findViewById(R.id.text_vehicle_number);
        txtSelcetedReport = (TextView) findViewById(R.id.text_slected_report);
        imageUser = (ImageView) findViewById(R.id.image_user);
        selectedVehicle1 = (VehicleListDialogFragment.VehileListDialog) ReportActivity.this;
        layoutReport.setOnClickListener(this::onClick);
        layoutVehicle.setOnClickListener(this::onClick);
        layoutSearch.setOnClickListener(this::onClick);
        imageUser.setOnClickListener(this::onClick);
        apiUtility = ApplicationActivity.getApiUtility();

        Intent intent = getIntent();
        if (intent != null) {
            dashboardResponse = (DashboardResponse) intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_select_vehicle:
                showVehicleList(dashboardResponse, true);
                break;
            case R.id.layout_select_report:
                showVehicleList(dashboardResponse, false);
                break;
            case R.id.layout_search:
                validateRequest();
                break;
            case R.id.image_user:
                if (layoutVehicle.getVisibility() == View.GONE) {
                    layoutVehicle.setVisibility(View.VISIBLE);
                    layoutReport.setVisibility(View.VISIBLE);
                    layoutSearch.setVisibility(View.VISIBLE);
                    imageUser.setRotation(imageUser.getRotation() + 180);
                } else {
                    layoutVehicle.setVisibility(View.GONE);
                    layoutReport.setVisibility(View.GONE);
                    layoutSearch.setVisibility(View.GONE);

                }

                break;
        }
    }

    @Override
    public void Response(Object data, int id) {
        InputData inputData = (InputData) data;
        if (id == 2) {
            TripResultResponse(inputData.getId(), inputData.getFromTime(), inputData.getToTime());
        } else if (id == 3) {
            DistanceResponse(inputData.getId(), inputData.getFromTime(), inputData.getToTime());
        } else if (id == 5) {
            StoppageResponse(inputData.getId(), inputData.getFromTime(), inputData.getToTime());
        } else if (id == 6) {
            IdleResponse(inputData.getId(), inputData.getFromTime(), inputData.getToTime());
        }
    }

    private void showVehicleList(DashboardResponse dashboardResponse, boolean isVehicleList) {
        FragmentManager fm = getSupportFragmentManager();
        VehicleListDialogFragment vehicleListDialogFragment;
        if (isVehicleList) {
            vehicleListDialogFragment = VehicleListDialogFragment.newInstance(dashboardResponse, selectedVehicle1);
        } else {
            vehicleListDialogFragment = VehicleListDialogFragment.newInstance(null, selectedVehicle1);
        }
        vehicleListDialogFragment.show(fm, "fragment_edit_name_1");

    }

    void TripResultResponse(int deviceId, String fromTime, String toTime) {
        HistoryReplyRequest historyReplyRequest = new HistoryReplyRequest(deviceId, fromTime, toTime);
        apiUtility.TripReportRequest(ReportActivity.this, true, historyReplyRequest, new APIUtility.APIResponseListener<TripReportResponse>() {
            @Override
            public void onReceiveResponse(TripReportResponse response) {
                if (response.getResult().getData().getPoints().size() > 0) {
                    setDataToAdapter(response, 12520, "Trip Report");
                } else {
                    CommonUtils.alert(ReportActivity.this, "No Data Found");
                }
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(ReportActivity.this, errorResponse.getErrorMessage().getStringData());
            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(ReportActivity.this, string);
            }
        });
    }

    void DistanceResponse(int deviceId, String fromTime, String toTime) {
        HistoryReplyRequest historyReplyRequest = new HistoryReplyRequest(deviceId, fromTime, toTime);
        apiUtility.DistanceRequest(_context, true, historyReplyRequest, new APIUtility.APIResponseListener<DistanceResponse>() {
            @Override
            public void onReceiveResponse(DistanceResponse response) {
                if (response.getResult().getData().getDistance().size() > 0) {
                    setDataToAdapter(response, 12525, "Distance Report");


                } else {
                    CommonUtils.alert(_context, "No Data Found");
                }
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(_context, errorResponse.getErrorMessage().getStringData());
            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(_context, string);
            }
        });
    }

    void StoppageResponse(int deviceId, String fromTime, String toTime) {
        HistoryReplyRequest historyReplyRequest = new HistoryReplyRequest(deviceId, fromTime, toTime);
        apiUtility.StoppageRequest(_context, true, historyReplyRequest, new APIUtility.APIResponseListener<StoppageResponse>() {
            @Override
            public void onReceiveResponse(StoppageResponse response) {
                if (response.getResult().getData().getPoints().size() > 0) {
                    setDataToAdapter(response, 12524, "Stoppage Report");

                } else {
                    CommonUtils.alert(_context, "No Data Found");
                }
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(_context, errorResponse.getErrorMessage().getStringData());
            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(_context, string);
            }
        });
    }

    void IdleResponse(int deviceId, String fromTime, String toTime) {
        HistoryReplyRequest historyReplyRequest = new HistoryReplyRequest(deviceId, fromTime, toTime);
        apiUtility.IdleRequest(_context, true, historyReplyRequest, new APIUtility.APIResponseListener<StoppageResponse>() {
            @Override
            public void onReceiveResponse(StoppageResponse response) {
                if (response.getResult().getData().getPoints().size() > 0) {
                    setDataToAdapter(response, 12523, "Idle Report");
                } else {
                    CommonUtils.alert(_context, "No Data Found");
                }
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(_context, errorResponse.getErrorMessage().getStringData());
            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(_context, string);
            }
        });
    }

    void setDataToAdapter(Object object, int reqCode, String title) {

        setTitle(title);
        if (reqCode == 12525) {
            DistanceResponse distanceResponse = (DistanceResponse) object;
            DistanceReportAdapter distanceReportAdapter = new DistanceReportAdapter(ReportActivity.this, distanceResponse.getResult().getData().getDistance());
            recyclerView.setAdapter(distanceReportAdapter);
        } else if (reqCode == 12524) {
            StoppageResponse stoppageResponse = (StoppageResponse) object;
            StoppageReportAdapter stoppageReportAdapter = new StoppageReportAdapter(ReportActivity.this, stoppageResponse.getResult().getData().getPoints(), false);
            recyclerView.setAdapter(stoppageReportAdapter);

        } else if (reqCode == 12523) {
            StoppageResponse stoppageResponse = (StoppageResponse) object;
            StoppageReportAdapter stoppageReportAdapter = new StoppageReportAdapter(ReportActivity.this, stoppageResponse.getResult().getData().getPoints(), true);
            recyclerView.setAdapter(stoppageReportAdapter);
        } else if (reqCode == 12520) {
            TripReportResponse tripReportResponse = (TripReportResponse) object;
            TripReportAdapter tripReportAdapter = new TripReportAdapter(ReportActivity.this, tripReportResponse.getResult().getData().getPoints());
            recyclerView.setAdapter(tripReportAdapter);
        }
    }

    void validateRequest() {
        boolean isCancel = false;
        if (datum == null) {
            Toast.makeText(ReportActivity.this, "Vehicle Not Selected", Toast.LENGTH_SHORT).show();
            isCancel = true;
        } else if (iconItem == null) {
            Toast.makeText(ReportActivity.this, "Report Type Not Selected", Toast.LENGTH_SHORT).show();
            isCancel = true;
        } else {
            if (!isCancel) {
                new VehicleFactory().CreateBuilderObject(this, iconItem.getColor(),this,null,datum.getDevice().getId()).showDialog();
            }
        }
    }



    @Override
    public void onSelectedVehicle(Datum datum) {
        this.datum = datum;
        vehicleNumer.setText(datum.getDevice().getVehicleNo());
    }

    @Override
    public void onSlectedReportType(IconItem report) {
        iconItem = report;
        Log.d("RequestType,",String.valueOf(iconItem.getColor()));
        txtSelcetedReport.setText(report.getLabel());
    }
}
