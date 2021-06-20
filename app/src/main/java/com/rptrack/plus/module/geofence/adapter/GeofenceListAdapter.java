package com.rptrack.plus.module.geofence.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.DataModel.geofence.GeofenceDatum;
import com.rptrack.plus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganesh ɐɯɹɐɥs on 2/23/2021.
 */
public class GeofenceListAdapter extends RecyclerView.Adapter<GeofenceListAdapter.Holder> {
    Context mContext;
    List<GeofenceDatum> data=new ArrayList<>();
    List<GeofenceDatum> mFilterList=new ArrayList<>();
    GeofenceSelection geofenceSelection;

    public interface GeofenceSelection {
        void geofenceSelected(GeofenceDatum datum);
    }

    public void updateDateGeofence(List<GeofenceDatum> datumList1){
        mFilterList = datumList1;
        data = datumList1;
        notifyDataSetChanged();
    }

    public GeofenceListAdapter(Context mContaxt,  GeofenceSelection geofenceSelection) {
        this.mContext = mContaxt;
        this.geofenceSelection = geofenceSelection;
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.geofence_list_adapter, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.vehicleNumber.setText(data.get(position).getName());
        if (data.get(position).getGeomType()==1){
            holder.shapeType.setImageResource(R.drawable.ic_polygon);
        }else{
            holder.shapeType.setImageResource(R.drawable.ic_circle);
        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geofenceSelection.geofenceSelected(data.get(position));
            }
        });


    }


    @Override
    public int getItemCount() {
        return data.size();
    }




    public class Holder extends RecyclerView.ViewHolder {
        TextView vehicleNumber;
        ImageView shapeType;
        LinearLayout mainLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            vehicleNumber = (TextView) itemView.findViewById(R.id.vehicle_number);
            shapeType = (ImageView) itemView.findViewById(R.id.shape_type);
            mainLayout = (LinearLayout) itemView.findViewById(R.id.mainLayout);
        }
    }


   /* public void updateData(List<GeofenceDatum> datumList1) {
        data = datumList1;
        notifyDataSetChanged();
    }*/
}



