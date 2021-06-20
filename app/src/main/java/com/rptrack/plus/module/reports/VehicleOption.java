package com.rptrack.plus.module.reports;

import android.content.Context;

import com.rptrack.plus.DataModel.DashboardResponse.Datum;


public abstract class VehicleOption {
public Context _context;
public IResult _result;
public Datum _datum;
public int _id;
public boolean isHideTime;
public int _deviceId;


public VehicleOption(Context _context, IResult _result, Datum _datum, int _id, boolean isHideTime) {
        this._context = _context;
        this._result = _result;
        this._datum = _datum;
        this._id = _id;
        this.isHideTime = isHideTime;
        }

public VehicleOption(Context _context, IResult _result, int _id, boolean isHideTime, int deviceId) {
        this._context = _context;
        this._result = _result;
        this._id = _id;
        this.isHideTime = isHideTime;
        this._deviceId = deviceId;
        }

    public abstract void showDialog();

}
