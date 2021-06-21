package com.rptrack.plus.module.device;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.maps.model.SquareCap;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.HistoryReplyResponse.HistoryReplyResponse;
import com.rptrack.plus.DataModel.HistoryReplyResponse.Position;
import com.rptrack.plus.DataModel.address.AddressResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.module.reports.IResult;
import com.rptrack.plus.module.reports.ResultFactory;
import com.rptrack.plus.module.reports.VehicleFactory;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

import java.util.ArrayList;
import java.util.List;


public class HistoryReplayActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private GoogleMap mMap;
    private Marker marker, marker1;
    private Handler handler;
    private Runnable runnable;
    private int i = 0;
    private LatLng latLng;
    private ArrayList<Marker> markerList;
    private ArrayList<LatLng> arrayPoints;
    private ImageView play_pause;
    private boolean stop = false;
    private PolylineOptions polylineOptions;
    private List<Position> results;
    private HistoryReplyResponse historyReplayResponse;
    private TextView vehicle_number, timestamp, speed_txt, farwardedText;
    private androidx.appcompat.widget.AppCompatSeekBar seek_bar;
    boolean isMarkerRotating;
    private Handler handler1;
    private SupportMapFragment mapFragment;
    private Float zoom;
    private CardView card_recent;
    private APIUtility apiUtility;
    private String address, forwarder = "2x";
    private LatLngBounds.Builder builder;
    private CircularImageView img_satellite;
    private int animateTime = 3000, m = 0, time = 4000;
    private ImageView imgForward, change_map_listener;
    private ValueAnimator valueAnimator1;
    private boolean seekPosition = false;
    private boolean start = false;
    int deviceId;
    boolean isShowMarkerWindow = false;
    TextView textAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_replay);
        setTitle("History Replay");
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        vehicle_number = (TextView) findViewById(R.id.text_title);
        textAddress = findViewById(R.id.text_address);

        ImageView backIcon = (ImageView) findViewById(R.id.back_icon);
        ImageView playback_speed_icon = (ImageView) findViewById(R.id.playback_speed_icon);
        playback_speed_icon.setVisibility(View.VISIBLE);
        playback_speed_icon.setOnClickListener(this);

        ImageView refreshIcon = (ImageView) findViewById(R.id.refresh_icon);
        refreshIcon.setImageResource(R.drawable.play_back_time_normal);
        refreshIcon.setOnClickListener(this);
        // refreshIcon.setVisibility(View.GONE);
        backIcon.setOnClickListener(v -> {
            finish();
        });

        //  timestamp = (TextView) findViewById(R.id.timestamp);
        //  vehicle_number = findViewById(R.id.vehicle_number);
        play_pause = (ImageView) findViewById(R.id.play_pause);
        change_map_listener = (ImageView) findViewById(R.id.change_map_listener);
        play_pause.setOnClickListener(this);
        change_map_listener.setOnClickListener(this);
        seek_bar = (androidx.appcompat.widget.AppCompatSeekBar) findViewById(R.id.seek_bar);
        card_recent = (CardView) findViewById(R.id.card_recent);
        imgForward = (ImageView) findViewById(R.id.rl_growSpeed);
        imgForward.setVisibility(View.GONE);
        farwardedText = (TextView) findViewById(R.id.growSpeed_txt);
        imgForward.setOnClickListener(this);
        card_recent.setVisibility(View.GONE);
        card_recent.setOnClickListener(this);

        polylineOptions = new PolylineOptions();
        results = new ArrayList<>();
        apiUtility = ApplicationActivity.getApiUtility();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        handler = new Handler();
        handler1 = new Handler();
        markerList = new ArrayList<>();
        arrayPoints = new ArrayList<>();
        isMarkerRotating = false;
        handler1 = new Handler();

        deviceId = HistoryReplayActivity.this.getIntent().getIntExtra("deviceId", 0);
        if ((HistoryReplyResponse) HistoryReplayActivity.this.getIntent().getSerializableExtra("historyReplay") != null) {
            historyReplayResponse = (HistoryReplyResponse) HistoryReplayActivity.this.getIntent().getSerializableExtra("historyReplay");
            if (historyReplayResponse != null) {
                results = historyReplayResponse.getResult().getData().getPosition();
                // timestamp.setText(results.get(0).getAppDate().getCustomerDateTime());
                vehicle_number.setText(historyReplayResponse.getResult().getData().getVehicle().getVehicleNo());
                seek_bar.setMax(results.size() - 1);

                seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mMap != null) {
                            if (!seekPosition) {
                                animateTime = 1;
                                stop = false;
                                i = seekBar.getProgress();
                                if (valueAnimator1 != null) {
                                    valueAnimator1.cancel();
                                }
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(markerList.get(progress).getPosition()));
                                // timestamp.setText(results.get(progress).getAppDate().getCustomerDateTime());
                                // speed_txt.setText(String.valueOf(results.get(progress).getSpeed()));
                                getLatLong(markerList.get(progress).getPosition());
                            }
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        seekPosition = false;
                        animateTime = 1;
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seekPosition = true;
                        stop = start;
                        m = seekBar.getProgress();
                    }
                });
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMaxZoomPreference(18f);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker.isInfoWindowShown()) {
                    isShowMarkerWindow = false;
                    marker.hideInfoWindow();
                } else {
                    isShowMarkerWindow = true;
                    marker.showInfoWindow();
                }
                return true;
            }
        });

        /*googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
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
                    tvLat.setText(arg0.getTitle() + "\nAddress : " + address);
                }
                arg0.setSnippet(address);
                return v;
            }
        });*/
        googleMap.setInfoWindowAdapter(new CustomAdapter(this));

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                if (!stop) {
                    //  card_recent.setVisibility(View.VISIBLE);
                }
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (zoom != mMap.getCameraPosition().zoom) {
                    // card_recent.setVisibility(View.VISIBLE);
                    zoom = mMap.getCameraPosition().zoom;
                }
            }
        });
        if (Preferences.getPreference_int(HistoryReplayActivity.this, Constant.MAPTYPE) == 0) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if (Preferences.getPreference_int(HistoryReplayActivity.this, Constant.MAPTYPE) == 1) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else if (Preferences.getPreference_int(HistoryReplayActivity.this, Constant.MAPTYPE) == 2) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        replay();
    }

    public Bitmap resizedMarker(int drawable) {
        int height = 90;
        int weight = 60;
        Bitmap icon = BitmapFactory.decodeResource(getResources(), drawable);
        Bitmap smallMarker = Bitmap.createScaledBitmap(icon, weight, height, false);
        return smallMarker;
    }

    void replay() {
        builder = new LatLngBounds.Builder();
        for (i = 0; i < results.size(); i++) {
            markerList.add(mMap.addMarker(new MarkerOptions().position(new LatLng(results.get(i).getLatitude(),
                    results.get(i).getLongitude())).visible(false).anchor(0.5f, 0.5f).title("Speed : " + results.get(i).getSpeed() + "\n Status : " + results.get(i).getTimestamp())
                    .icon(BitmapDescriptorFactory.fromBitmap(resizedMarker(R.drawable.playback_icon)))));
            arrayPoints.add(new LatLng(results.get(i).getLatitude(), results.get(i).getLongitude()));
            builder.include(new LatLng(results.get(i).getLatitude(), results.get(i).getLongitude()));

            if (results.size() - 1 > i) {
                if (i % 20 == 0) {
                    LatLng from = new LatLng(results.get(i).getLatitude(), results.get(i).getLongitude());
                    LatLng to = new LatLng(results.get(i + 1).getLatitude(), results.get(i + 1).getLongitude());
                    //  drawPolylineWithArrowEndcap(this, mMap, from, to);
                }

            }

        }
        i = 0;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                try {
                    LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        mMap.addMarker(new MarkerOptions().position(new LatLng(results.get(i).getLatitude(),
//                results.get(i).getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.end_red)).
//                title("Time" + " : " + results.get(i).getTimestamp().replace("T", " ") + "\n" + "Address" + " : "));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(results.get(results.size() - 1).getLatitude(),
//                results.get(results.size() - 1).getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.end_red)).
//                title("Time " + " : " + results.get(results.size() - 1).getTimestamp().replace("T", " ") + "\n" + "Address" + " : "));
        getLatLong(new LatLng(results.get(i).getLatitude(), results.get(i).getLongitude()));
        zoom = mMap.getCameraPosition().zoom;
        polylineOptions.color(Color.parseColor(Preferences.getPreference(HistoryReplayActivity.this, Constant.POLYLINE_COLOR)));
        polylineOptions.width(8);
        polylineOptions.startCap(new SquareCap());
        polylineOptions.addAll(arrayPoints);

        Polyline polyline = mMap.addPolyline(polylineOptions);
        polyline.setStartCap(new SquareCap());
        polyline.setEndCap(new SquareCap());


        i = i + 1;
        runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void run() {
                handler.postDelayed(runnable, animateTime);
                if (i < results.size()) {
                    if (stop) {
                        seekPosition = true;
                        seek_bar.setProgress(i);
                        if (results.get(i).getAppDate() != null) {
                            CommonUtils.moveCamera(markerList.get(i).getPosition(), mMap);
                            getLatLong(markerList.get(i).getPosition());
                            //  timestamp.setText(results.get(i).getAppDate().getCustomerDateTime());
                            // speed_txt.setText(String.valueOf(results.get(i).getSpeed()));
                        }
                        if (i >= m) {
                            animateTime = time;
                            farwardedText.setText(forwarder);
                        }
                        i = i + 1;
                    }
                } else {
                    handler.removeCallbacks(runnable);
                }

            }
        };
        handler.postDelayed(runnable, animateTime);
    }

    void getLatLong(LatLng latLng1) {
        if (marker != null) {
            marker.setVisible(false);
        }
        marker = markerList.get(i);
        CommonUtils.setAddressOnTextView(HistoryReplayActivity.this, marker.getPosition().latitude, marker.getPosition().longitude, textAddress);
        textAddress.setTextColor(getResources().getColor(R.color.white));
        textAddress.setVisibility(View.VISIBLE);

        // getAddressFromLatLng(marker, marker.getPosition());


        Location location = new Location("RoadPoint");
        location.setLatitude(latLng1.latitude);
        location.setLongitude(latLng1.longitude);

        if (latLng != null) {
            if (marker1 != null) {
                marker.setRotation(marker1.getRotation());
            }
            if (latLng.latitude != location.getLatitude() && latLng.longitude != location.getLongitude()) {
                if (stop) {
                    animateMarker(latLng, new LatLng(location.getLatitude(), location.getLongitude()), marker);
                }
            }
        }
        marker.setVisible(true);

        String tagMessage = "Time :" + results.get(i).getTimestamp().replace("T", " ")
                + "\nSpeed : " + results.get(i).getSpeed() + "KM/H";
        marker.setTag(tagMessage);
        if (isShowMarkerWindow) {
            marker.showInfoWindow();
        }
        latLng = latLng1;
    }

    public void animateMarker(final LatLng start, final LatLng destination, final Marker marker) {
        if (marker != null) {
            marker1 = marker;
            final CommonUtils.LatLngInterpolator latLngInterpolator = new CommonUtils.LatLngInterpolator.LinearFixed();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(animateTime);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        marker1.setAnchor(.5f, .5f);
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, start, destination);
                        marker1.setPosition(newPosition);
                        float bearing = CommonUtils.getBearing(destination, start);
                        rotateMarker(marker1, bearing, animateTime / 5);
                    } catch (Exception ex) {
                        // I don't care atm..
                    }
                }
            });
            valueAnimator.start();
            valueAnimator1 = valueAnimator;
        }
    }

    private void rotateMarker(final Marker marker, final float toRotation, final long duration) {
        if (!isMarkerRotating) {
            final long start = SystemClock.uptimeMillis();
            final float startRotation = marker.getRotation();

            final Interpolator interpolator = new LinearInterpolator();
            handler1.post(new Runnable() {
                @Override
                public void run() {
                    isMarkerRotating = true;
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = interpolator.getInterpolation((float) elapsed / duration);
                    float rot = t * toRotation + (1 - t) * startRotation;
                    float bearing = -rot > 180 ? rot / 2 : rot;
                    marker.setRotation(bearing);
                    if (t < 1.0) {
                        handler1.postDelayed(this, 0);
                    } else {
                        isMarkerRotating = false;
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.play_pause:
                if (play_pause.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.playback_play).getConstantState()) {
                    stop = true;
                    if (marker != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16f));
                    }
                    play_pause.setImageResource(R.drawable.playback_pause);
                    Toast.makeText(HistoryReplayActivity.this, "Play", Toast.LENGTH_SHORT).show();
                } else {
                    play_pause.setImageResource(R.drawable.playback_play);
                    stop = false;
                    Toast.makeText(HistoryReplayActivity.this, "Pause", Toast.LENGTH_SHORT).show();
                }
                start = stop;
                break;

            case R.id.card_recent:
                card_recent.setVisibility(View.GONE);
                if (marker1 != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(marker1.getPosition().latitude, marker1.getPosition().longitude), 14.5f));
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude), 14.5f));
                }
                zoom = 14.5f;
                break;

            case R.id.change_map_listener:
                mMap.setMapType(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL ? GoogleMap.MAP_TYPE_SATELLITE : GoogleMap.MAP_TYPE_NORMAL);
               /* if (img_satellite.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.map_satellite).getConstantState()) {
                    img_satellite.setImageResource(R.drawable.map_lite);
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else {
                    img_satellite.setImageResource(R.drawable.map_satellite);
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }*/
                break;

           /* case R.id.rl_growSpeed:
                if (animateTime == 4000) {
                    animateTime = 3000;
                    farwardedText.setText("2x");
                } else if (animateTime == 3000) {
                    animateTime = 2000;
                    farwardedText.setText("3x");
                } else if (animateTime == 2000) {
                    animateTime = 1000;
                    farwardedText.setText("4x");
                } else if (animateTime == 1000) {
                    animateTime = 500;
                    farwardedText.setText("8x");
                } else if (animateTime == 500) {
                    animateTime = 4000;
                    farwardedText.setText("1x");
                }
                time = animateTime;
                forwarder = farwardedText.getText().toString();*/
            // break;
            case R.id.playback_speed_icon:

                selectAnimateTime(true);
                break;

            case R.id.refresh_icon:

                new VehicleFactory().CreateBuilderObject(HistoryReplayActivity.this, 0, new IResult() {
                    @Override
                    public void Response(Object data, int id) {
                        finish();
                        new ResultFactory().CreateInstance(id, data, HistoryReplayActivity.this).Process();
                    }
                }, null, deviceId).showDialog();
                //   new VehicleFactory().CreateBuilderObject(HistoryReplayActivity.this, 0, (IResult) (data, id) -> new ResultFactory().CreateInstance(id, data, HistoryReplayActivity.this).Process(), datum).showDialog();

                break;
        }
    }

    void getAddressFromLatLng(final Marker marker, LatLng latlng) {
        String urlStrAddon = "na/" + "na/" + latlng.latitude + "/" + latlng.longitude;
        String url = "http://newcoreapi.gpsapphub.com/api/Geocode/Geocode/" + urlStrAddon;
        apiUtility.getAddressFromLatLan(HistoryReplayActivity.this, false, url, new APIUtility.APIResponseListener<AddressResponse>() {
            @Override
            public void onReceiveResponse(AddressResponse response) {
                address = response.getAddress();
                marker.showInfoWindow();
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                //   Toast.makeText(HistoryReplayActivity.this, errorResponse.getErrorMessage().getStringMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailureResponse(String string) {
                Toast.makeText(HistoryReplayActivity.this, string.toString(), Toast.LENGTH_SHORT).show();
            }


        });
    }

    void showDialot() {
        CharSequence[] items = {"Android M", "Android N"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {

                } else if (item == 1) {

                } else if (item == 2) {

                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 100;   //x position
        wmlp.y = 100;   //y position
        dialog.show();
    }


    public Polyline drawPolylineWithArrowEndcap(Context context,
                                                GoogleMap googleMap,
                                                LatLng fromLatLng,
                                                LatLng toLatLng) {

        int arrowColor = Color.RED; // change this if you want another color (Color.BLUE)
        int lineColor = Color.RED;

        BitmapDescriptor endCapIcon = getEndCapIcon(context, arrowColor);

        // have googleMap create the line with the arrow endcap
        // NOTE:  the API will rotate the arrow image in the direction of the line
        Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                .geodesic(true)
                .color(lineColor)
                .width(8)
                .startCap(new RoundCap())
                .endCap(new CustomCap(endCapIcon, 8))
                .jointType(JointType.ROUND)
                .add(fromLatLng, toLatLng));

        return polyline;
    }


    public BitmapDescriptor getEndCapIcon(Context context, int color) {

        // mipmap icon - white arrow, pointing up, with point at center of image
        // you will want to create:  mdpi=24x24, hdpi=36x36, xhdpi=48x48, xxhdpi=72x72, xxxhdpi=96x96
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.track_cap);

        // set the bounds to the whole image (may not be necessary ...)
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());


        // overlay (multiply) your color over the white icon
        drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        // create a bitmap from the drawable
        android.graphics.Bitmap bitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // render the bitmap on a blank canvas
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);

        // create a BitmapDescriptor from the new bitmap
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    void selectAnimateTime(boolean checkedRadio) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppDialogTheme);
        LayoutInflater layoutInflater = ((Activity) this).getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.dialog_animate_time, null);
        TextView button_submit = (TextView) customView.findViewById(R.id.submit_btn);
        TextView button_cancel = (TextView) customView.findViewById(R.id.cancel_btn);

        RadioGroup radioGroup = (RadioGroup) customView.findViewById(R.id.radioGroup);
        RadioButton radioVerySlow = (RadioButton) customView.findViewById(R.id.radio_very_slow);
        RadioButton radioSlow = (RadioButton) customView.findViewById(R.id.radio_slow);
        RadioButton radioNormal = (RadioButton) customView.findViewById(R.id.radio_normal);
        RadioButton radioFast = (RadioButton) customView.findViewById(R.id.radio_fast);
        RadioButton radioVeryFast = (RadioButton) customView.findViewById(R.id.radio_very_fast);
        //radioNormal.setChecked(true);

        switch (animateTime) {
            case 4000:
                radioVerySlow.setChecked(true);
                break;
            case 3000:
                radioSlow.setChecked(true);
                break;
            case 2000:
                radioNormal.setChecked(true);
                break;
            case 1000:
                radioFast.setChecked(true);
                break;
            case 500:
                radioVeryFast.setChecked(true);
                break;
        }


        builder.setView(customView);
        final AlertDialog alert = builder.create();
        alert.setCancelable(false);

        alert.show();

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // time = animateTime;
                // forwarder = farwardedText.getText().toString();
                stop = true;
                if (marker != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16f));
                }
                play_pause.setImageResource(R.drawable.playback_pause);
                Toast.makeText(HistoryReplayActivity.this, "Play", Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });

        button_cancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        }));
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_very_slow:
                    animateTime = 4000;
                    farwardedText.setText("2x");
                    break;
                case R.id.radio_slow:
                    animateTime = 3000;
                    farwardedText.setText("4x");
                    break;
                case R.id.radio_normal:
                    animateTime = 2000;
                    farwardedText.setText("8x");
                    break;
                case R.id.radio_fast:
                    animateTime = 1000;
                    farwardedText.setText("16x");
                    break;
                case R.id.radio_very_fast:
                    animateTime = 500;
                    farwardedText.setText("32x");
                    break;


            }
            time = animateTime;
            forwarder = farwardedText.getText().toString();
        });
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

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            titleUi.setText(datum);
        }
    }


}
