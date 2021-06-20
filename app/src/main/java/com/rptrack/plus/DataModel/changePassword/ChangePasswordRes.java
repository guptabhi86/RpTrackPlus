package com.rptrack.plus.DataModel.changePassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rptrack.plus.DataModel.BaseResponse;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/12/2021 12:48 PM.
 */
public class ChangePasswordRes extends BaseResponse implements Serializable {
    public ChangePasswordRes(Integer statusCode, String responseMessage) {
        super(statusCode, responseMessage);
    }

    @SerializedName("Result")@Expose
    private ChagnePasswordResult result;

    public ChagnePasswordResult getResult() {
        return result;
    }
}
