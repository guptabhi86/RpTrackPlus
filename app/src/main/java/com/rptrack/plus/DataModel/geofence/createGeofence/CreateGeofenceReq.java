package com.rptrack.plus.DataModel.geofence.createGeofence;

import com.rptrack.plus.DataModel.geofence.GeofencePoints;

import java.util.List;

/**
 * Created by Ganesh ɐɯɹɐɥs on 3/17/2021 5:01 PM.
 */
public class CreateGeofenceReq {

    private int id ;
    private int CustomerId;
    private int GeomType;
    private String Name;
    List<GeofencePoints> Points;
    private int Radius;


    public CreateGeofenceReq(int id, int customerId, int geomType, String name, List<GeofencePoints> geofencePoints, int radius) {
        this.id = id;
        CustomerId = customerId;
        GeomType = geomType;
        Name = name;
        this.Points = geofencePoints;
        Radius = radius;
    }

    public int getId() {
        return id;
    }
}
