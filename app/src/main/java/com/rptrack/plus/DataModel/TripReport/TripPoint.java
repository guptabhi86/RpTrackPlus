
package com.rptrack.plus.DataModel.TripReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.utilities.ApplicationDate;
import com.rptrack.plus.utilities.CommonUtils;
import java.io.Serializable;

public class TripPoint implements Serializable {

    @SerializedName("StartTime")
    @Expose
    private String startTime;
    @SerializedName("EndTime")
    @Expose
    private String endTime;
    @SerializedName("Start")
    @Expose
    private Start start;
    @SerializedName("End")
    @Expose
    private End end;
    @SerializedName("Duration")
    @Expose
    private String duration;
    @SerializedName("MaxSpeed")
    @Expose
    private Double maxSpeed;
    @SerializedName("AvgSpeed")
    @Expose
    private Double avgSpeed;

    @SerializedName("Distance")@Expose
    private Double distance;

    public Double getDistance() {
        return distance;
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

    public Start getStart() {
        return start;
    }

    public void setStart(Start start) {
        this.start = start;
    }

    public End getEnd() {
        return end;
    }

    public void setEnd(End end) {
        this.end = end;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public ApplicationDate getAppStartDate(){
        return CommonUtils.isValidDate(getStartTime());
    }
    public ApplicationDate getAppEndDate(){
        return CommonUtils.isValidDate(getEndTime());
    }

}
