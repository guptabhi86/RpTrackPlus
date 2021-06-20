package com.rptrack.plus.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.google.maps.android.SphericalUtil;
import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.address.AddressResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.FirebaseMessaging.DatabaseHelper;
import com.rptrack.plus.FirebaseMessaging.NotificationData;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rptrack.plus.utilities.Preferences.getPreference_int;


public class CommonUtils {

  /*  public static Set<BluetoothDevice> getPairedBTDeviceList() {
        BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio
        return mBTAdapter.getBondedDevices();
    }

    public static BluetoothDevice getPairedBTDeviceList(String id) {
        BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio
        Set<BluetoothDevice> mPairedDevices = mBTAdapter.getBondedDevices();
        BluetoothDevice bluetoothDevice = null;
        for (BluetoothDevice device : mPairedDevices) {
            if (device.getName().equals(id)) {
                bluetoothDevice = device;
                break;
            }
        }
        return bluetoothDevice;
    }
*/
    public static void setMapType(GoogleMap gMap, int selectedMap) {
        switch (selectedMap) {
            case 0:
                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case 3:
                gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                gMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }
    }

    /*public static void getAddressFromLatLong(double lat, double lng, final TextView address1, Context mContaxt, APIUtility apiUtility) {
        address1.setText("Address Loading...");


        String urlStrAddon = "na/" + "na/" + lat + "/" + lng;
        String url = "http://newcoreapi.gpsapphub.com/api/Geocode/Geocode/" + urlStrAddon;
        Log.d("AddressSucess5", url);

        apiUtility.getAddressFromLatLan(mContaxt, false, url, new APIUtility.APIResponseListener<AddressResponse>() {
            @Override
            public void onReceiveResponse(AddressResponse response) {

                address1.setText(response.getAddress());
            }

            @Override
            public void onErrorResponse(ErrorResponse string) {
                address1.setText("No Address Found");
            }

            @Override
            public void onFailureResponse(String string) {

                Log.d("AddressSucess2", string);

                address1.setText(string);
            }
        });
    }*/

    public static int getVehicleIcon(int vehicleStatus, int vehicleType) {
        int drawable = R.drawable.rp_marker_car_gray;
        switch (vehicleStatus) {
            case 1:
                switch (vehicleType) {
                    case 1:
                        drawable = R.drawable.rp_marker_car_green;
                        break;
                    case 2:
                        drawable = R.drawable.rp_marker_bus_green;
                        break;
                    case 3:
                        drawable = R.drawable.rp_marker_truck_green;
                        break;
                    case 4:
                        drawable = R.drawable.rp_marker_bike_green;
                        break;
                    case 10:
                        break;
                }
                break;
            case 2:
                switch (vehicleType) {
                    case 1:
                        drawable = R.drawable.rp_marker_car_red;
                        break;
                    case 2:
                        drawable = R.drawable.rp_marker_bus_red;
                        break;
                    case 3:
                        drawable = R.drawable.rp_marker_truck_red;
                        break;
                    case 4:
                        drawable = R.drawable.rp_marker_bike_red;
                        break;

                }
                break;
            case 3:
                switch (vehicleType) {
                    case 1:
                        drawable = R.drawable.rp_marker_car_blue;
                        break;
                    case 2:
                        drawable = R.drawable.rp_marker_bus_blue;
                        break;
                    case 3:
                        drawable = R.drawable.rp_marker_truck_blue;
                        break;
                    case 4:
                        drawable = R.drawable.rp_marker_bike_blue;
                        break;

                }
                break;

            default:
                switch (vehicleType) {
                    case 1:
                        drawable = R.drawable.rp_marker_car_gray;
                        break;
                    case 2:
                        drawable = R.drawable.rp_marker_bus_gray;
                        break;
                    case 3:
                        drawable = R.drawable.rp_marker_truck_gray;
                        break;
                    case 4:
                        drawable = R.drawable.rp_marker_bike_gray;
                        break;
                }

        }

        return drawable;
    }

   /* public static int getVehicleFromView(int vehicleStatus, int vehicleType) {
        int drawable = R.drawable.red_car_f;
        switch (vehicleStatus) {
            case 1:
                switch (vehicleType) {
                    case 1:
                        drawable = R.drawable.green_car_f;
                        break;
                    case 2:
                        drawable = R.drawable.green_bus_f;
                        break;
                    case 3:
                        drawable = R.drawable.green_truck_f;
                        break;
                    case 4:
                        drawable = R.drawable.green_bike_f;
                        break;
                }
                break;
            case 2:
                switch (vehicleType) {
                    case 1:
                        drawable = R.drawable.red_car;
                        break;
                    case 2:
                        drawable = R.drawable.red_bus;
                        break;
                    case 3:
                        drawable = R.drawable.red_truck;
                        break;
                    case 4:
                        drawable = R.drawable.red_bike;
                        break;

                }
                break;
            case 3:
                switch (vehicleType) {
                    case 1:
                        drawable = R.drawable.red_car;
                        break;
                    case 2:
                        drawable = R.drawable.yellow_bus;
                        break;
                    case 3:
                        drawable = R.drawable.yellow_bus;
                        break;
                    case 4:
                        drawable = R.drawable.yellow_bike;
                        break;

                }
                break;

            default:
                switch (vehicleType) {
                    case 1:
                        drawable = R.drawable.car_grey;
                        break;
                    case 2:
                        drawable = R.drawable.gray_bus;
                        break;
                    case 3:
                        drawable = R.drawable.gray_truck;
                        break;
                    case 4:
                        drawable = R.drawable.gray_bike;
                        break;
                }

        }

        return drawable;
    }*/

