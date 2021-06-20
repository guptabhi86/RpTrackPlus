package com.rptrack.plus.bluetooth;

import java.io.Serializable;

public class ConnectedDeviceResponse implements Serializable {
    private String vehicleNumber;
    private String macAddress;
    private String bluetoothName;
    private String uuid;

    public ConnectedDeviceResponse(String vehicleNumber, String macAddress, String bluetoothName) {
        this.vehicleNumber = vehicleNumber;
        this.macAddress = macAddress;
        this.bluetoothName = bluetoothName;
    }

    public ConnectedDeviceResponse(String vehicleNumber, String uuid) {
        this.vehicleNumber = vehicleNumber;
        this.uuid = uuid;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public String getUuid() {
        return uuid;
    }
}
