package com.rptrack.plus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
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
import com.rptrack.plus.module.commands.CommandsActivity;
import com.rptrack.plus.module.device.DeviceOptionActivity;
import com.rptrack.plus.module.monitor.MonitorViewModel;
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

import static com.rptrack.plus.utilities.CommonUtils.ResizedMarker;
import static com.rptrack.plus.utilities.CommonUtils.getBearing;

public class TrackingActivity extends AppCompatActivity implements View.OnClickListener {

    //List<Datum>datum=new ArrayList<>();
    SupportMapFragment mapFragment;
    GoogleMap mMap = null;
    UiSettings mapSettings;
    LatLngBounds.Builder builder;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    ImageView imageViewGeofence, imageViewNearBy, imageViewCommand, imageViewDirection, imageShowVehicleNumber;
    private Map<String, Marker> markersHasmap = new HashMap<>();
    Chronometer refresh_time;
    long elpesTime = 10;
    private Handler handler;
    private Runnable runnable;
    int refreshTime = 10000;
    APIUtility apiUtility;
    TextView textAddress;
    Location lastPhoneLocation = null;
    FusedLocationProviderClient fusedLocationProviderClient;
    /**
     * *************LIve Tracking**************
     */

    Handler updateLocation = new Handler();
    Handler newData = new Handler();
    Datum defaultLocation = null;
    List<LatLng> polyLineList = new ArrayList<>();
    List<Datum> datumList = new ArrayList<>();
    LatLng startPosition, endPosition;
    Double lat, lng;
    Marker marker = null;
    boolean firstCall = true;
    private Polyline polyline_path;
    private float previousZoomLevel = 15.0f;
    Context context;
    private Handler rotateMarkerHandler;
    Datum selectedMarkerObj;
    DashboardResponse dashbordObj;
    int selectedVehicle;


    /**
     * *************LIve Tracking Close**************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        getSupportActionBar().hide();
        context = TrackingActivity.this;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();

        TextView textTitle = (TextView) findViewById(R.id.text_title);
        ImageView title_left = findViewById(R.id.back_icon);
        ImageView title_right = findViewById(R.id.refresh_icon);
        title_left.setVisibility(View.VISIBLE);
        title_right.setVisibility(View.GONE);
        title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        refresh_time = findViewById(R.id.refresh_time);

        init();

        Intent intent = getIntent();
        if (intent != null) {
            datumList.add((Datum) intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE));
            selectedVehicle = datumList.get(0).getDevice().getId();
            defaultLocation = (Datum) intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE);
            textTitle.setText(datumList.get(0).getDevice().getVehicleNo());
        }

        handler = new Handler();
        runnable = () -> {
            getCurrentLocation();
            getDashboardData();
            handler.postDelayed(runnable, refreshTime);
        };
        refreshTime = CommonUtils.setRefreshTime(TrackingActivity.this);
    }

    void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.d("TAG", "onSuccess: Phone Location" + new Gson().toJson(location));
                lastPhoneLocation = location;
            }
        });

    }

    OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mapSettings = mMap.getUiSettings();
            mapSettings.setMyLocationButtonEnabled(false);
            mapSettings.setCompassEnabled(false);
            mapSettings.setRotateGesturesEnabled(false);

            LatLng sydney = new LatLng(-33.852, 151.211);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //  mapSettings.setZoomControlsEnabled(true);
            builder = new LatLngBounds.Builder();
            mMap = googleMap;

            /**   **************LIve Tracking***************/
            PolylineOptions routes = new PolylineOptions().width(5).color(Color.BLUE);
            polyline_path = mMap.addPolyline(routes);
            updateLocation.post(r);

            googleMap.setInfoWindowAdapter(new CustomAdapter(TrackingActivity.this));

