package com.rptrack.plus.DataModel.changePassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/12/2021 1:15 PM.
 */
public class ChagnePasswordResult implements Serializable {

    @SerializedName("Message")@Expose
    private String message;

    @SerializedName("Data")@Expose
    private String data;

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
