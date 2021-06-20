
package com.rptrack.plus.DataModel.HistoryReplyResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private HistoryReplayData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HistoryReplayData getData() {
        return data;
    }

    public void setData(HistoryReplayData data) {
        this.data = data;
    }

}
