package com.rptrack.plus.Retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rptrack.plus.DataModel.DashboardResponse.DashboardResponse;
import com.rptrack.plus.DataModel.DistanceReport.DistanceResponse;
import com.rptrack.plus.DataModel.HistoryReplyResponse.HistoryReplyRequest;
import com.rptrack.plus.DataModel.HistoryReplyResponse.HistoryReplyResponse;
import com.rptrack.plus.DataModel.Relay.RelayRequest;
import com.rptrack.plus.DataModel.Relay.RelayResponse;
import com.rptrack.plus.DataModel.StoppageResponse.StoppageResponse;
import com.rptrack.plus.DataModel.TripReport.TripReportResponse;
import com.rptrack.plus.DataModel.VehicleWiseResponse.VehicleDataResponse;
import com.rptrack.plus.DataModel.address.AddressResponse;
import com.rptrack.plus.DataModel.alerts.AlertResponse;
import com.rptrack.plus.DataModel.changePassword.ChangePasswordReq;
import com.rptrack.plus.DataModel.changePassword.ChangePasswordRes;
import com.rptrack.plus.DataModel.deleteGeofence.DeleteGeofenceResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.DataModel.geofence.GeofenceListBaseResponse;
import com.rptrack.plus.DataModel.geofence.GeofenceResult;
import com.rptrack.plus.DataModel.geofence.createGeofence.CreateGeofenceReq;
import com.rptrack.plus.DataModel.geofence.createGeofence.CreateGeofenceResponse;
import com.rptrack.plus.DataModel.login.LoginBaseResponse;
import com.rptrack.plus.DataModel.login.LoginRequest;
import com.rptrack.plus.DataModel.logout.LogoutBaseResponse;
import com.rptrack.plus.DataModel.logout.LogoutRequest;
import com.rptrack.plus.DataModel.parking.ParkingBaseResponse;
import com.rptrack.plus.DataModel.parking.ParkingListenerRequest;
import com.rptrack.plus.DataModel.speedLimit.SpeedRequest;
import com.rptrack.plus.DataModel.speedLimit.SpeedResponse;
import com.rptrack.plus.Retrofit.retrofithelper.APIServiceGenerator;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rptrack.plus.utilities.Preferences.getPreference;


public class APIUtility {
    private static final String TAG = "APIUtility";
    private APIService mApiService = null;
    //  private ErrorResponse errorResponse=null;

    private static final String BASEURL = Constant.BASE_URL;

    public interface APIResponseListener<T> {
        void onReceiveResponse(T response);

        void onErrorResponse(ErrorResponse errorResponse);

        void onFailureResponse(String string);
    }

    public APIUtility(Context context) {
        APIServiceGenerator.setBaseUrl(BASEURL);
        mApiService = APIServiceGenerator.createService(APIService.class);
    }

    public APIUtility() {
        APIServiceGenerator.setBaseUrl(BASEURL);
        APIServiceGenerator.addHeader("Content-Type", "application/json");
        mApiService = APIServiceGenerator.createService(APIService.class);
    }

    public void showDialog(Context context, boolean isDialog) {
        if (isDialog) {
            ProcessDialog.start(context);
        }
    }

    public void dismissDialog(boolean isDialog) {
        if (isDialog) {
            ProcessDialog.dismiss();
        }
    }


