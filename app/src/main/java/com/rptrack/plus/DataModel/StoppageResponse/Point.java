
package com.rptrack.plus.DataModel.StoppageResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.utilities.ApplicationDate;
import com.rptrack.plus.utilities.CommonUtils;

import java.io.Serializable;

public class Point implements Serializable {

    @SerializedName("Loc")
    @Expose
    private Loc loc;
    @SerializedName("StartTime")
    @Expose
    private String startTime;
    @SerializedName("EndTime")
    @Expose
    private String endTime;
    @SerializedName("Duration")
    @Expose
    private String duration;

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ApplicationDate getAppStartDate(){
        return CommonUtils.isValidDate(getStartTime());
    }
    public ApplicationDate getAppEndDate(){
        return CommonUtils.isValidDate(getEndTime());
    }

}
