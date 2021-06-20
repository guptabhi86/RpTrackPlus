
package com.rptrack.plus.DataModel.DistanceReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.utilities.ApplicationDate;
import com.rptrack.plus.utilities.CommonUtils;


import java.io.Serializable;

public class Distance  implements Serializable {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Distance")
    @Expose
    private Double distance;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public ApplicationDate getApplicationDate(){
        return CommonUtils.isValidDate(getDate());
    }

}
