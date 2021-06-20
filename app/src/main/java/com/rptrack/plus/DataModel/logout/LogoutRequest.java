package com.rptrack.plus.DataModel.logout;

import java.io.Serializable;

public class LogoutRequest implements Serializable {
    String FCMToken;

    public LogoutRequest(String FCMToken) {
        this.FCMToken = FCMToken;
    }
}
