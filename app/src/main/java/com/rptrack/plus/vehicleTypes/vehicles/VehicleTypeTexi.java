package com.rptrack.plus.vehicleTypes.vehicles;

import com.rptrack.plus.R;
import com.rptrack.plus.vehicleTypes.IVehicleType;

/**
 * Created by Ganesh ɐɯɹɐɥs on 4/16/2021 3:58 PM.
 */
public class VehicleTypeTexi implements IVehicleType {
    @Override
    public int getVehicleType() {
        return 13;
    }

    @Override
    public int getVehicleIcon(int vehicleStatus, int vehicleSubStatus) {
        if (vehicleStatus == 0)
            return R.drawable.rp_marker_texi_gray;

        if (vehicleSubStatus == 1)
            return R.drawable.rp_marker_texi_green;

        if (vehicleSubStatus == 2 || vehicleSubStatus == 3)
            return R.drawable.rp_marker_texi_blue;
        else
            return R.drawable.rp_marker_texi_gray;
    }

    @Override
    public int getVehicleDashboardIcon(int vehicleStatus, int vehicleSubStatus) {
        if (vehicleStatus == 0)
            return R.drawable.rp_side_texi_gray;

        if (vehicleSubStatus == 1)
            return R.drawable.rp_side_texi_green;

        if (vehicleSubStatus == 2 || vehicleSubStatus == 3)
            return R.drawable.rp_side_texi_blue;
        else
            return R.drawable.rp_side_texi_gray;
    }
}
