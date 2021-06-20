package com.rptrack.plus.Retrofit;


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
import com.rptrack.plus.DataModel.geofence.GeofenceListBaseResponse;
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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIService {
  //  @Headers("Content-Type: application/json")

    @POST("Login/CLogin")
    Call<LoginBaseResponse> LOGIN_RESPONSE_CALL(@Body LoginRequest loginRequest);

    @GET()
    Call<DashboardResponse>DASHBOARD_DATA(@Url String url);

    @GET()
    Call<VehicleDataResponse>DASHBOARD_DATA_BY_VEHICLE(@Url String url);

    @GET()
    Call<AddressResponse> ADDRESS_RESPONSE_CALL(@Url String url);

    @POST("History")
    Call<HistoryReplyResponse>HISTORY_REPLY_RESPONSE(@Body HistoryReplyRequest replyRequest);

    @POST("Relay")
    Call<RelayResponse>RELAY_RESPONSE(@Body RelayRequest relayRequest);

    @POST("Distance")
    Call<DistanceResponse>DISTANCE_REPORT_RESPONSE(@Body HistoryReplyRequest historyReplyRequest);

    @POST("Stoppage")
    Call<StoppageResponse>STOPPAGE_RESPONSE_CALL(@Body HistoryReplyRequest historyReplyRequest);
    @POST("Idle")
    Call<StoppageResponse>IDLE_RESPONSE_CALL(@Body HistoryReplyRequest historyReplyRequest);

    @POST("Trip")
    Call<TripReportResponse>TRIP_RESPONSE_CALL(@Body HistoryReplyRequest historyReplyRequest);

    @POST("Login/Logout")
    Call<LogoutBaseResponse>LOGOUT_RESPONSE_CALL(@Body LogoutRequest logoutRequest);

    @POST("GprsCommand/Parking")
    Call<ParkingBaseResponse>PARKING_RESPONSE_CALL(@Body ParkingListenerRequest parkingListenerRequest);

    @POST("password")
    Call<ChangePasswordRes>CHANGE_PASSWORD_RES_CALL(@Body ChangePasswordReq parkingListenerRequest);

    @GET()
    Call<GeofenceListBaseResponse>GEOFENCE_LIST_RESPONSE_CALL(@Url String url);

    @POST("Geofence")
    Call<CreateGeofenceResponse>CREATE_GEOFENCE_RESPONSECALL(@Body CreateGeofenceReq createGeofenceReq);

    @PUT("Geofence/{id}")
    Call<CreateGeofenceResponse>UPDATE_GEOFENCE_RESPONSECALL(@Path("id") int id,@Body CreateGeofenceReq createGeofenceReq);

    @PUT("v1/DeviceMetaData/UpdateSpeedLimit/{id}")
    Call<SpeedResponse>SPEED_RESPONSE_CALL(@Path("id") int id, @Body SpeedRequest speedRequest);

    @GET()
    Call<AlertResponse>ALERT_RESPONSE_CALL(@Url String url);

    @DELETE()
    Call<DeleteGeofenceResponse>DELETE_GEOFENCE(@Url String url);


}
