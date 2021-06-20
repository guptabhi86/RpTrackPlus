package com.rptrack.plus.vehicleTypes;

/**
 * Created by Ganesh ɐɯɹɐɥs on 4/16/2021 3:50 PM.
 */
public interface IVehicleType {
    int getVehicleType();

    int getVehicleDashboardIcon(int vehicleStatus, int vehicleSubStatus);

    int getVehicleIcon(int vehicleStatus, int vehicleSubStatus);
}
