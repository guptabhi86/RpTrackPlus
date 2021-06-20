package com.rptrack.plus.vehicleTypes.vehicles;

import com.rptrack.plus.R;
import com.rptrack.plus.vehicleTypes.IVehicleType;

/**
 * Created by Ganesh ɐɯɹɐɥs on 4/16/2021 3:56 PM.
 */
public class VehicleTypeTruck implements IVehicleType {
    @Override
    public int getVehicleType() {
        return 3;
    }

    @Override
    public int getVehicleIcon(int vehicleStatus, int vehicleSubStatus) {
        if (vehicleStatus == 0)
            return R.drawable.rp_marker_truck_gray;

        if (vehicleSubStatus == 1)
            return R.drawable.rp_marker_truck_green;

        if (vehicleSubStatus == 2 || vehicleSubStatus == 3)
            return R.drawable.rp_marker_truck_blue;
        else
            return R.drawable.rp_marker_truck_gray;
    }

    @Override
    public int getVehicleDashboardIcon(int vehicleStatus, int vehicleSubStatus) {
        if (vehicleStatus == 0)
            return R.drawable.rp_side_truck_gray;

        if (vehicleSubStatus == 1)
            return R.drawable.rp_side_truck_green;

        if (vehicleSubStatus == 2 || vehicleSubStatus == 3)
            return R.drawable.rp_side_truck_blue;
        else
            return R.drawable.rp_side_truck_gray;
    }
}
