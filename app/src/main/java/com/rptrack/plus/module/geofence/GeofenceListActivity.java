package com.rptrack.plus.module.geofence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chase.track.module.geofence.AddGeofenceActivity;
import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.DataModel.geofence.GeofenceDatum;
import com.rptrack.plus.DataModel.geofence.GeofenceListBaseResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.module.geofence.adapter.GeofenceListAdapter;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;


public class GeofenceListActivity extends AppCompatActivity implements GeofenceListAdapter.GeofenceSelection {


    APIUtility apiUtility;
    RecyclerView recyclerView;
    GeofenceListAdapter geofenceListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofence_list);
        getSupportActionBar().hide();
        TextView textTitle = (TextView)findViewById(R.id.text_title);
        TextView textTitleRight = (TextView)findViewById(R.id.text_title_right);
        textTitleRight.setVisibility(View.VISIBLE);
        textTitleRight.setText("New");
        textTitle.setText("Geofence List");
        ImageView backIcon = (ImageView) findViewById(R.id.back_icon);
        ImageView refreshIcon = (ImageView) findViewById(R.id.refresh_icon);
        refreshIcon.setVisibility(View.GONE);
        backIcon.setOnClickListener(v -> {
            finish();
        });
        textTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(GeofenceListActivity.this, AddGeofenceActivity.class);
                startActivity(intent);
            }
        });


        recyclerView=(RecyclerView)findViewById(R.id.geofence_list_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        geofenceListAdapter = new GeofenceListAdapter(this,this::geofenceSelected);
        recyclerView.setAdapter(geofenceListAdapter);
         apiUtility= ApplicationActivity.getApiUtility();
        getGeofenceList();

    }




    void getGeofenceList() {

        String url = "Geofence";
        apiUtility.getGeofenceList(GeofenceListActivity.this, true, url, new APIUtility.APIResponseListener<GeofenceListBaseResponse>() {
            @Override
            public void onReceiveResponse(GeofenceListBaseResponse response) {
            geofenceListAdapter.updateDateGeofence(response.getResult().getData());

               // pageViewModel.setData(response);
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
              //  pageViewModel.setErrorMessage(errorResponse.getErrorMessage().getStringData());
                CommonUtils.alert(GeofenceListActivity.this, errorResponse.getErrorMessage().getStringData()+"GQFHGF");

            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(GeofenceListActivity.this, string);

            }
        });
    }

    @Override
    public void geofenceSelected(GeofenceDatum datum) {

        Intent geonfence = new Intent(GeofenceListActivity.this, AddGeofenceActivity.class);
        geonfence.putExtra(Constant.INTENT_SERIALIZABLE, datum);
        startActivityForResult(geonfence, 1234);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getGeofenceList();
    }
}