    public void LoginRequestAttemp(Context context, final boolean isDialog, final LoginRequest loginRequest, final APIResponseListener<LoginBaseResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: LoginRequest" + new Gson().toJson(loginRequest));
            showDialog(context, isDialog);
            mApiService.LOGIN_RESPONSE_CALL(loginRequest).enqueue(new Callback<LoginBaseResponse>() {
                @Override
                public void onResponse(Call<LoginBaseResponse> call, Response<LoginBaseResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: LoginResponse" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginBaseResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void DashboardResponseData(final Context context, final boolean isDialog, String url,
                                      final APIResponseListener<DashboardResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            showDialog(context, isDialog);
            APIServiceGenerator.addHeader("Authorization", "Bearer " + getPreference(context, Constant.ACCESS_TOKEN));
            Log.d("DashboardToken", getPreference(context, Constant.ACCESS_TOKEN));
            Log.d("DashboardURL", url);

            mApiService.DASHBOARD_DATA(url).enqueue(new Callback<DashboardResponse>() {
                @Override
                public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                    dismissDialog(true);
                    if (response.isSuccessful()) {
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {

                            Log.d("Errrorm", new Gson().toJson(response.message()));
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DashboardResponse> call, Throwable t) {
                    dismissDialog(true);
                    apiResponseListener.onFailureResponse(t.getMessage());
                }
            });
        } else {
            dismissDialog(true);
        }
    }

    public void DashboardResponseDataByVehicle(final Context context, final boolean isDialog, String url,
                                               final APIResponseListener<VehicleDataResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            showDialog(context, isDialog);
            APIServiceGenerator.addHeader("Authorization", "Bearer " + getPreference(context, Constant.ACCESS_TOKEN));
            Log.d("DashboardToken", getPreference(context, Constant.ACCESS_TOKEN));
            Log.d("DashboardURLByVehicle", url);

            mApiService.DASHBOARD_DATA_BY_VEHICLE(url).enqueue(new Callback<VehicleDataResponse>() {
                @Override
                public void onResponse(Call<VehicleDataResponse> call, Response<VehicleDataResponse> response) {
                    dismissDialog(true);
                    if (response.isSuccessful()) {
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {

                            Log.d("Error", new Gson().toJson(response.message()));
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<VehicleDataResponse> call, Throwable t) {
                    dismissDialog(true);
                    apiResponseListener.onFailureResponse(t.getMessage());
                }
            });
        } else {
            dismissDialog(true);
        }
    }


    public void getAddressFromLatLan(final Context context, final boolean isDialog, String stringUrl, final APIResponseListener<AddressResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            showDialog(context, isDialog);
            APIServiceGenerator.addHeader("Authorization", "Bearer " + getPreference(context, Constant.ACCESS_TOKEN));
            mApiService.ADDRESS_RESPONSE_CALL(stringUrl).enqueue(new Callback<AddressResponse>() {
                @Override
                public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                    dismissDialog(true);
                    apiResponseListener.onReceiveResponse(response.body());
                }

                @Override
                public void onFailure(Call<AddressResponse> call, Throwable t) {
                    apiResponseListener.onErrorResponse(null);
                }
            });

        } else {
            CommonUtils.displayNetworkAlert(context, true);
            dismissDialog(true);
        }
    }

    public void getGeofenceList(final Context context, final boolean isDialog, String stringUrl, final APIResponseListener<GeofenceListBaseResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            showDialog(context, isDialog);
            APIServiceGenerator.addHeader("Authorization", "Bearer " + getPreference(context, Constant.ACCESS_TOKEN));
            mApiService.GEOFENCE_LIST_RESPONSE_CALL(stringUrl).enqueue(new Callback<GeofenceListBaseResponse>() {
                @Override
                public void onResponse(Call<GeofenceListBaseResponse> call,
                                       Response<GeofenceListBaseResponse> response) {
                    dismissDialog(true);
                    if (response.isSuccessful()) {
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            Log.d("Error", new Gson().toJson(response.message()));
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GeofenceListBaseResponse> call, Throwable t) {
                    apiResponseListener.onErrorResponse(null);
                }
            });


        } else {
            CommonUtils.displayNetworkAlert(context, true);
            dismissDialog(true);
        }
    }


    public void HistoryReplyRequest(Context context, final boolean isDialog, final HistoryReplyRequest historyReplyRequest, final APIResponseListener<HistoryReplyResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: historyReplyRequest" + new Gson().toJson(historyReplyRequest));
            showDialog(context, isDialog);
            mApiService.HISTORY_REPLY_RESPONSE(historyReplyRequest).enqueue(new Callback<HistoryReplyResponse>() {
                @Override
                public void onResponse(Call<HistoryReplyResponse> call, Response<HistoryReplyResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: HistoryReplyResponse" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<HistoryReplyResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void DistanceRequest(Context context, final boolean isDialog, final HistoryReplyRequest historyReplyRequest, final APIResponseListener<DistanceResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: DistanceRequest" + new Gson().toJson(historyReplyRequest));
            showDialog(context, isDialog);
            mApiService.DISTANCE_REPORT_RESPONSE(historyReplyRequest).enqueue(new Callback<DistanceResponse>() {
                @Override
                public void onResponse(Call<DistanceResponse> call, Response<DistanceResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: DistanceRequest" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DistanceResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void StoppageRequest(Context context, final boolean isDialog, final HistoryReplyRequest historyReplyRequest, final APIResponseListener<StoppageResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: StoppageRequest" + new Gson().toJson(historyReplyRequest));
            showDialog(context, isDialog);
            mApiService.STOPPAGE_RESPONSE_CALL(historyReplyRequest).enqueue(new Callback<StoppageResponse>() {
                @Override
                public void onResponse(Call<StoppageResponse> call, Response<StoppageResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: StoppageResponse" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<StoppageResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void IdleRequest(Context context, final boolean isDialog, final HistoryReplyRequest historyReplyRequest, final APIResponseListener<StoppageResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: IdleRequest" + new Gson().toJson(historyReplyRequest));
            showDialog(context, isDialog);
            mApiService.IDLE_RESPONSE_CALL(historyReplyRequest).enqueue(new Callback<StoppageResponse>() {
                @Override
                public void onResponse(Call<StoppageResponse> call, Response<StoppageResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: IdleResponse" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<StoppageResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void TripReportRequest(Context context, final boolean isDialog, final HistoryReplyRequest historyReplyRequest, final APIResponseListener<TripReportResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: TripRequest" + new Gson().toJson(historyReplyRequest));
            showDialog(context, isDialog);
            mApiService.TRIP_RESPONSE_CALL(historyReplyRequest).enqueue(new Callback<TripReportResponse>() {
                @Override
                public void onResponse(Call<TripReportResponse> call, Response<TripReportResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: TripResponse" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TripReportResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void LogoutRequest(Context context, final boolean isDialog, final LogoutRequest logoutRequest, final APIResponseListener<LogoutBaseResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: TripRequest" + new Gson().toJson(logoutRequest));
            showDialog(context, isDialog);
            mApiService.LOGOUT_RESPONSE_CALL(logoutRequest).enqueue(new Callback<LogoutBaseResponse>() {
                @Override
                public void onResponse(Call<LogoutBaseResponse> call, Response<LogoutBaseResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: LogoutResponse" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LogoutBaseResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void RELAYRequest(Context context, final boolean isDialog, final RelayRequest relayRequest, final APIResponseListener<RelayResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: relayRequest" + new Gson().toJson(relayRequest));
            showDialog(context, isDialog);
            mApiService.RELAY_RESPONSE(relayRequest).enqueue(new Callback<RelayResponse>() {
                @Override
                public void onResponse(Call<RelayResponse> call, Response<RelayResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: RelayResponse" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RelayResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void setParkingListenerRequest(Context context, final boolean isDialog, final ParkingListenerRequest parkingListenerRequest, final APIResponseListener<ParkingBaseResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: parkingRequest" + new Gson().toJson(parkingListenerRequest));
            showDialog(context, isDialog);
            //APIServiceGenerator.addHeader("Authorization", "Bearer " + getPreference(context, Constant.ACCESS_TOKEN));

            mApiService.PARKING_RESPONSE_CALL(parkingListenerRequest).enqueue(new Callback<ParkingBaseResponse>() {
                @Override
                public void onResponse(Call<ParkingBaseResponse> call, Response<ParkingBaseResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: RelayResponse" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ParkingBaseResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void changePassworAttampt(Context context, final boolean isDialog, final ChangePasswordReq changePasswordReq, final APIResponseListener<ChangePasswordRes> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: changePassword" + new Gson().toJson(changePasswordReq));
            showDialog(context, isDialog);
            //  APIServiceGenerator.addHeader("Authorization", "Bearer " + getPreference(context, Constant.ACCESS_TOKEN));
            mApiService.CHANGE_PASSWORD_RES_CALL(changePasswordReq).enqueue(new Callback<ChangePasswordRes>() {
                @Override
                public void onResponse(Call<ChangePasswordRes> call, Response<ChangePasswordRes> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: ChangePassword" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ChangePasswordRes> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void createGeofenceAttampt(Context context, final boolean isDialog, final CreateGeofenceReq createGeofenceReq, final APIResponseListener<CreateGeofenceResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: geofence" + new Gson().toJson(createGeofenceReq));
            showDialog(context, isDialog);
            APIServiceGenerator.addHeader("Authorization", "Bearer " + getPreference(context, Constant.ACCESS_TOKEN));
            mApiService.CREATE_GEOFENCE_RESPONSECALL(createGeofenceReq).enqueue(new Callback<CreateGeofenceResponse>() {
                @Override
                public void onResponse(Call<CreateGeofenceResponse> call, Response<CreateGeofenceResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: Geofence" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CreateGeofenceResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void updateGeofenceAttampt(Context context, final boolean isDialog, final CreateGeofenceReq createGeofenceReq, final APIResponseListener<CreateGeofenceResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: geofenceUpdate" + new Gson().toJson(createGeofenceReq));
            showDialog(context, isDialog);
            APIServiceGenerator.addHeader("Authorization", "Bearer " + getPreference(context, Constant.ACCESS_TOKEN));
            mApiService.UPDATE_GEOFENCE_RESPONSECALL(createGeofenceReq.getId(), createGeofenceReq).enqueue(new Callback<CreateGeofenceResponse>() {
                @Override
                public void onResponse(Call<CreateGeofenceResponse> call, Response<CreateGeofenceResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: GeofenceUpdate" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CreateGeofenceResponse> call, Throwable throwable) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }

    public void updateSpeedLimit(Context context, final boolean isDialog, final SpeedRequest speedRequest, final APIResponseListener<SpeedResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "onRequest: speed" + new Gson().toJson(speedRequest));
            showDialog(context, isDialog);
            mApiService.SPEED_RESPONSE_CALL(speedRequest.getDeviceId(), speedRequest).enqueue(new Callback<SpeedResponse>() {
                @Override
                public void onResponse(Call<SpeedResponse> call, Response<SpeedResponse> response) {
                    dismissDialog(isDialog);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: speed" + new Gson().toJson(response.body()));
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SpeedResponse> call, Throwable t) {
                    dismissDialog(isDialog);
                    apiResponseListener.onFailureResponse("Something Went Wrong ! Try later .");
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, false);
        }
    }


    public void getAllAlerts(final Context context, final boolean isDialog, String stringUrl, final APIResponseListener<AlertResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            showDialog(context, isDialog);
            APIServiceGenerator.addHeader("Authorization", "Bearer " + getPreference(context, Constant.ACCESS_TOKEN));
            mApiService.ALERT_RESPONSE_CALL(stringUrl).enqueue(new Callback<AlertResponse>() {
                @Override
                public void onResponse(Call<AlertResponse> call, Response<AlertResponse> response) {
                    dismissDialog(true);
                    if (response.isSuccessful()) {
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AlertResponse> call, Throwable t) {
                    dismissDialog(true);
                    apiResponseListener.onFailureResponse(t.getMessage());
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, true);
            dismissDialog(true);
        }
    }

    public void deleteGeofence(final Context context, final boolean isDialog, String stringUrl, final APIResponseListener<DeleteGeofenceResponse> apiResponseListener) {
        if (CommonUtils.isNetworkAvailable(context)) {
            showDialog(context, isDialog);
            APIServiceGenerator.addHeader("Authorization", "Bearer " + getPreference(context, Constant.ACCESS_TOKEN));
           Log.d("requestUrl:delete",stringUrl);

            mApiService.DELETE_GEOFENCE(stringUrl).enqueue(new Callback<DeleteGeofenceResponse>() {
                @Override
                public void onResponse(Call<DeleteGeofenceResponse> call, Response<DeleteGeofenceResponse> response) {
                    dismissDialog(true);
                    if (response.isSuccessful()) {
                        apiResponseListener.onReceiveResponse(response.body());
                    } else {
                        try {
                            ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiResponseListener.onErrorResponse(errorResponse);
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DeleteGeofenceResponse> call, Throwable t) {
                    dismissDialog(true);
                    apiResponseListener.onFailureResponse(t.getMessage());
                }
            });
        } else {
            CommonUtils.displayNetworkAlert(context, true);
            dismissDialog(true);
        }
    }


}