            /**   **************LIve Tracking Close***************/
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    CommonUtils.setAddressOnTextView(context, marker.getPosition().latitude, marker.getPosition().longitude, textAddress);
                    textAddress.setTextColor(getResources().getColor(R.color.white));
                    textAddress.setVisibility(View.VISIBLE);
                    imageViewGeofence.setVisibility(View.VISIBLE);
                    imageViewNearBy.setVisibility(View.VISIBLE);
                    imageViewCommand.setVisibility(View.VISIBLE);
                    imageViewDirection.setVisibility(View.VISIBLE);
                    return false;
                }
            });

            if (mMap != null) {
                enableMyLocation(mMap);
            }
            // setDataAndMap(datumList);
        }
    };


    /**
     * ******************LIveTracking**********************************
     */
    Runnable r = new Runnable() {
        @Override
        public void run() {


            if (mMap != null) {
                if (defaultLocation != null) {
                    Toast.makeText(TrackingActivity.this, "Run", Toast.LENGTH_SHORT).show();
                    LatLng latLng = new LatLng(defaultLocation.getEventdata().getLatitude(), defaultLocation.getEventdata().getLongitude());
                    marker = mMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromBitmap(ResizedMarker(context,
                                    defaultLocation.getSubStatus(),
                                    defaultLocation.getDevice().getVehicleType()))));
                    marker.setRotation(defaultLocation.getEventdata().getHeading().floatValue());

                    StringBuffer sb = new StringBuffer("Status: Running(" + defaultLocation.getEventdata().getSpeed() + " Km/h)");
                    if (defaultLocation.getSubStatus() != 1) {
                        sb = new StringBuffer("Status: " + defaultLocation.getStatusDuration());
                    }
                    sb.append("\nTime: " + defaultLocation.getEventdata().getTimestamp().replace("T", " "));
                    if (null != defaultLocation.getEventdata().getDI1()) {
                        if (defaultLocation.getEventdata().getDI1().equals(1)) {
                            sb.append("\nEngine: ON");
                        } else {
                            sb.append("\nEngine: OFF");
                        }
                    }
                    if (null != defaultLocation.getEventdata().getEPC()) {
                        if (defaultLocation.getEventdata().getEPC().equals(1)) {
                            sb.append("\nExternal Power: ON");
                        } else {
                            sb.append("\nExternal Power: OFF");
                        }
                    }
                    /*sb.append("\nDistance: " + CommonUtils.distance(defaultLocation.getEventdata().getLatitude(),
                            defaultLocation.getEventdata().getLatitude(),
                            phoneLocation.getLatitude(),
                            phoneLocation.getLongitude()) + " km");*/
                    marker.setTag(sb.toString());


                    markersHasmap.put(defaultLocation.getDevice().getVehicleNo(), marker);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14));
                }
                return;
            }
            updateLocation.postDelayed(this, 500);
        }
    };

    private String getMarkerInfoWindow(Datum defaultLocation) {
        StringBuffer sb = new StringBuffer("Status: Running(" + defaultLocation.getEventdata().getSpeed() + " Km/h)");
        if (defaultLocation.getSubStatus() != 1) {
            sb = new StringBuffer("Status: " + defaultLocation.getStatusDuration());
        }
        sb.append("\nTime: " + defaultLocation.getEventdata().getTimestamp().replace("T", " "));
        if (null != defaultLocation.getEventdata().getDI1()) {
            if (defaultLocation.getEventdata().getDI1().equals(1)) {
                sb.append("\nEngine: ON");
            } else {
                sb.append("\nEngine: OFF");
            }
        }
        if (null != defaultLocation.getEventdata().getEPC()) {
            if (defaultLocation.getEventdata().getEPC().equals(1)) {
                sb.append("\nExternal Power: ON");
            } else {
                sb.append("\nExternal Power: OFF");
            }
        }
        return sb.toString();
    }

    private void UpdatePoints(LatLng newlatlng) {
        List<LatLng> points = polyline_path.getPoints();
        if (!points.contains(newlatlng)) {
            points.add(newlatlng);
        }
        polyline_path.setPoints(points);
    }

    int markerPosition = 0;
    int counter = 0;

    Runnable staticCarRunnable = new Runnable() {
        @Override
        public void run() {
            if (marker != null) {
                if (polyLineList.size() - 1 > markerPosition) {
                    Log.d("MarkerSize", String.valueOf(polyLineList.size()));
                    startPosition = marker.getPosition();
                    marker.setTag(datumList.get(markerPosition));
                    GetMarker();

                    endPosition = polyLineList.get(markerPosition + 1);
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                    valueAnimator.setDuration(14000);
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    CommonUtils.LatLngInterpolator latLngInterpolator = new CommonUtils.LatLngInterpolator.LinearFixed();
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float v = valueAnimator.getAnimatedFraction();
                            LatLng newPos = latLngInterpolator.interpolate(v, startPosition, endPosition);
                            Log.d("NewPostion", String.valueOf(newPos) + " " + String.valueOf(v));
                            UpdatePoints(newPos);
                            Datum last = datumList.get(datumList.size() - 1);
                            marker.setPosition(newPos);
                            marker.setIcon(BitmapDescriptorFactory.fromBitmap(ResizedMarker(context, last.getSubStatus(), last.getDevice().getVehicleType())));
                            marker.setAnchor(0.5f, 0.5f);
                            //marker.setRotation(getBearing(startPosition, endPosition));
                            rotateMarker(marker, getBearing(startPosition, endPosition), 2000);
                            if (counter == 0) {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15f));
                            }
                            marker.setTag(getMarkerInfoWindow(datumList.get(markerPosition)));
                            marker.showInfoWindow();
                            counter++;
                        }
                    });
                    startPosition = polyLineList.get(markerPosition);
                    valueAnimator.start();
                    markerPosition++;
                }
                counter = 0;
                newData.postDelayed(this, 15000);
            } else {
                updateLocation.postDelayed(r, 500);
            }
        }
    };

    void GetMarker() {
        for (Map.Entry<String, Marker> entry : markersHasmap.entrySet()) {
            Marker marker = entry.getValue();
            Datum datum = (Datum) marker.getTag();
            if (datum.getDevice().getId() == selectedVehicle) {
                Datum datum1 = datum;
                selectedMarkerObj = datum;

             /*   CommonUtils.setAddressOnTextView(context, datum1.getEventdata().getLatitude(), datum1.getEventdata().getLongitude(), textAddress);
                textAddress.setTextColor(getResources().getColor(R.color.white));
                textTimeStamp.setText(datum1.getEventdata().getTimestamp().replace("T", " "));
                vehicleNumber.setText(datum1.getDevice().getVehicleNo());
                ViewPagerTwoAdapter adapter = new ViewPagerTwoAdapter(getActivity(), datum1);
                viewPager2.setAdapter(adapter);
                pageIndicatorView.setCount(2);
                viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                        pageIndicatorView.setSelected(position);
                    }
                });

                sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        lastBehave = newState;
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                    }
                });
                sheetBehavior.setState(lastBehave);*/
                break;
            }
        }
    }

    boolean isMarkerRotating = false;

    private void rotateMarker(final Marker marker, final float toRotation, final long duration) {
        if (!isMarkerRotating) {
            final long start = SystemClock.uptimeMillis();
            final float startRotation = marker.getRotation();
            final Interpolator interpolator = new LinearInterpolator();
            rotateMarkerHandler.post(new Runnable() {
                @Override
                public void run() {
                    isMarkerRotating = true;
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = interpolator.getInterpolation((float) elapsed / duration);
                    float rot = t * toRotation + (1 - t) * startRotation;
                    float bearing = -rot > 180 ? rot / 2 : rot;
                    marker.setRotation(bearing);
                    if (t < 1.0) {
                        rotateMarkerHandler.postDelayed(this, 0);
                    } else {
                        isMarkerRotating = false;
                    }
                }
            });
        }
    }

    boolean changeZoom = true;

    public GoogleMap.OnCameraChangeListener getCameraChangeListener() {
        return new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                if (previousZoomLevel != position.zoom) {
                    changeZoom = true;
                }
                previousZoomLevel = position.zoom;
            }
        };
    }

    /**
     * **************************LIveTracking Close
     ***********************************/
    private void init() {
        TextView textTitle = (TextView) findViewById(R.id.text_title);
        textAddress = findViewById(R.id.text_address);
        textTitle.setText(Preferences.getPreference(TrackingActivity.this, Constant.USER_NAME));
        ((ImageView) findViewById(R.id.change_map_listener)).setOnClickListener(this::onClick);
        ((ImageView) findViewById(R.id.traffic_listener)).setOnClickListener(this::onClick);
        ((ImageView) findViewById(R.id.icon_poi)).setOnClickListener(this::onClick);
        imageShowVehicleNumber = (ImageView) findViewById(R.id.show_vehicle_number);
        imageShowVehicleNumber.setOnClickListener(this::onClick);
        imageShowVehicleNumber.setVisibility(View.GONE);
        imageViewNearBy = (ImageView) findViewById(R.id.near_by);
        imageViewNearBy.setOnClickListener(this::onClick);
        imageViewNearBy.setVisibility(View.GONE);
        imageViewGeofence = ((ImageView) findViewById(R.id.create_geofence));
        imageViewGeofence.setOnClickListener(this::onClick);
        imageViewGeofence.setVisibility(View.GONE);
        ((ImageView) findViewById(R.id.my_location)).setOnClickListener(this::onClick);
        ((ImageView) findViewById(R.id.zoom_up)).setOnClickListener(this::onClick);
        ((ImageView) findViewById(R.id.zoom_down)).setOnClickListener(this::onClick);
        imageViewCommand = ((ImageView) findViewById(R.id.icon_command));
        imageViewCommand.setOnClickListener(this::onClick);
        imageViewCommand.setVisibility(View.GONE);
        ImageView device_next = findViewById(R.id.device_next);
        ImageView device_prev = findViewById(R.id.device_prev);

        imageViewDirection = findViewById(R.id.icon_navigation);
        imageViewDirection.setOnClickListener(this::onClick);
        imageViewDirection.setVisibility(View.GONE);

        device_next.setVisibility(View.GONE);
        device_prev.setVisibility(View.GONE);
        refresh_time.setVisibility(View.GONE);
        imageViewGeofence.setVisibility(View.VISIBLE);
        imageViewNearBy.setVisibility(View.VISIBLE);
        imageViewCommand.setVisibility(View.VISIBLE);
        imageViewDirection.setVisibility(View.VISIBLE);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(onMapReadyCallback);
        rotateMarkerHandler = new Handler();
        apiUtility = ApplicationActivity.getApiUtility();

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
            String datum = (String) marker.getTag();
            if (datum != null) {
                StringBuilder sb = new StringBuilder(datum);
                if (lastPhoneLocation != null) {
                    sb.append("\nDistance: " + getDistance(defaultLocation.getEventdata().getLatitude(),
                            defaultLocation.getEventdata().getLongitude(),
                            lastPhoneLocation.getLatitude(),
                            lastPhoneLocation.getLongitude()) + " km");
                }
                String title = marker.getTitle();
                TextView titleUi = ((TextView) view.findViewById(R.id.title));
                titleUi.setText(sb.toString());
            }
        }
    }

    double getDistance(double lat1, double lng1, double lat2, double lng2) {
        if (mMap == null)
            return 0;
        enableMyLocation(mMap);
        return CommonUtils.distance(lat1, lng1, lat2, lng2);
    }

    private Bitmap createCustomMarker(boolean isShowBubble, float rotation, int resource, String _name) {

        View markerLayout = getLayoutInflater().inflate(R.layout.custom_cluster_layout, null);
        ImageView markerImage = (ImageView) markerLayout.findViewById(R.id.marker_image);
        ImageView markerRating = (ImageView) markerLayout.findViewById(R.id.marker_text);
        markerImage.setImageResource(resource);
        markerImage.setRotation(rotation);
        // markerRating.setText(_name);
        if (isShowBubble) {
            IconGenerator iconGen = new IconGenerator(TrackingActivity.this);
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
            if (datum.getDevice().getId() == selectedVehicle) {
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
        if (ContextCompat.checkSelfPermission(TrackingActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {

                map.setMyLocationEnabled(true);
                mapFragment.getView().findViewById(0x2).performClick();
            }
        } else {
            PermissionUtils.requestPermission((AppCompatActivity) TrackingActivity.this, LOCATION_PERMISSION_REQUEST_CODE,
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

    public Marker createMarker(Datum datum, int iconResID) {
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
        markersHasmap.put(datum.getDevice().getVehicleNo(), markerM);
        // myMarker = markerM;
        return markerM;
    }

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
                setDataAndMap(datumList);
                break;
            case R.id.icon_navigation:
                String urlNavigation = "google.navigation:q=" + defaultLocation.getEventdata().getLatitude() + "," + defaultLocation.getEventdata().getLongitude();
                Uri gmmIntentUri = Uri.parse(urlNavigation);
                Intent mapIntentNavi = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntentNavi.setPackage("com.google.android.apps.maps");
                startActivity(mapIntentNavi);
                break;
            case R.id.icon_command:
                Intent commands = new Intent(TrackingActivity.this, CommandsActivity.class);
                commands.putExtra(Constant.INTENT_SERIALIZABLE, defaultLocation);
                startActivity(commands);
                break;
            case R.id.near_by:
                String url = "geo:" + defaultLocation.getEventdata().getLatitude() + "," + defaultLocation.getEventdata().getLongitude();
                // Uri gmmIntentUri = Uri.parse("geo:"+datum.getEventdata().getLatitude(),datum.getEventdata().getLatitude());
                Uri gmmIntentUri1 = Uri.parse(url);
                Intent mapIntent1 = new Intent(Intent.ACTION_VIEW, gmmIntentUri1);
                mapIntent1.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent1);
                break;

            case R.id.create_geofence:
                GeofencePoints latLng = new GeofencePoints(defaultLocation.getEventdata().getLatitude(), defaultLocation.getEventdata().getLongitude());
                ArrayList<GeofencePoints> arrayLis = new ArrayList<>();
                arrayLis.add(latLng);
                CreateGeofenceReq geofenceReq = new CreateGeofenceReq(0, 0, 2, defaultLocation.getDevice().getVehicleNo(), arrayLis, 300);
                confirmationDialog(geofenceReq);
                break;
        }
    }

    void getDashboardData() {
        apiUtility.DashboardResponseData(TrackingActivity.this, false, Constant.DASHBOARD_DATA_URL, new APIUtility.APIResponseListener<DashboardResponse>() {
            @Override
            public void onReceiveResponse(DashboardResponse s) {
                dashbordObj = s;

                /**  ******************LiveTracking****************/

                List<Datum> data = s.getResult().getData();
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).getDevice().getId() == selectedVehicle) {
                        if (data.get(j).getEventdata() != null) {
                            LatLng pointPosition = new LatLng(data.get(j).getEventdata().getLatitude(), data.get(j).getEventdata().getLongitude());

                            Log.d("LocationNew", String.valueOf(new Gson().toJson(pointPosition)));
                            if (!polyLineList.contains(pointPosition)) {
                                polyLineList.add(pointPosition);
                                datumList.add(data.get(j));
                            } else {
                                Toast.makeText(context, "No new location", Toast.LENGTH_SHORT).show();
                            }
                            if (firstCall == true) {
                                newData.post(staticCarRunnable);
                                firstCall = false;
                            }
                            break;
                        }
                    }
                }

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

    public void confirmationDialog(CreateGeofenceReq createGeofenceReq) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirm");
        alertDialogBuilder.setMessage("Are you sure, to create a Circle \n GEO-fence(Radious: 300m) for" +
                defaultLocation.getDevice().getVehicleNo() + "?");
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


    void setDataAndMap(List<Datum> dataList) {
        if (dataList.size() > 0) {
            mMap.clear();
            markersHasmap.clear();
            int count = 0;
            LatLng lastLatLng = null;
            for (Datum datum : dataList) {
                if (datum.getResultCode() != 1) {
                    continue;
                }
                count++;
                IVehicleType iVehicleType = VehicleTypeFactory.getInstance(datum.getDevice().getVehicleType());
                if (iVehicleType == null) {
                    iVehicleType = VehicleTypeFactory.getInstance(1);
                }
                createMarker(datum, iVehicleType.getVehicleIcon(datum.getStatus(), datum.getSubStatus())).hideInfoWindow();

                lastLatLng = new LatLng(datum.getEventdata().getLatitude(), datum.getEventdata().getLongitude());
                builder.include(lastLatLng);
            }

            if (count == 1) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 18.0f));
            } else {
                LatLngBounds bounds = builder.build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
            }

            // GetMarker();
            Datum clickedData = getClickedVehicleTag();
            if (clickedData != null) {
                centerMarker(clickedData.getDevice().getVehicleNo());
            }

            mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {
                    // LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(19.0f));
                }
            });

        } else {
            Toast.makeText(TrackingActivity.this, "No Data Found !", Toast.LENGTH_SHORT).show();
        }
    }

    void creaateGeofenceAttempt(CreateGeofenceReq createGeofenceReq) {
        apiUtility.createGeofenceAttampt(TrackingActivity.this, true, createGeofenceReq, new APIUtility.APIResponseListener<CreateGeofenceResponse>() {
            @Override
            public void onReceiveResponse(CreateGeofenceResponse response) {
                CommonUtils.alert(TrackingActivity.this, response.getGeofenceResult().getStringData());
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(TrackingActivity.this, errorResponse.getErrorMessage().getStringData());
            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(TrackingActivity.this, string);
            }
        });
    }
}