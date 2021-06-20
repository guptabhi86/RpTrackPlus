package com.rptrack.plus.module.reportUi.reportAdapter;



import com.rptrack.plus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganesh ɐɯɹɐɥs on 2/23/2021.
 */
public class VehicleTransformerTransfarmer {


    public static ArrayList<String> getTabArrayData() {
        ArrayList<String> tabArray = new ArrayList<>();
        tabArray.add("ALL");
        tabArray.add("Engine");
        tabArray.add("AC");
        tabArray.add("Over Speed");
        tabArray.add("Power");
        tabArray.add("Tampor");
        tabArray.add("SOS");
        tabArray.add("Geofence");
        tabArray.add("Immoblizer");
        tabArray.add("Parking");
        return tabArray;
    }

    public static ArrayList<IconItem> getAllReportType() {
        ArrayList<IconItem> tabArray = new ArrayList<>();
        tabArray.add(new IconItem(5, "Stoppage"));
        tabArray.add(new IconItem(6, "Idle"));
        tabArray.add(new IconItem(3, "Distance"));
        tabArray.add(new IconItem(2, "Trip"));
        return tabArray;
    }


   /* public static List<IconItem> getSettingsDetail() {
        List<IconItem> items = new ArrayList<>();
        items.add(new IconItem(R.drawable.ic_profile, "PROFILE", "1"));
        items.add(new IconItem(R.drawable.ic_report, "REPORT", "2"));
        items.add(new IconItem(R.drawable.ic_tracking, "GEOFENCE", "3"));
        items.add(new IconItem(R.drawable.ic_settings, "APP SETTINGS", "4"));
        items.add(new IconItem(R.drawable.ic_vehicles, "VEHICLE(S)", "5"));
        items.add(new IconItem(R.drawable.ic_change_password, "CHANGE PASSWORD", "8"));
        items.add(new IconItem(R.drawable.ic_help, "HELP", "7"));
        items.add(new IconItem(R.drawable.ic_logout, "LOGOUT", "6"));
        return items;
    }

    public static List<IconItem> pagerItems() {
        List<IconItem> items = new ArrayList<>();
        items.add(new IconItem(R.drawable.ic_profile, "PROFILE", "1"));
        items.add(new IconItem(R.drawable.ic_report, "REPORT", "2"));
        items.add(new IconItem(R.drawable.ic_tracking, "GEOFENCE", "3"));
        items.add(new IconItem(R.drawable.ic_settings, "APP SETTINGS", "4"));

        return items;
    }

    public static List<IconItem> getInfoIconDetail() {
        List<IconItem> items = new ArrayList<>();
        items.add(new IconItem(R.drawable.ic_profile, "OFF", "ignition"));
        items.add(new IconItem(R.drawable.ic_report, "DISCONNECTED", "Network"));
        items.add(new IconItem(R.drawable.ic_tracking, "OUTSIDE", "Geofence"));
        items.add(new IconItem(R.drawable.ic_settings, "OFF", "Immoblizer"));
        items.add(new IconItem(R.drawable.ic_vehicles, "ON", "Parking"));
        items.add(new IconItem(R.drawable.ic_logout, "CLOSED", "Door"));
        items.add(new IconItem(R.drawable.ic_profile, "N/A", "A/c"));
        items.add(new IconItem(R.drawable.ic_report, "ON", "Charging"));
        items.add(new IconItem(R.drawable.ic_tracking, "N/A", "Temperature"));
        items.add(new IconItem(R.drawable.ic_report, "Battery", "100"));
        items.add(new IconItem(R.drawable.ic_logout, "0", "Ext Battery"));
        return items;
    }


    public static List<GeofenceData> geofenceDataList() {
        List<GeofenceData> items = new ArrayList<>();
        items.add(new GeofenceData("Test1", 1));
        items.add(new GeofenceData("Test2", 0));
        items.add(new GeofenceData("Test3", 2));
        items.add(new GeofenceData("Test4", 1));
        return items;
    }
*/
}