package com.rptrack.plus.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rptrack.plus.R;

import java.util.ArrayList;

public class ConnectedDeviceListAdapter extends RecyclerView.Adapter<ConnectedDeviceListAdapter.Holder> {

    ArrayList<ConnectedDeviceResponse> deviceResponseArrayList=new ArrayList<>();
    ArrayList<BluetoothDevice> mBTArray=new ArrayList<>();
    Context mContext;
    public ClickCallback clickCallback;

    public ConnectedDeviceListAdapter(Context context, ArrayList<ConnectedDeviceResponse> data, ArrayList<BluetoothDevice> btData, ClickCallback clickCallback1) {
        mContext=context;
        deviceResponseArrayList=data;
        mBTArray=btData;
        clickCallback=clickCallback1;

    }
    public interface ClickCallback{
        void clickeItem(BluetoothDevice deviceResponse);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.connected_device_adapter,parent,false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.macAddress.setText(mBTArray.get(position).getAddress());
        //holder.blutoothName.setText(mBTArray.get(position).getName());
        holder.vehicleNumber.setText(deviceResponseArrayList.get(position).getVehicleNumber());
        holder.mainCardlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallback.clickeItem(mBTArray.get(position));
                Log.d("DeviceBlue",new Gson().toJson(mBTArray.get(position))+" g3");

            }
        });

    }

    @Override
    public int getItemCount() {
        return mBTArray.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView macAddress,vehicleNumber,blutoothName;
        CardView mainCardlayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            macAddress=(TextView)itemView.findViewById(R.id.macAddress);
            vehicleNumber=(TextView)itemView.findViewById(R.id.vehicle_number);
            blutoothName=(TextView)itemView.findViewById(R.id.bluetooth_name);
            mainCardlayout=(CardView)itemView.findViewById(R.id.mainCardlayout);
        }
    }
}
