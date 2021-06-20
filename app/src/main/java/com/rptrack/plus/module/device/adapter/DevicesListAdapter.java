package com.rptrack.plus.module.device.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.DataModel.DashboardResponse.Datum;
import com.rptrack.plus.R;
import com.rptrack.plus.module.device.DeviceOptionActivity;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.vehicleTypes.IVehicleType;
import com.rptrack.plus.vehicleTypes.VehicleTypeFactory;

import java.util.ArrayList;
import java.util.List;


public class DevicesListAdapter extends RecyclerView.Adapter<DevicesListAdapter.Holder> implements Filterable {

    Context mContext;
    List<Datum> datumList = new ArrayList<>();
    List<Datum> mFilterList;
    ValueFilter valueFilter;

    public DevicesListAdapter(Context mContaxt1) {
        mContext = mContaxt1;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView vehicleNumber, vehicleStatus;
        ImageView img_vehicle;
        RelativeLayout mainLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            vehicleNumber = itemView.findViewById(R.id.vehicle_number);
            vehicleStatus = itemView.findViewById(R.id.vehicle_status);
            img_vehicle = itemView.findViewById(R.id.img_vehicle);
            mainLayout = itemView.findViewById(R.id.main_layout);
        }
    }

    @NonNull
    @Override
    public DevicesListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_adapter, parent, false);
        return new DevicesListAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.vehicleNumber.setText(datumList.get(position).getDevice().getVehicleNo());
        holder.vehicleStatus.setText(datumList.get(position).getStatusDuration());
        IVehicleType type = VehicleTypeFactory.getInstance(datumList.get(position).getDevice().getVehicleType());
        Log.d("obj-binder", type.getVehicleType() + "");
        holder.img_vehicle.setImageResource(type.getVehicleDashboardIcon(datumList.get(position).getStatus(), datumList.get(position).getSubStatus()));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datumList.get(position).getResultCode()!=1){
                    Toast.makeText(mContext,"Device never Connected with server !!",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent =new Intent(mContext,DeviceOptionActivity.class);
                    intent.putExtra(Constant.INTENT_SERIALIZABLE,datumList.get(position));
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new DevicesListAdapter.ValueFilter();
        }
        return valueFilter;
    }

    public void updateDeviceListData(List<Datum> deviceList) {
        this.datumList = deviceList;
        this.mFilterList = deviceList;
        notifyDataSetChanged();
    }


    public void filter(String text1, List<Datum> datumList1) {

        Log.d("SearchText",text1+" ");
        if (!text1.isEmpty()) {
            datumList.clear();
            for (Datum d : datumList1) {

                if (d.getDevice().getVehicleNo().toLowerCase().contains(text1.toLowerCase())) {
                    datumList.add(d);
                }
            }
            notifyDataSetChanged();
        }
    }


    public class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence != null && charSequence.length() > 0) {
                List<Datum> filterList = new ArrayList<>();
                for (int i = 0; i < mFilterList.size(); i++) {
                    if (mFilterList.get(i).getDevice().getVehicleNo().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filterList.add(mFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mFilterList.size();
                results.values = mFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            datumList = (List<Datum>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}


















