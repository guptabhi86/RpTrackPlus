
package com.rptrack.plus.DataModel.DashboardResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Datum implements Serializable {

    @SerializedName("ResultCode")
    @Expose
    private Integer resultCode;

    @SerializedName("ResultMessage")
    @Expose
    private Object resultMessage;

    @SerializedName("Device")
    @Expose
    private Device device;

    @SerializedName("Eventdata")
    @Expose
    private Eventdata eventdata;

    @SerializedName("Peripherial")
    @Expose
    private Peripherial peripherial;

    @SerializedName("Battery")
    @Expose
    private Battery battery;

    @SerializedName("StatusDuration")
    @Expose
    private String statusDuration;

    @SerializedName("Status")
    @Expose
    private Integer status;

    @SerializedName("SubStatus")
    @Expose
    private Integer subStatus;

    @SerializedName("ActivationDate")
    @Expose
    private String activationDate;

    @SerializedName("NextRechargeDue")
    @Expose
    private String nextRechargeDate;

    public String getActivationDate() {
        if (activationDate == null) {
            return "-";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return new SimpleDateFormat("yyyy-MM-dd").format(format.parse(activationDate.replace("T", " ")));
        } catch (ParseException ex) {
            return "-";
        }
    }

    public String getNextRechargeDate() {
        if (nextRechargeDate == null) {
            return "-";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return new SimpleDateFormat("yyyy-MM-dd").format(format.parse(nextRechargeDate.replace("T", " ")));
        } catch (ParseException ex) {
            return "-";
        }
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public Object getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(Object resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Eventdata getEventdata() {
        return eventdata;
    }

    public void setEventdata(Eventdata eventdata) {
        this.eventdata = eventdata;
    }

    public Peripherial getPeripherial() {
        return peripherial;
    }


    public void setPeripherial(Peripherial peripherial) {
        this.peripherial = peripherial;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public String getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(String statusDuration) {
        this.statusDuration = statusDuration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    public String getVehicleDashBoardStatus() {
        if (this.statusDuration == null) {
            return "";
        }

        String[] status = this.statusDuration.split("\\s+");
        if (status.length == 3) {
            StringBuffer buffer = new StringBuffer();
            String[] sub = status[2].split("[.:]+");
            if (sub.length == 4) {
                int days = Integer.parseInt(sub[0]);
                int hours = Integer.parseInt(sub[1]);
                int minutes = Integer.parseInt(sub[2]);

                buffer.append(status[0] + " (");
                if (days != 0) {
                    buffer.append(String.format("%1$d Day, %2$d Hr, %3$d Min", days, hours, minutes));
                } else {
                    buffer.append(String.format("%1$d Hr, %2$d Min", hours, minutes));
                }
                buffer.append(")");
                return buffer.toString();
            }
            return this.statusDuration;
        }
        return this.statusDuration;
    }

}
