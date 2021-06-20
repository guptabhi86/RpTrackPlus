package com.rptrack.plus.vehicleTypes.vehicles;

import com.rptrack.plus.R;
import com.rptrack.plus.vehicleTypes.IVehicleType;

/**
 * Created by Ganesh ɐɯɹɐɥs on 4/16/2021 3:58 PM.
 */
public class VehicleTypePerson implements IVehicleType {
    @Override
    public int getVehicleType() {
        return 9;
    }

    @Override
    public int getVehicleIcon(int vehicleStatus, int vehicleSubStatus) {
        if (vehicleStatus == 0)
            return R.drawable.rp_marker_person_gray;

        if (vehicleSubStatus == 1)
            return R.drawable.rp_marker_person_green;

        if (vehicleSubStatus == 2 || vehicleSubStatus == 3)
            return R.drawable.rp_marker_person_blue;
        else
            return R.drawable.rp_marker_person_gray;
    }

    @Override
    public int getVehicleDashboardIcon(int vehicleStatus, int vehicleSubStatus) {
        if (vehicleStatus == 0)
            return R.drawable.rp_side_person_gray;

        if (vehicleSubStatus == 1)
            return R.drawable.rp_side_person_green;

        if (vehicleSubStatus == 2 || vehicleSubStatus == 3)
            return R.drawable.rp_side_person_blue;
        else
            return R.drawable.rp_side_person_gray;
    }
}
