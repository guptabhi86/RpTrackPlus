package com.rptrack.plus.alert;

import java.io.Serializable;

public class Notification implements Serializable {
    private boolean _receiveNotification = false;

    public Notification(boolean _receiveNotification) {
        this._receiveNotification = _receiveNotification;
    }

    public boolean getReceiveNotification() {
        return _receiveNotification;
    }

    public void setReceiveNotification(boolean _receiveNotification) {
        this._receiveNotification = _receiveNotification;
    }
}
