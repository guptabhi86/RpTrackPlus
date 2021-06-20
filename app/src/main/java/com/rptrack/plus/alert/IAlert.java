package com.rptrack.plus.alert;

import com.rptrack.plus.DataModel.alerts.AlertDatum;

public interface IAlert {
    /**
     * Get alert type
     *
     * @return AlertType enum
     */
    AlertType getAlertType();

    /**
     * Return alert message
     * Notification Data
     *
     * @return Alert type message
     */
    String getMessage(AlertDatum alertObj);


    /**
     * Return alert icon
     * Notification Data
     *
     * @return Icon Resource Id
     */
    int getAlertIcon(AlertDatum alertObj);

    /**
     * function will return whether notification is required to show on notification tray
     *
     * @return True or False
     */
    boolean showAlert();
}
