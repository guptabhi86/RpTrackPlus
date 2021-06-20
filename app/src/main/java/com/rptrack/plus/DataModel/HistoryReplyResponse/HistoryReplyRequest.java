package com.rptrack.plus.DataModel.HistoryReplyResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistoryReplyRequest implements Serializable {


    @SerializedName("DeviceId")
    private int DeviceId;

    @SerializedName("FromTime")
    private String FromTime;

    @SerializedName("ToTime")
    private String ToTime;


    public HistoryReplyRequest(int deviceId, String fromTime, String toTime) {
        DeviceId = deviceId;
        FromTime = fromTime;
        ToTime = toTime;
    }
}
