package com.rptrack.plus.utilities;

import com.rptrack.plus.ApplicationActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationDate {
    private String jsonDateFormat="yyyy-MM-dd'T'HH:mm:ss";

    private String FormatOne = "yyyy-MM-dd HH:mm:ss";
    private String FormatTwo = "yy-MMM-dd HH:mm:ss";
    private String FormatThree = "MMM dd, yy HH:mm:ss";
    private String FormatFour = "yyyy-MM-dd hh:mm:ss a";

    int TypeFormat=Preferences.getPreference_int(ApplicationActivity.getContext(),Constant.DATE_TIME_FORMAT);

    private Boolean isValid;
    private Date date;


    public String getJsonDateFormat() {
        return jsonDateFormat;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerDate() {
        if (this.isValid)

       return new SimpleDateFormat(FormatOne).format(date);
        else
            return "-";
    }

    public String getOnlyCustomerDate(){

        if (TypeFormat==0){
            TypeFormat=2;
        }
        if (this.isValid)
            if (this.isValid)
                if (TypeFormat==0){
                    return new SimpleDateFormat("yyyy-MM-dd").format(date);
                }else if(TypeFormat==1){
                    return new SimpleDateFormat("yy-MMM-dd").format(date);
                }else if (TypeFormat==2){
                    return new SimpleDateFormat("MMM dd, yy").format(date);
                }else if (TypeFormat==3){
                    return new SimpleDateFormat("yyyy-MM-dd").format(date);
                }else {
                    return new SimpleDateFormat("yyyy-MM-dd").format(date);
                }
            else
                return "-";
        else
            return "-";
    }

    public String getCustomerDateTime() {
        if (this.isValid)
            if (this.isValid)
                if (TypeFormat==0){
                    return new SimpleDateFormat(FormatOne).format(date);
                }else if(TypeFormat==1){
                    return new SimpleDateFormat(FormatTwo).format(date);
                }else if (TypeFormat==2){
                    return new SimpleDateFormat(FormatThree).format(date);
                }else if (TypeFormat==3){
                    return new SimpleDateFormat(FormatFour).format(date);
                }else {
                    return new SimpleDateFormat(FormatOne).format(date);
                }


            else
                return "-";
        else
            return "-";
    }

    public String getCustomerTime() {
        if (this.isValid)
            return new SimpleDateFormat("hh:mm a").format(date);
        else
            return "-";
    }
}
