package com.rptrack.plus.module.device.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.R;
import com.rptrack.plus.module.device.DeviceOptionActivity;
import com.rptrack.plus.vehicleTypes.IVehicleType;
import com.rptrack.plus.vehicleTypes.VehicleTypeFactory;

import java.util.ArrayList;
import java.util.List;


public class DevicesTypeAdapter extends RecyclerView.Adapter<DevicesTypeAdapter.Holder> {

    Context mContext;
    List<DeviceType> deviceType = new ArrayList<>();
    int selectedType;

    public class DeviceType {
        public DeviceType(int id, int resourceId, int selectedResourceId) {
            this.id = id;
            this.resourceId = resourceId;
            this.selectedResourceId = selectedResourceId;
        }

        private int id;
        private int resourceId;
        private int selectedResourceId;
    }

    public DevicesTypeAdapter(Context mContaxt1, int vehicleType) {
        selectedType = vehicleType;
        mContext = mContaxt1;
        deviceType.add(new DeviceType(1, R.drawable.rp_side_car_normal, R.drawable.rp_side_car_blue));
        deviceType.add(new DeviceType(2, R.drawable.rp_side_bus_normal, R.drawable.rp_side_bus_blue));
        deviceType.add(new DeviceType(3, R.drawable.rp_side_truck_normal, R.drawable.rp_side_truck_blue));
        deviceType.add(new DeviceType(4, R.drawable.rp_side_bike_normal, R.drawable.rp_side_bike_blue));
        deviceType.add(new DeviceType(5, R.drawable.rp_side_jcb_normal, R.drawable.rp_side_jcb_blue));
        deviceType.add(new DeviceType(6, R.drawable.rp_side_lifter_normal, R.drawable.rp_side_lifter_blue));
        deviceType.add(new DeviceType(7, R.drawable.rp_side_loader_normal, R.drawable.rp_side_loader_blue));
        deviceType.add(new DeviceType(8, R.drawable.rp_side_marker_normal, R.drawable.rp_side_marker_blue));
        deviceType.add(new DeviceType(9, R.drawable.rp_side_person_normal, R.drawable.rp_side_person_blue));
        deviceType.add(new DeviceType(10, R.drawable.rp_side_pet_normal, R.drawable.rp_side_pet_blue));
        deviceType.add(new DeviceType(11, R.drawable.rp_side_ship_normal, R.drawable.rp_side_ship_blue));
        deviceType.add(new DeviceType(12, R.drawable.rp_side_tanker_normal, R.drawable.rp_side_tanker_blue));
        deviceType.add(new DeviceType(13, R.drawable.rp_side_texi_normal, R.drawable.rp_side_texi_blue));
        deviceType.add(new DeviceType(14, R.drawable.rp_side_tractor_normal, R.drawable.rp_side_tractor_blue));
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView ivDeviceType;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ivDeviceType = itemView.findViewById(R.id.iv_vehicle_type);
        }
    }

    @NonNull
    @Override
    public DevicesTypeAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_device_type_adapter, parent, false);
        return new DevicesTypeAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        if (selectedType == deviceType.get(position).id) {
            holder.ivDeviceType.setImageResource(deviceType.get(position).selectedResourceId);
        } else {
            holder.ivDeviceType.setImageResource(deviceType.get(position).resourceId);
        }

        holder.ivDeviceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDeviceType(deviceType.get(position).id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceType.size();
    }

    public void updateDeviceType(int selected) {
        selectedType = selected;
        notifyDataSetChanged();
    }
}























