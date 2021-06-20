package com.rptrack.plus.DataModel.changePassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/12/2021 12:48 PM.
 */
public class ChangePasswordReq implements Serializable {

    @SerializedName("Password")
    @Expose
    private String password;

    @SerializedName("NewPassword")
    @Expose
    private String newPassword;



    public ChangePasswordReq(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;

    }
}
