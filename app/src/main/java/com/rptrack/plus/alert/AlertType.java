package com.rptrack.plus.alert;

import java.util.HashMap;
import java.util.Map;

public enum AlertType {

    IGNITION(1, "Ignition"),
    AC(2, "AC"),
    OVER_SPEED(3, "Over Speed"),
    POWER(4, "Power"),
    TAMPER(5, "Tamper"),
    SOS(6, "SOS"),
    GEO_FENCE(7, "Geofence"),
    IMMOBILIZER(8, "Immobilizer"),
    PARKING(9, "Parking");

    private int value;
    private String text;

    private static Map<Integer, AlertType> alertTypeToEnumValuesMapping = new HashMap<>();

    static {
        for (AlertType pizzaStatus : AlertType.values()) {
            alertTypeToEnumValuesMapping.put(
                    pizzaStatus.getAlertType(),
                    pizzaStatus
            );
        }
    }

    AlertType(int value, String text) {
        this.value = value;
        this.text = text;
    }

   public String getAlertAsString() {
        return text;
    }

    int getAlertType() {
        return value;
    }

    public static AlertType castIntToEnum(int timeToDelivery) {
        return alertTypeToEnumValuesMapping.get(timeToDelivery);
    }

    public static Map<Integer, AlertType> getAllAlerts() {
        return alertTypeToEnumValuesMapping;
    }
}