    public static int setRefreshTime(Context context) {
        int refreshTime = 15000;
        if (getPreference_int(context, Constant.REFRESHTIME) == 0) {
            refreshTime = 10000;
        } else if (getPreference_int(context, Constant.REFRESHTIME) == 1) {
            refreshTime = 20000;
        } else if (getPreference_int(context, Constant.REFRESHTIME) == 2) {
            refreshTime = 30000;
        } else if (getPreference_int(context, Constant.REFRESHTIME) == 3) {
            refreshTime = 45000;
        } else if (getPreference_int(context, Constant.REFRESHTIME) == 4) {
            refreshTime = 60000;
        } else {
            refreshTime = 15000;
        }
        return refreshTime;
    }

  /*  public static void createCustomAnimation(FloatingActionMenu menuRed) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menuRed.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menuRed.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menuRed.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menuRed.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menuRed.getMenuIconView().setImageResource(menuRed.isOpened()
                        ? R.drawable.filter_icon : R.drawable.close_icon);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menuRed.setIconToggleAnimatorSet(set);
    }*/

    public static String parsedateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        //String outputPattern = "dd-MMM-yyyy";
        String outputPattern = "MMM dd, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToddMMyyyy(String time) {
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(time);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }


    public static String getRealPathFromURI(Context mContaxt, Uri contentURI) {
        String filePath;
        Cursor cursor = mContaxt.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            filePath = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }
        return filePath;
    }

    public static void setAddressOnTextView(Context mContext, double lat, double lng, final TextView address1) {
        APIUtility apiUtility = ApplicationActivity.getApiUtility();
        address1.setText("Address Loading...");
        address1.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        String urlStrAddon = "na/" + "na/" + lat + "/" + lng;
        String url = "http://newcoreapi.gpsapphub.com/api/Geocode/Geocode/" + urlStrAddon;
        apiUtility.getAddressFromLatLan(mContext, false, url, new APIUtility.APIResponseListener<AddressResponse>() {
            @Override
            public void onReceiveResponse(AddressResponse response) {
                address1.setText(response.getAddress());
            }

            @Override
            public void onErrorResponse(ErrorResponse errorMessage) {
                address1.setText("No Address Found");
            }

            @Override
            public void onFailureResponse(String string) {
                address1.setText(string);
            }
        });
    }

    public static void saveNotification(Context context, NotificationData notificationData1) {
        DatabaseHelper db;
        db = new DatabaseHelper(context);
        db.insertNote(notificationData1.getDeviceId(),
                notificationData1.getBody(),
                notificationData1.getTitle(),
                String.valueOf(notificationData1.isCalling()),
                String.valueOf(notificationData1.isVoice()),
                notificationData1.getVehicleNo(),
                notificationData1.getImage(),
                notificationData1.getDate().replace("T", " "),
                notificationData1.getDuration(),
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                0, notificationData1.getAlertType());
        CommonUtils.setBadge(context, getPreference_int(context, Constant.BADGECOUNT));
    }

    public static boolean isBetweetTwoTimes(String fromTimeString, String toTimeString, int isEnable) {
        boolean result = false;
        try {
            if (intToBool(isEnable)) {
                return true;
            }
            SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
            Date fromTime = sf.parse(fromTimeString);
            Calendar fromT = Calendar.getInstance();
            fromT.setTime(fromTime);
            fromT.add(Calendar.DATE, 1);

            Date toTime = sf.parse(toTimeString);
            Calendar toT = Calendar.getInstance();
            toT.setTime(toTime);
            toT.add(Calendar.DATE, 1);

            Date currentTime = sf.parse(sf.format(Calendar.getInstance().getTime()));
            Calendar currentT = Calendar.getInstance();
            currentT.setTime(currentTime);
            currentT.add(Calendar.DATE, 1);
            result = currentT.getTime().before(fromT.getTime()) && currentT.getTime().after((toT.getTime()));
        } catch (java.text.ParseException ex) {

        }
        return result;
    }

    public static boolean intToBool(int vale) {
        boolean isChecked = false;
        if (vale == 1) {
            isChecked = true;
        }
        return isChecked;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                componentInfo = taskInfo.get(0).topActivity;
            }
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;

    }

    public static void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }


    private static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }



 /*
    public static void validateRefreshTOken(final Activity context, final TokenRefreshedCallback tokenRefreshedCallback, final int requestCodec){

        String refreshToken=Preferences.getPreference(context,Constant.REFRESH_TOKEN);
        APIUtility apiUtility=ApplicationActivity.apiUtility;
        RefreshTokenRequest refreshTokenRequest=new RefreshTokenRequest(refreshToken);
        apiUtility.refreshTokenService(context, true, refreshTokenRequest, new APIUtility.APIResponseListener<RefreshTokenResponse>() {
            @Override
            public void onReceiveResponse(RefreshTokenResponse response) {
                Preferences.setPreference(context,Constant.USER_NAME,response.getResult().getUserName());
                Preferences.setPreference(context,Constant.ORG_NAME,response.getResult().getOrgName());
                Preferences.setPreference(context,Constant.ACCESS_TOKEN,response.getResult().getAccess_token());
                Preferences.setPreference(context,Constant.TOKEN_TIMESTAMP, CommonUtils.getCurrentTimeStamp());
                Preferences.setPreference(context,Constant.REFRESH_TOKEN,response.getResult().getRefresh_token());
                Preferences.setPreference_int(context,Constant.ACCESS_TOKEN_VALIDITY,response.getResult().getExpires_in());
                Preferences.setPreference_int(context,Constant.REFRESH_TOKEN_VALIDITY, Integer.parseInt(response.getResult().getRefreshTokenTimeout()));
                Preferences.setPreference_int(context,Constant.USER_TYPE,response.getResult().getUserType());
                if (tokenRefreshedCallback!=null){
                }
            }



            @Override
            public void onErrorResponse(ErrorResponse errorMessage) {


                new AlertDialog.Builder(context)
                        .setTitle((R.string.app_name))
                        .setMessage(errorMessage.getMessage())
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Preferences.removePreference(context,Constant.USER_TYPE);
                                Preferences.removePreference(context,Constant.USER_NAME);
                                Preferences.removePreference(context,Constant.REFRESH_TOKEN);
                                Preferences.removePreference(context,Constant.ACCESS_TOKEN);

                                Intent intent=new Intent(context, LoginActivity.class);;
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                context.finish();

                            }
                        }).create().show();

            }

            @Override
            public void onFailureResponse(String string) {

            }
        });
    }*/

    public interface LatLngInterpolator {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolator {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                // Take the shortest path across the 180th meridian.
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }


    public static ApplicationDate isValidDate(String date) {
        ApplicationDate result = new ApplicationDate();
/*
        String customerDateFormat= getPreference(ApplicationActivity.getContext(), Constants.CUSTOMER_DATE);
        String customerDateTimeFormat= getPreference(ApplicationActivity.getContext(), Constants.CUSTOMER_DATE_TIME);

        if(!customerDateFormat.isEmpty()){
            result.setCustomerDateFormat(customerDateFormat);
        }
        if(!customerDateTimeFormat.isEmpty()){
            result.setCustomerDateTimeFormat(customerDateTimeFormat);
        }
*/

        result.setValid(false);
        try {
            result.setDate(new SimpleDateFormat(result.getJsonDateFormat()).parse(date));
            if (result.getDate().toString().contains("1970") || date.contains("0001")) {
                result.setValid(false);
            } else {
                result.setValid(true);
            }
        } catch (Exception ex) {

        } finally {
            return result;
        }
    }

    public static void moveCamera(LatLng destination, GoogleMap mMap) {
        Projection projection = mMap.getProjection();
        LatLngBounds bounds = projection.getVisibleRegion().latLngBounds;
        int boundsTopY = projection.toScreenLocation(bounds.northeast).y;
        int boundsBottomY = projection.toScreenLocation(bounds.southwest).y;
        int boundsTopX = projection.toScreenLocation(bounds.northeast).x;
        int boundsBottomX = projection.toScreenLocation(bounds.southwest).x;

        int offsetY = (boundsBottomY - boundsTopY) / 2;
        int offsetX = (boundsTopX - boundsBottomX) / 2;

        Point destinationPoint = projection.toScreenLocation(destination);
        int destinationX = destinationPoint.x;
        int destinationY = destinationPoint.y;

        int scrollX = 0;
        int scrollY = 0;

        if (destinationY <= (boundsTopY + offsetY)) {
            scrollY = -(Math.abs((boundsTopY + offsetY) - destinationY));
        } else if (destinationY >= (boundsBottomY - offsetY)) {
            scrollY = (Math.abs(destinationY - (boundsBottomY - offsetY)));
        }
        if (destinationX >= (boundsTopX - offsetX)) {
            scrollX = (Math.abs(destinationX - (boundsTopX - offsetX)));
        } else if (destinationX <= (boundsBottomX + offsetX)) {
            scrollX = -(Math.abs((boundsBottomX + offsetX) - destinationX));
        }
        mMap.animateCamera(CameraUpdateFactory.scrollBy(scrollX, scrollY));

        //  marker.setPosition(destination);
    }

    public static float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);
        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }

    public static double roundTwoDecimals(double doub) {
//round off decimal to int
        double dd = Math.round(doub);
        DecimalFormat twoDForm = new DecimalFormat("##");
        return Double.valueOf(twoDForm.format(dd));
    }

    //for show only 2 decimal
    public static double roundTwoDecimals1(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Gingerbread or later.
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Honeycomb or later.
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Honeycomb MR1 or later.
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * Uses static final constants to detect idiaf the device's platform version is
     * ICS or later.
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * This Method is Checked that network is Available or Not If available
     * Result Will be True If not available Result Will be False
     */
    public static boolean isNetworkAvailable(Context mContext) {
        /* getting systems Service connectivity manager */
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (mConnectivityManager != null) {
            NetworkInfo[] mNetworkInfos = mConnectivityManager.getAllNetworkInfo();
            if (mNetworkInfos != null) {
                for (NetworkInfo mNetworkInfo : mNetworkInfos) {
                    if (mNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static LatLngBounds toBounds(LatLng center, double radiusInMeters) {
        double distanceFromCenterToCorner = radiusInMeters * Math.sqrt(2.0);
        LatLng southwestCorner =
                SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 225.0);
        LatLng northeastCorner =
                SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 45.0);
        return new LatLngBounds(southwestCorner, northeastCorner);
    }


    public static Bitmap ResizedMarker(Context context, int vehicleStatus, int vehicleType) {
        int height = 85;
        int weight = 50;
        Bitmap icon = ((BitmapDrawable) context.getResources().getDrawable(CommonUtils.getVehicleIcon(vehicleStatus, vehicleType))).getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(icon, weight, height, false);
        return smallMarker;
    }

    // public static void displayIntenetAlert(final Context context, final
    // String message, final boolean isFinish) {
    // AlertDialog.Builder builder = new
    // AlertDialog.Builder(context,R.style.dialog_light);
    //
    // builder.setTitle(context.getResources().getString(R.string.app_name));
    // builder.setMessage(message);
    // builder.setCancelable(false);
    // builder.setPositiveButton("Retry", new DialogInterface.OnClickListener()
    // {
    //
    // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    // }
    // });
    // builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
    //
    // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // if (isFinish)
    // {
    // dialog.dismiss();
    // ((Activity) context).finish();
    //
    // } else {
    // dialog.dismiss();
    //
    // }
    //
    // }
    // });
    //
    // builder.show();
    // }


    public static void log(Context c, String tag, String msg) {

        if (Constant.isDebug) {
            Log.d(tag, msg);
        }
    }


    public static String getParamsToUrl(String Url,
                                        LinkedHashMap<String, String> ParamsLinked) {
        // Using StringBuffer append for better performance.
        StringBuilder combinedParams = new StringBuilder();
        if (!ParamsLinked.isEmpty()) {

            for (Map.Entry<String, String> entry : ParamsLinked.entrySet()) {
                try {
                    combinedParams.append(combinedParams.length() > 1 ? "&" : "").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return Url + combinedParams.toString();
    }

    public static boolean isInternetReachable() {
        try {
            // make a URL to a known source
            URL url = new URL("http://www.google.com");

            // open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection) url
                    .openConnection();

            // trying to retrieve data from the source. If there
            // is no connection, this line will fail
            urlConnect.setConnectTimeout(5 * 1000);
            urlConnect.getContent();

        } catch (UnknownHostException e) {

            e.printStackTrace();
            return false;
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.getAdapter().getView(position, null, listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    // public static void addShortcut(Context context, String AppIconClickClass)
    // {
    // Intent shortcut = new Intent(
    // "com.android.launcher.action.INSTALL_SHORTCUT");
    //
    // ApplicationInfo appInfo = context.getApplicationInfo();
    //
    // // Shortcut name
    // shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
    // context.getString(R.string.app_name));
    // shortcut.putExtra("duplicate", false); // Just create once
    //
    // // Setup activity shoud be shortcut object
    // ComponentName component = new ComponentName(appInfo.packageName,
    // AppIconClickClass);
    // shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
    // Intent.ACTION_MAIN).setComponent(component));
    //
    // // Set shortcut icon
    // ShortcutIconResource iconResource = Intent.ShortcutIconResource
    // .fromContext(context, appInfo.icon);
    // shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
    //
    // context.sendBroadcast(shortcut);
    // }

    // public static void deleteShortcut(Context context, String
    // AppIconClickClass) {
    // Intent shortcut = new Intent(
    // "com.android.launcher.action.UNINSTALL_SHORTCUT");
    // ApplicationInfo appInfo = context.getApplicationInfo();
    //
    // // Shortcut name
    // shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
    // context.getString(R.string.app_name));
    //
    // ComponentName comp = new ComponentName(appInfo.packageName,
    // AppIconClickClass);
    // shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
    // Intent.ACTION_MAIN).setComponent(comp));
    //
    // context.sendBroadcast(shortcut);
    // }

    // public static long createRandomInteger(int aStart, long aEnd, Random
    // aRandom) {
    // if (aStart > aEnd) {
    // throw new IllegalArgumentException("Start cannot exceed End.");
    // }
    // // get the range, casting to long to avoid overflow problems
    // long range = aEnd - (long) aStart + 1;
    // // compute a fraction of the range, 0 <= frac < range
    // long fraction = (long) (range * aRandom.nextDouble());
    // long randomNumber = fraction + (long) aStart;
    //
    // if (Consts.IS_DEBUG)
    // Log.v("", "Generated : " + randomNumber);
    //
    // return randomNumber;
    // }

    // public static void DeleteFolderRecursive(File dir) {
    // if (dir.isDirectory()) {
    // String[] children = dir.list();
    // for (int i = 0; i < children.length; i++) {
    // File temp = new File(dir, children[i]);
    // DeleteFolderRecursive(temp);
    // }
    // }
    // if (dir.delete() == false) {
    //
    // if (Consts.IS_DEBUG)
    // Log.e("DeleteRecursive", "DELETE FAIL");
    // }
    // }

    // getHeight of status bar
    public static int getStatusBarHeight(Context Con) {
        int result = 0;
        int resourceId = Con.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = Con.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    // Calculate ActionBar height

    public static int getActionBarBarHeight(Context Con) {
        int result = 0;
        TypedValue tv = new TypedValue();
        if (Con.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv,
                true)) {
            result = TypedValue.complexToDimensionPixelSize(tv.data, Con
                    .getResources().getDisplayMetrics());
        }

        return result;
    }

    // getHeight of NavigationBar bar
    public static int getNavigationBarHeight(Context Con) {
        int result = 0;

        Resources resources = Con.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;

    }

    public static Bitmap makeBitmap(String path, Context mContext, int width,
                                    int height) {
        int orient = 0;

        /*
         * Display dispDefault = ((WindowManager)
         * mContext.getSystemService(Context.WINDOW_SERVICE))
         * .getDefaultDisplay(); DisplayMetrics dispMetrics = new
         * DisplayMetrics(); dispDefault.getMetrics(dispMetrics);
         *
         * Point mPoint = CommonUtils.getDisplaySize(dispDefault);
         */
        Resources resource = mContext.getResources();
        // int screenWidth = resource.getDisplayMetrics().widthPixels;
        // int screenHeight = resource.getDisplayMetrics().heightPixels;

        try {
            ExifInterface ei = new ExifInterface(path);
            orient = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Old code for load bitmaps
        // BitmapFactory.Options opt = new BitmapFactory.Options();
        // opt.inSampleSize = 4;
        // Bitmap image = BitmapFactory.decodeFile(path, opt);

        Bitmap image = decodeSampledBitmapFromResource(path, resource, 1,
                width, height);

        Matrix matrix = new Matrix();
        switch (orient) {
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            default:
                matrix.postRotate(0);
        }
        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(),
                image.getHeight(), matrix, true);
        matrix.reset();

        float scaleWidth = ((float) width) / image.getWidth();
        float scaleHeight = ((float) height) / image.getHeight();
        float scale = Math.min(scaleWidth, scaleHeight);
        matrix.postScale(scale, scale);

        return Bitmap.createBitmap(image, 0, 0, image.getWidth(),
                image.getHeight(), matrix, true);
    }

    // public static int calculateInSampleSize(BitmapFactory.Options options,
    // int reqWidth, int reqHeight) {
    // // Raw height and width of image
    // final int height = options.outHeight;
    // final int width = options.outWidth;
    // int inSampleSize = 1;
    //
    // if (height > reqHeight || width > reqWidth) {
    //
    // final int halfHeight = height / 2;
    // final int halfWidth = width / 2;
    //
    // // Calculate the largest inSampleSize value that is a power of 2 and
    // // keeps both
    // // height and width larger than the requested height and width.
    // while ((halfHeight / inSampleSize) > reqHeight
    // && (halfWidth / inSampleSize) > reqWidth) {
    // inSampleSize *= 2;
    // }
    // }
    //
    // return inSampleSize;
    // }

    // url = file path or whatever suitable URL you want.
    public static String getMimeType(Context context, Uri uri) {
        String type = "";
        if (uri != null) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.MediaColumns.MIME_TYPE}, null,
                    null, null);

            if (cursor != null && cursor.moveToNext()) {
                type = cursor.getString(0);
            }

            return type;
        }
        return type;
    }

    public static Bitmap decodeSampledBitmapFromResource(String path,
                                                         Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // BitmapFactory.decodeResource(res, resId, options);
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * this method is for getting Display Dimensions Weather this method is
     * Deprecated or not
     **/
    @SuppressLint("NewApi")
    public static Point getDisplaySize(final Display display) {
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }

    public static String getDateDifference(Date thenDate) {
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        now.setTime(new Date());
        then.setTime(thenDate);

        // Get the represented date in milliseconds
        long nowMs = now.getTimeInMillis();
        long thenMs = then.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = nowMs - thenMs;

        // Calculate difference in seconds
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);


        if (diffMinutes < 60) {
            if (diffMinutes == 1)
                return diffMinutes + " minute ago";
            else
                return diffMinutes + " minutes ago";
        } else if (diffHours < 24) {
            if (diffHours == 1)
                return diffHours + " hour ago";
            else
                return diffHours + " hours ago";
        } else if (diffDays < 30) {
            if (diffDays == 1)
                return diffDays + " day ago";
            else
                return diffDays + " days ago";
        } else if ((diffDays / 30) >= 2 && (diffDays / 30) < 12) {
            return String.format("%d months ago", (diffDays / 30));
        } else if ((diffDays / 365) > 1) {
            return String.format("%d years ago", (diffDays / 365));
        } else {
            return "a long time ago..";
        }
    }

    public static boolean CalculateTimeDifferenceInTermsofMinute(String StartDate, String StopDate) {
        boolean check = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            Date oldDate = null;
            Date currentDate = null;
            try {
                oldDate = dateFormat.parse(StartDate);
                currentDate = dateFormat.parse(StopDate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            System.out.println(oldDate);

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            //     long minutes = seconds / 60;
            //   long hours = minutes / 60;

            Log.e("dirrdgaslgl", String.valueOf(seconds));

            if (seconds >= 0) {
                check = true;
            }

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return check;
    }

    /**
     * @param view EditText
     * @return true/false
     * @category EditText Validation
     */
    public static boolean isEditValid(EditText view) {
        boolean isValid = false;

        EditText mEditText = view;

        if (mEditText.getText().toString().trim().length() > 0) {
            isValid = true;
        }

        return isValid;
    }

    /*
     * isNameValid function is check wheather given text contains any numeric
     * value or special character if it contains numeric value or special
     * character it will return false
     */

    public static boolean isNameValid(String name) {
        boolean isValid = true;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(Character.isSpaceChar(c) || Character.isLetter(c))) {
                isValid = false;
            }

        }
        return isValid;
    }

    /**
     * @param email as a String
     * @return true/false
     * @category Email Validation
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isPasswordValid(String password) {
        boolean isValid = false;
        if (password.length() > 4) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isValidMobile(EditText phoneEditText) {
        boolean check;
        if (phoneEditText.getText().toString().trim().length() < 6 || phoneEditText.getText().toString().trim().length() > 13) {
            check = false;
        } else {
            check = true;
        }
        return check;
    }

    public static boolean isValidMobNumber(String phoneNumber) {
        boolean check;
        if (phoneNumber.trim().length() == 10) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }


    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String MillisecondsToDate(long millis) {
        String returnTime;
        // SimpleDateFormat formatter = new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        // formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        returnTime = df.format(c.getTime());
        return returnTime;
    }

    public static void CopyRAWtoSDCard(Context mContext, int id, String path)
            throws IOException {
        InputStream in = mContext.getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }


    public static boolean isDateBefore(String startDate, String endDate) {
        try {
            String myFormatString = "yyyy-MM-dd"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (startingDate.before(date1))
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return date in specified format.
     *
     * @param milliSeconds Date in milliseconds
     * @param dateFormat   Date format
     * @return String representing date in specified format
     */
    public static String MillisecondsToHeaderDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    public static String Base64Decoder(String base64string) {
        byte[] data = Base64.decode(base64string, Base64.DEFAULT);
        String text = "";
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text.trim();

    }

    // public static String getHttpCall(String url) {
    // String result = "";
    // try {
    // RestClient mRestClient = new RestClient(url);
    // result = mRestClient.execute(RestClient.RequestMethod.GET);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return result;
    // }

    /**
     * Add this code to print out the key hash, and use that KeyHash
     *
     * @return
     */
    // public static String getFaceBookHashKey(Context Con) {
    // try {
    // String str = "";
    // PackageInfo info = Con.getPackageManager().getPackageInfo(
    // Con.getPackageName(), PackageManager.GET_SIGNATURES);
    // for (Signature signature : info.signatures) {
    // MessageDigest md = MessageDigest.getInstance("SHA");
    // md.update(signature.toByteArray());
    //
    // if (Consts.IS_DEBUG)
    // Log.e("KeyHash:",
    // Base64.encodeToString(md.digest(), Base64.DEFAULT));
    //
    // str = Base64.encodeToString(md.digest(), Base64.DEFAULT);
    // return str;
    // }
    // } catch (NameNotFoundException e) {
    // e.printStackTrace();
    //
    // } catch (NoSuchAlgorithmException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }
    public static void displayNetworkAlert(final Context context, final boolean isFinish) {

        new AlertDialog.Builder(context)
                .setMessage("Please Check Your Internet Connection and Try Again")
                .setTitle("Network Error")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (isFinish) {
                                    dialog.dismiss();
                                    ((Activity) context).finish();
                                } else {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
    }

    public static String getDate(String timestamp) {
        Date oneWayTripDate;
        String formatedDate = "";
        if (!TextUtils.isEmpty(timestamp)) {
            try {
                String time[] = timestamp.split(" ");
                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
                oneWayTripDate = input.parse(time[0]);
                formatedDate = output.format(oneWayTripDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return formatedDate;
    }

    public static int getExpireDays(String date) {
        SimpleDateFormat dfDate = new SimpleDateFormat("MMM dd, yyyy");
        Date d = null;
        Date d1 = null;
        int diffInDays;

        Calendar cal = Calendar.getInstance();
        try {
            d = dfDate.parse(date);
            d1 = dfDate.parse(dfDate.format(cal.getTime()));// Returns
            // 15/10/2012
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));

        return diffInDays;

    }

    public static Bitmap getBitmapFromPath(String FilePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(FilePath, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        if (imageWidth > imageHeight) {
            options.inSampleSize = calculateInSampleSize(options, 512, 256);// if
            // landscape
        } else {
            options.inSampleSize = calculateInSampleSize(options, 256, 512);// if
            // portrait
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(FilePath, options);
    }

    public static Bitmap getBitmapThumbnailsFromPath(String FilePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(FilePath, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        if (imageWidth > imageHeight) {
            options.inSampleSize = calculateInSampleSize(options, 512, 256);// if
            // landscape
        } else {
            options.inSampleSize = calculateInSampleSize(options, 256, 512);// if
            // portrait
        }
        options.inJustDecodeBounds = false;
        // return BitmapFactory.decodeFile(FilePath, options);
        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeFile(FilePath, options), 64, 64, false);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static String removeZeroFromDate(String date) {
//		Log.e("Original Date---", "" + date);
        String[] separated = date.split(" ");
        String s = separated[1];
        String remove = "";
        if (s.startsWith("0")) {
//			Log.e("Comp", "Comp---" + s.substring(1, s.length() - 1));
            remove = s.substring(1, s.length() - 1);
//			Log.e("Seprated[0]", "" + separated[0]);
//			Log.e("s", "" + remove);
//			Log.e("Final String---", "" + separated[0] + " " + remove + ","
//					+ " " + separated[2]);
            return separated[0] + " " + remove + "," + " " + separated[2];
        } else {
//			Log.e("Else", "Else");
//			Log.e("Not Chaged Date---", "" + date);
            return date;
        }

    }

    public static void AlertDialogDefault(Context mContext, String Title,
                                          String Message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        alertDialogBuilder.setTitle(Title);
        alertDialogBuilder.setMessage(Message).setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public static int timeDiff(String time) {
        int min = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            int HH = calendar.get(Calendar.HOUR_OF_DAY);
            int mm = calendar.get(Calendar.MINUTE);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date date1 = simpleDateFormat.parse(HH + ":" + mm);
            Date date2 = simpleDateFormat.parse(time);

            long difference = date2.getTime() - date1.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return min;
    }

//	public static void getTokenForNotification(final Context _context,Intent i)
//	{
//
//		ParseAnalytics.trackAppOpened(i);
//		ParseInstallation.getCurrentInstallation().saveInBackground(
//				new SaveCallback() {
//
//					@Override
//					public void done(ParseException arg0)
//					{
//						if (arg0 == null)
//						{
//							try
//							{
//								if(Consts.isDebug)
//									Log.e("Splashscreen","Android id-->"+ ParseInstallation.getCurrentInstallation().get("deviceToken").toString());
//
//								Global.KEY_DEVICETOKEN = ParseInstallation.getCurrentInstallation().get("deviceToken").toString();
//
//								_context.getSharedPreferences(Global.KEY_DEVICETOKEN, 0)
//										.edit()
//										.putString(Global.KEY_DEVICETOKEN,Global.KEY_DEVICETOKEN).commit();
//
//								Preferences.setPreference(_context, PrefEntity.DEVICETOKEN, Global.KEY_DEVICETOKEN);
//
//								ComponentName receiver = new ComponentName(_context, GcmBroadcastReceiver.class);
//								PackageManager pm = _context.getPackageManager();
//
//
//							}
//							catch (Exception e)
//							{
//								e.printStackTrace();
//							}
//						}
//					}
//				});
//
//	}

    public static String encodeString(String string) {
        String s = "";
        if (!string.equals("")) {
            //encoding  byte array into base 64
            byte[] encoded = Base64.encode(string.getBytes(), Base64.DEFAULT);
            s = new String(encoded);
        }
        return s.trim();
    }

    public static String twoTimeDifference(String startTime, String endTime) {
        int min = 0;
        String returnTime = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date date1 = simpleDateFormat.parse(startTime);
            Date date2 = simpleDateFormat.parse(endTime);

            long difference = date2.getTime() - date1.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);

            if (hours > 0) {
                returnTime = "" + hours + " hrs";
            } else if (min > 0) {
                returnTime = "" + min + " mins";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnTime;
    }

    public static long dateDifferent(String dateStart, String dateStop) {
//		String dateStart = "01/14/2012 09:29:58";
//		String dateStop = "01/15/2012 10:31:48";

        //HH converts hour in 24 hours format (0-23), day calculation
        long diffDays = 0;
        if (!dateStart.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            Date d1 = null;
            Date d2 = null;

            try {
                d1 = format.parse(dateStart);
                d2 = format.parse(dateStop);

                //in milliseconds
                long diff = d2.getTime() - d1.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                diffDays = diff / (24 * 60 * 60 * 1000);

                System.out.print(diffDays + " days, ");
                System.out.print(diffHours + " hours, ");
                System.out.print(diffMinutes + " minutes, ");
                System.out.print(diffSeconds + " seconds.");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return diffDays;
    }


//	public static String getAddress(LatLng latLng, Context context)
//	{
//		Geocoder geocoder = new Geocoder(context);
//		double latitude = latLng.latitude;
//		double longitude = latLng.longitude;
//
//		String address = "";
//
//		try
//		{
//			Log.i("Address Info","Address based opn geocoder");
//			DrawerList<Address> addresses = geocoder.getFromLocation(latitude,
//					longitude, 1);
//
//			if (addresses != null && !addresses.isEmpty())
//			{
//				Address returnedAddress = addresses.get(0);
//				StringBuilder strReturnedAddress = new StringBuilder();
//				int addressLineIndex = returnedAddress.getMaxAddressLineIndex();
//
//				int addressLinesToShow = 2;
//				//              To get address in limited lines
//				if (addressLineIndex < 2)
//				{
//					addressLinesToShow = addressLineIndex;
//				}
//				for (int p = 0; p < addressLinesToShow; p++)
//				{
//					strReturnedAddress
//							.append(returnedAddress.getAddressLine(p)).append(
//							"\n");
//				}
//				address = strReturnedAddress.toString();
//			}
//			else
//			{
//				address = "Address not available";
//			}
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//			address = "Address not available";
//			Log.e("Address not found","Unable to get Address in info window");
//		}
//		return address;
//	}

//	public GeoPoint getLocationFromAddress(String strAddress,Context mContext)
//	{
//
//		Geocoder coder = new Geocoder(mContext);
//		DrawerList<Address> address;
//		GeoPoint p1 = null;
//
//		try {
//			address = coder.getFromLocationName(strAddress, 5);
//			if (address == null) {
//				return null;
//			}
//			Address location = address.get(0);
//			location.getLatitude();
//			location.getLongitude();
//
//			p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
//					(int) (location.getLongitude() * 1E6));
//
//
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return p1;
//	}

//	public static void getGCMToken(final Activity activity)
//	{
//		String PROJECT_NUMBER = "342255497947";
//		GCMClientManager pushClientManager;
//		pushClientManager = new GCMClientManager(activity, PROJECT_NUMBER);
//		pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
//			@Override
//			public void onSuccess(String registrationId, boolean isNewRegistration) {
//				if(Consts.isDebug) {
//					Log.e("GCM Token----", "" + registrationId);
//				}
//				Preferences.setPreference(activity,PrefEntity.DEVICETOKEN,registrationId);
//			}
//
//			@Override
//			public void onFailure(String ex) {
//				super.onFailure(ex);
//				// If there is an error registering, don't just keep trying to register.
//				// Require the user to click a button again, or perform
//				// exponential back-off when retrying.
//			}
//		});
//	}
//
//	public static void getUniqueDeviceID(Context mContext)
//	{
//		try
//		{
//			String deviceId = Settings.Secure.getString(mContext.getContentResolver(),
//					Settings.Secure.ANDROID_ID);
//			if(Consts.isDebug) {
//				Log.e("deviceId---",""+deviceId);
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//	}

//	public static String getCityName(Context mContext)
//	{
//		String cityName="";
//		try
//		{
//			//if(CustomerHomeActivity.mAutoSearchLocation.getText().length() > 0 && !CustomerHomeActivity.mAutoSearchLocation.getText().toString().equals(mContext.getResources().getString(R.string.no_address_found))) {
//				Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
//				DrawerList<Address> addresses = gcd.getFromLocation(Double.parseDouble(Global.latitude), Double.parseDouble(Global.longitude), 1);
//				if (addresses.size() > 0) {
//					System.out.println(addresses.get(0).getLocality());
//					cityName = addresses.get(0).getLocality();
//				}
//			//}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//		return cityName;
//	}

    public static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 5) {
            return true;
        }
        return false;
    }

    public static boolean isValidUserName(String username) {
        if (username != null && username.length() >= 5) {
            return true;
        }
        return false;
    }

//	public static boolean idValidUsername(String username){
//		Pattern pattern;
//		Matcher matcher;
//		String USERNAME_PATTERN = "^[a-z0-9A-Z]{3,15}$";
//		pattern = Pattern.compile(USERNAME_PATTERN);
//		matcher = pattern.matcher(username);
//		return matcher.matches();
//
//	}

//	public static boolean idValidUsername(String s)
//	{
//		Pattern letter = Pattern.compile("[a-zA-z]");
//		Pattern digit = Pattern.compile("[0-9]");
//		Matcher hasLetter = letter.matcher(s);
//		Matcher hasDigit = digit.matcher(s);
//
//		if(hasLetter.find() && hasDigit.find())
//		{
//			return true;
//		}
//		else
//		{
//			return false;
//		}
//	}

    public static String getDateFormate(String timestamp) {
        String date = "";
        if (!timestamp.equals("")) {
            try {
                String time[] = timestamp.split(" ");
                date = time[0];
//				if(getDate(time[0]).equals(getCurrentDate()))
//				{
//					date=getCurrentTime(timestamp);
//				}
//				else {
//					date=getDate(time[0])+" "+getCurrentTime(timestamp);
//				}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    private static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(c.getTime());
    }

    private static String getCurrentTime(String dateString) {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date parsed = null;
        try {
            try {
                parsed = sourceFormat.parse(dateString);
            } catch (java.text.ParseException e) {

                e.printStackTrace();
                return "";
            }
        } catch (ParseException e1) {

            e1.printStackTrace();
            return "";
        }

        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat destFormat = new SimpleDateFormat("HH:mm");
        destFormat.setTimeZone(tz);
        String result = destFormat.format(parsed);
        return result;
    }

    public static boolean checkNumber(String s) {
        if (s.matches("[0-9]+")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getRelativeDate(String date) {

        Calendar future = Calendar.getInstance();
        String relativeDate = "";

//		Log.e("days---",""+getDateDifference(new Date(date)));

        //19 May 2015 10:13:28
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MMM yyyy HH:MM:SS");
        String inputString1 = "23 01 1997";
        String inputString2 = "27 04 1997";

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }

        long days = getDateDiff(future.getTime(), Calendar.getInstance().getTime(), TimeUnit.DAYS);

//		Log.e("days---",""+date.substring(5));

        if (days < 7) {
            CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(future.getTimeInMillis(), System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_ALL);

//      Timber.d("relativeTime - " + relativeTime);

            if (relativeTime.toString().equals("0 minutes ago")
                    || relativeTime.toString().equals("in 0 minutes")) {
                relativeDate = "Just now";
            } else if (relativeTime.toString().contains("hr. ")) {
                if (relativeTime.toString().equals("1 hr. ago")) {
                    relativeDate = "1 hour ago";
                } else {
                    relativeDate = relativeTime.toString().replace("hr. ", "hours ");
                }
            } else {
                relativeDate = relativeTime.toString();
            }
        } else if (days >= 7 && days < 14) {
            relativeDate = "A week ago";
        } else if (days >= 14 && days < 21) {
            relativeDate = "2 weeks ago";
        } else if (days >= 21 && days < 28) {
            relativeDate = "3 weeks ago";
        } else if ((days / 30) == 1) {
            relativeDate = "1 month ago";
        } else if ((days / 30) >= 2 && (days / 30) < 12) {
            relativeDate = String.format("%d months ago", (days / 30));
        } else if ((days / 365) > 1) {
            relativeDate = String.format("%d years ago", (days / 365));
        }

//        Timber.d("getRelativeDate() : days - " + days);
//        Timber.d("getRelativeDate() : relativeDate - " + relativeDate);

        return relativeDate;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static void copyDatabaseToSdCard(Context mContext) {
        if (Constant.isDebug) {
            try {

                String DB_PATH = "";
                String DATABASE_NAME = "mindworks.db";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    DB_PATH = mContext.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;
                } else {
                    DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/" + DATABASE_NAME;
                }

                File f1 = new File(DB_PATH);
                if (f1.exists()) {
                    File f2 = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/mindworks.sqlite");
                    f2.createNewFile();
                    InputStream in = new FileInputStream(f1);
                    OutputStream out = new FileOutputStream(f2);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getCurrentTimeStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

//	public static void showSnackBar(View view,String message)
//	{
//		Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
//	}


    public static void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > columns) {
            x = items / columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

    }

    public static void getListViewSize(GridView mGridView) {
        ListAdapter myListAdapter = mGridView.getAdapter();
        if (myListAdapter == null) {
            // do nothing return null
            return;
        }
        // set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, mGridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // setting listview item in adapter
        ViewGroup.LayoutParams params = mGridView.getLayoutParams();
        params.height = totalHeight - (myListAdapter.getCount() - 1);//(totalHeight+ (1 * (myListAdapter.getCount() - 1)))/2;
        mGridView.setLayoutParams(params);
        // print height of adapter on log
    }

    public static void cancelToastMessage(final Toast toast) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);
    }

    public static void customToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String getDeviceType(Context context) {
        if ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return "Tablet";
        } else {
            return "Mobile";
        }

    }

    public static String getNetworkTypeName(int type) {
        switch (type) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "CDMA - EvDo rev. 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "CDMA - EvDo rev. A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "CDMA - EvDo rev. B";
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "CDMA - 1xRTT";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "CDMA - eHRPD";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDEN";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            default:
                return "UNKNOWN";
        }
    }


    public static String convertTimestampToDate(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String localTime = sdf.format(new Date(Long.parseLong(timeStamp) * 1000));
        Date date = new Date();
        try {
            date = sdf.parse(localTime);//get local date

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("DATE : ", date.toString());
        return "" + sdf.format(date);
    }

    public static String convertDateToTimestamp(String date) {

        String timestamp[] = date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Integer.parseInt(timestamp[0]), Integer.parseInt(timestamp[1]) - 1, Integer.parseInt(timestamp[2]));
        return String.valueOf(calendar.getTimeInMillis() / 1000);
    }

    public static void alert(Context context, String msg) {

        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.app_name))
                .setMessage(msg)
                .show();
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }


}