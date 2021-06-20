package com.rptrack.plus.vehicleTypes;

import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeBike;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeBus;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeCar;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeJCB;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeLifter;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeLoader;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeMarker;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypePerson;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypePet;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeShip;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeTanker;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeTexi;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeTractor;
import com.rptrack.plus.vehicleTypes.vehicles.VehicleTypeTruck;

/**
 * Created by Ganesh ɐɯɹɐɥs on 4/16/2021 3:49 PM.
 */
public class VehicleTypeFactory {
    public static IVehicleType getInstance(int vehicleType) {
        IVehicleType iVehicleType = null;
        switch (vehicleType) {
            case 1:
                iVehicleType=new VehicleTypeCar();
                break;
            case 2:
                iVehicleType=new VehicleTypeBus();
                break;
            case 3:
                iVehicleType=new VehicleTypeTruck();
                break;
            case 4:
                iVehicleType=new VehicleTypeBike();
                break;
            case 5:
                iVehicleType=new VehicleTypeJCB();
                break;
            case 6:
                iVehicleType=new VehicleTypeLifter();
                break;
            case 7:
                iVehicleType=new VehicleTypeLoader();
                break;
            case 8:
                iVehicleType=new VehicleTypeMarker();
                break;
            case 9:
                iVehicleType=new VehicleTypePerson();
                break;
            case 10:
                iVehicleType=new VehicleTypePet();
                break;
            case 11:
                iVehicleType=new VehicleTypeShip();
                break;
            case 12:
                iVehicleType=new VehicleTypeTanker();
                break;
            case 13:
                iVehicleType=new VehicleTypeTexi();
                break;
            case 14:
                iVehicleType=new VehicleTypeTractor();
                break;
            default:
                iVehicleType=new VehicleTypeCar();
                break;
        }
        return iVehicleType;
    }
}
