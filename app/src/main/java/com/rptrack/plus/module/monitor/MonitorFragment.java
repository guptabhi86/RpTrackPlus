package com.rptrack.plus.module.monitor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.DashboardResponse.DashboardResponse;
import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.DataModel.geofence.GeofencePoints;
import com.rptrack.plus.DataModel.geofence.createGeofence.CreateGeofenceReq;
import com.rptrack.plus.DataModel.geofence.createGeofence.CreateGeofenceResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.activity.TrackingActivity;
import com.rptrack.plus.module.commands.CommandsActivity;
import com.rptrack.plus.module.reports.VehicleFactory;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.PermissionUtils;
import com.rptrack.plus.utilities.Preferences;
import com.rptrack.plus.vehicleTypes.IVehicleType;
import com.rptrack.plus.vehicleTypes.VehicleTypeFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorFragment extends Fragment implements View.OnClickListener {

    private MonitorViewModel monitorViewModel;
    SupportMapFragment mapFragment;
    GoogleMap mMap = null;
    UiSettings mapSettings;
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    ImageView imageViewGeofence, imageViewNearBy, imageViewCommand, imageViewDirection, imageShowVehicleNumber, deviceNext, devicePrev;
    ImageView refreshIcon;
    private Map<String, Marker> markersHasmap = new HashMap<>();
    int clickedVehicle = 0;
    DashboardResponse dashboardResponse;
    Chronometer refresh_time;
    TextView textAddress;
    long elpesTime = 10;
    int clickedMarkerIndex = 0;
    ArrayList<Marker> markerArrayList = new ArrayList<>();
    RefreshCalled refreshCalled;
    APIUtility apiUtility;
    Datum datum;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        monitorViewModel = new ViewModelProvider(this).get(MonitorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_monitor, container, false);
        init(root);
        apiUtility = ApplicationActivity.getApiUtility();
        refresh_time = (Chronometer) root.findViewById(R.id.refresh_time);
        refresh_time.setFormat("Refresh In : %s");
        refresh_time.setBase(SystemClock.elapsedRealtime());
        refresh_time.start();
        refresh_time.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

            }
        });
        new ViewModelProvider(this.getActivity()).get(MonitorViewModel.class).getData().observe(getViewLifecycleOwner(), dashboardResponse -> {
            this.dashboardResponse = dashboardResponse;
            if (mMap != null) {
                setDataAndMap(dashboardResponse.getResult().getData());
                refresh_time.setBase(SystemClock.elapsedRealtime());
            }

            Toast.makeText(getActivity(), dashboardResponse.getResponseMessage(), Toast.LENGTH_SHORT).show();
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshCalled = (RefreshCalled) getActivity();
    }

    OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mapSettings = mMap.getUiSettings();
            mapSettings.setMyLocationButtonEnabled(false);
            mapSettings.setRotateGesturesEnabled(false);
            mapSettings.setCompassEnabled(false);

            LatLng sydney = new LatLng(-33.852, 151.211);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //  mapSettings.setZoomControlsEnabled(true);

            mMap = googleMap;
            clickedMarkerIndex = 0;

            mMap.setInfoWindowAdapter(new CustomAdapter(getActivity()));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @SuppressLint("PotentialBehaviorOverride")
                @Override
                public boolean onMarkerClick(Marker marker) {
                    clickedMarkerIndex = Integer.parseInt(marker.getSnippet());
                    datum = (Datum) marker.getTag();
                    CommonUtils.setAddressOnTextView(getActivity(), datum.getEventdata().getLatitude(), datum.getEventdata().getLongitude(), textAddress);
                    textAddress.setTextColor(getResources().getColor(R.color.white));
                    textAddress.setVisibility(View.VISIBLE);
                    imageViewGeofence.setVisibility(View.VISIBLE);
                    imageViewNearBy.setVisibility(View.VISIBLE);
                    imageViewCommand.setVisibility(View.VISIBLE);
                    imageViewDirection.setVisibility(View.VISIBLE);
                    return false;
                }
            });

           /* googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker arg0) {
                    View v = getLayoutInflater().inflate(R.layout.custom_info, null);
                    TextView tvLat = (TextView) v.findViewById(R.id.info);
                    tvLat.setTextColor(Color.BLACK);
                    tvLat.setPadding(20, 0, 20, 0);
                    tvLat.setTypeface(tvLat.getTypeface(), Typeface.BOLD);
                    if (arg0.getTitle() == null) {
                        arg0.setVisible(false);
                    } else {
                        arg0.setVisible(true);
                        tvLat.setText(datum.getDevice().getVehicleNo() + "\nTime:" + datum.getEventdata().getTimestamp());
                    }

                    return v;
                }
            });*/

            if (dashboardResponse != null) {
                setDataAndMap(dashboardResponse.getResult().getData());
            } else {

            }

        }
    };


    @Override
    public void onClick(View v) {
        Log.d("isViewActivated", String.valueOf(v.isActivated()));
        if (v instanceof ImageView) {
            v.setSelected(!v.isSelected());
        }
        switch (v.getId()) {
            case R.id.traffic_listener:
                mMap.setTrafficEnabled(!mMap.isTrafficEnabled());
                break;
            case R.id.change_map_listener:
                mMap.setMapType(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL ? GoogleMap.MAP_TYPE_SATELLITE : GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.zoom_down:
                mMap.animateCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom - 1.0f));
                break;
            case R.id.zoom_up:
                mMap.animateCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom + 1.0f));
                break;
            case R.id.my_location:
                enableMyLocation(mMap);
                break;
            case R.id.icon_poi:
                break;
            case R.id.show_vehicle_number:
                setDataAndMap(dashboardResponse.getResult().getData());
                break;
            case R.id.icon_navigation:
                String urlNavigation = "google.navigation:q=" + datum.getEventdata().getLatitude() + "," + datum.getEventdata().getLongitude();
                Uri gmmIntentUri = Uri.parse(urlNavigation);
                Intent mapIntentNavi = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntentNavi.setPackage("com.google.android.apps.maps");
                startActivity(mapIntentNavi);
                break;
            case R.id.icon_command:
                Intent commands = new Intent(getActivity(), CommandsActivity.class);
                commands.putExtra(Constant.INTENT_SERIALIZABLE, datum);
                startActivity(commands);
                break;
            case R.id.device_next:
                markerArrayList.get(clickedMarkerIndex).showInfoWindow();
                CommonUtils.setAddressOnTextView(getActivity(), markerArrayList.get(clickedMarkerIndex).getPosition().latitude, markerArrayList.get(clickedMarkerIndex).getPosition().longitude, textAddress);
                clickedMarkerIndex++;
                break;
            case R.id.device_prev:
                markerArrayList.get(clickedMarkerIndex).showInfoWindow();
                CommonUtils.setAddressOnTextView(getActivity(), markerArrayList.get(clickedMarkerIndex).getPosition().latitude, markerArrayList.get(clickedMarkerIndex).getPosition().longitude, textAddress);
                clickedMarkerIndex++;
                markerArrayList.get(5).showInfoWindow();
                // markerArrayList.get(clickedMarkerIndex).showInfoWindow();
                break;
            case R.id.refresh_icon:
                refreshCalled.onRefreshAttempt();
                break;
            case R.id.create_geofence:
                GeofencePoints latLng = new GeofencePoints(datum.getEventdata().getLatitude(), datum.getEventdata().getLongitude());
                ArrayList<GeofencePoints> arrayLis = new ArrayList<>();
                arrayLis.add(latLng);
                CreateGeofenceReq geofenceReq = new CreateGeofenceReq(0, 0, 2, datum.getDevice().getVehicleNo(), arrayLis, 300);
                confirmationDialog(geofenceReq);
                break;
            case R.id.near_by:
                String url = "geo:" + datum.getEventdata().getLatitude() + "," + datum.getEventdata().getLongitude();
                // Uri gmmIntentUri = Uri.parse("geo:"+datum.getEventdata().getLatitude(),datum.getEventdata().getLatitude());
                Uri gmmIntentUri1 = Uri.parse(url);
                Intent mapIntent1 = new Intent(Intent.ACTION_VIEW, gmmIntentUri1);
                mapIntent1.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent1);
                break;
        }
    }


    void setDataAndMap(List<Datum> dataList) {
        if (dataList.size() > 0) {
            mMap.clear();
            markersHasmap.clear();
            int count = 0;
            LatLng lastLatLng = null;
            // for (Datum datum : dataList) {
            for (int i = 0; dataList.size() > i; i++) {
                Datum datum = dataList.get(i);
                if (datum.getResultCode() != 1) {
                    continue;
                }
                count++;
                IVehicleType iVehicleType = VehicleTypeFactory.getInstance(datum.getDevice().getVehicleType());
                if (iVehicleType == null) {
                    iVehicleType = VehicleTypeFactory.getInstance(1);
                }
                //  createMarker(datum, CommonUtils.getVehicleIcon(datum.getSubStatus(), datum.getDevice().getVehicleType())).hideInfoWindow();

                createMarker(i, datum, iVehicleType.getVehicleIcon(datum.getStatus(), datum.getSubStatus()));
                lastLatLng = new LatLng(datum.getEventdata().getLatitude(), datum.getEventdata().getLongitude());
                builder.include(lastLatLng);
            }
            if (count == 1) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 18.0f));
            } else {
                if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.O) {
                    LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
                } else {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 7.0f));
                }
            }
            mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {
                    // LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(19.0f));
                }
            });

        } else {
            Toast.makeText(getActivity(), "No Data Found !", Toast.LENGTH_SHORT).show();
        }


    }


    public Marker createMarker(int index, Datum datum, int iconResID) {
        Bitmap b = createCustomMarker(imageShowVehicleNumber.isSelected(), datum.getEventdata().getHeading().floatValue(), iconResID, datum.getDevice().getVehicleNo());
      /*  MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconGen.makeIcon(datum.getDevice().getVehicleNo()))).
                position(new LatLng(datum.getEventdata().getLatitude(),
                        datum.getEventdata().getLongitude())).anchor(0.5f, 0.5f);
        mMap.addMarker(markerOptions);*/
        Marker markerM = mMap.addMarker(new MarkerOptions().position(new LatLng(datum.getEventdata().getLatitude(),
                datum.getEventdata().getLongitude())).anchor(0.5f, 0.5f)
                .title(datum.getDevice().getVehicleNo())
                .snippet(String.valueOf(datum.getDevice().getId()))
                .icon(BitmapDescriptorFactory.fromBitmap(b)));
        markerM.setAnchor(0f, 0f);
        //  Marker markerM=mMap.addMarker(markerOptions);
        markerM.setTag(datum);
        markerM.setSnippet(String.valueOf(index));
        markersHasmap.put(datum.getDevice().getVehicleNo(), markerM);
        // myMarker = markerM;
        markerArrayList.add(markerM);
        return markerM;
    }


    private Bitmap createCustomMarker(boolean isShowBubble, float rotation, int resource, String _name) {
        View markerLayout = getLayoutInflater().inflate(R.layout.custom_cluster_layout, null);
        ImageView markerImage = (ImageView) markerLayout.findViewById(R.id.marker_image);
        ImageView markerRating = (ImageView) markerLayout.findViewById(R.id.marker_text);
        markerImage.setImageResource(resource);
        markerImage.setRotation(rotation);
        // markerRating.setText(_name);
        if (isShowBubble) {
            IconGenerator iconGen = new IconGenerator(getActivity());
            iconGen.setRotation(90);
            iconGen.setContentRotation(-90);
            markerRating.setImageBitmap(iconGen.makeIcon(_name));
            markerRating.setVisibility(View.VISIBLE);
        } else {
            markerRating.setVisibility(View.GONE);
        }


        markerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        markerLayout.layout(0, 0, markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight());
        final Bitmap bitmap = Bitmap.createBitmap(markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        markerLayout.draw(canvas);
        return bitmap;
    }

    private Datum getClickedVehicleTag() {
        Datum result = null;
        for (Map.Entry<String, Marker> entry : markersHasmap.entrySet()) {
            Marker marker = entry.getValue();
            Datum datum = (Datum) marker.getTag();
            if (datum.getDevice().getId() == clickedVehicle) {
                result = datum;
                break;
            }
        }
        return result;
    }

    private void centerMarker(String title) {
        Marker marker = markersHasmap.get(title);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14f));
    }


    @SuppressLint("ResourceType")
    private void enableMyLocation(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
                mapFragment.getView().findViewById(0x2).performClick();
            }
        } else {
            PermissionUtils.requestPermission((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation(mMap);
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }

    private void init(View root) {
        TextView textTitle = (TextView) root.findViewById(R.id.text_title);
        ImageView back_icon = (ImageView) root.findViewById(R.id.back_icon);
        back_icon.setVisibility(View.GONE);
        textAddress = root.findViewById(R.id.text_address);
        textTitle.setText(Preferences.getPreference(getActivity(), Constant.USER_NAME));
        ((ImageView) root.findViewById(R.id.change_map_listener)).setOnClickListener(this::onClick);
        ((ImageView) root.findViewById(R.id.traffic_listener)).setOnClickListener(this::onClick);
        ((ImageView) root.findViewById(R.id.icon_poi)).setOnClickListener(this::onClick);
        imageShowVehicleNumber = (ImageView) root.findViewById(R.id.show_vehicle_number);
        imageShowVehicleNumber.setOnClickListener(this::onClick);
        imageViewNearBy = (ImageView) root.findViewById(R.id.near_by);
        imageViewNearBy.setOnClickListener(this::onClick);
        imageViewNearBy.setVisibility(View.GONE);
        imageViewGeofence = ((ImageView) root.findViewById(R.id.create_geofence));
        imageViewGeofence.setOnClickListener(this::onClick);
        imageViewGeofence.setVisibility(View.GONE);
        root.findViewById(R.id.refresh_icon).setOnClickListener(this::onClick);
        ((ImageView) root.findViewById(R.id.my_location)).setOnClickListener(this::onClick);
        ((ImageView) root.findViewById(R.id.zoom_up)).setOnClickListener(this::onClick);
        ((ImageView) root.findViewById(R.id.zoom_down)).setOnClickListener(this::onClick);
        ((ImageView) root.findViewById(R.id.device_next)).setOnClickListener(this::onClick);
        ((ImageView) root.findViewById(R.id.device_prev)).setOnClickListener(this::onClick);
        imageViewCommand = ((ImageView) root.findViewById(R.id.icon_command));
        imageViewCommand.setOnClickListener(this::onClick);
        imageViewCommand.setVisibility(View.GONE);
        imageViewDirection = root.findViewById(R.id.icon_navigation);
        imageViewDirection.setOnClickListener(this::onClick);
        imageViewDirection.setVisibility(View.GONE);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(onMapReadyCallback);

    }

    void creaateGeofenceAttempt(CreateGeofenceReq createGeofenceReq) {
        apiUtility.createGeofenceAttampt(getActivity(), true, createGeofenceReq, new APIUtility.APIResponseListener<CreateGeofenceResponse>() {
            @Override
            public void onReceiveResponse(CreateGeofenceResponse response) {
                CommonUtils.alert(getActivity(), response.getGeofenceResult().getStringData());
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(getActivity(), errorResponse.getErrorMessage().getStringData());
            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(getActivity(), string);
            }
        });
    }


    public void confirmationDialog(CreateGeofenceReq createGeofenceReq) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Confirm");
        alertDialogBuilder.setMessage("Are you sure, to create a Circle \n GEO-fence(Radious: 300m) for" +
                datum.getDevice().getVehicleNo() + "?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        creaateGeofenceAttempt(createGeofenceReq);
                    }
                });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

final class CustomAdapter implements GoogleMap.InfoWindowAdapter {
    private final View mWindow;


    public CustomAdapter(Activity mContext) {
        mWindow = mContext.getLayoutInflater().inflate(R.layout.custom_info_adapter, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        render(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void render(Marker marker, View view) {
        Datum datum = (Datum) marker.getTag();

        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        titleUi.setText(datum.getDevice().getVehicleNo()
                + "\nTime :" + datum.getEventdata().getTimestamp().replace("T", " ")
                + "\nStatus : " + datum.getStatusDuration()
                + "\nSpeed : " + datum.getEventdata().getSpeed());
    }
}