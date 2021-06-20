package com.rptrack.plus.alert;

import android.content.Context;

import com.rptrack.plus.alert.alerts.*;

public class AlertTypeFactory {
    public static IAlert getInstance(Context context, int alertType) {

        AlertType type = AlertType.castIntToEnum(alertType);
        switch (type) {
            case IGNITION:
                return new AlertTypeIgnition(context);
            case AC:
                return new AlertTypeAC(context);
            case OVER_SPEED:
                return new AlertTypeOverSpeed(context);
            case POWER:
                return new AlertTypePower(context);
            case TAMPER:
                return new AlertTypeTamper(context);
            case SOS:
                return new AlertTypeSOS(context);
            case GEO_FENCE:
                return new AlertTypeGeofence(context);
            case IMMOBILIZER:
                return new AlertTypeImmobilizer(context);
            case PARKING:
                return new AlertTypeParking(context);
            default:
                return null;
        }
    }
}
