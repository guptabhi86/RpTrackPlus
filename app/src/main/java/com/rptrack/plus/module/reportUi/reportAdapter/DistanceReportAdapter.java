package com.rptrack.plus.module.reportUi.reportAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.DataModel.DistanceReport.Distance;
import com.rptrack.plus.R;

import java.util.List;


public class DistanceReportAdapter extends RecyclerView.Adapter<DistanceReportAdapter.Holder> {

    Context mContext;
    List<Distance> distance;
    String stringDistance = "-";
    double totalDistance = 0.0;


    public DistanceReportAdapter(Context mContaxt1, List<Distance> distance1) {
        mContext = mContaxt1;
        distance = distance1;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView date;
        TextView previous_day_distance, total_distance, currentDayDistance;

        public Holder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            previous_day_distance = (TextView) itemView.findViewById(R.id.previous_day_distance);
            currentDayDistance = (TextView) itemView.findViewById(R.id.current_day_distance);
            total_distance = (TextView) itemView.findViewById(R.id.total_distance);
        }

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.distance_report_adapter, parent, false);
        return new Holder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        totalDistance = totalDistance + distance.get(position).getDistance();
        holder.previous_day_distance.setText(stringDistance);
        holder.total_distance.setText( String.format("%.2f", totalDistance));
        holder.date.setText(distance.get(position).getApplicationDate().getOnlyCustomerDate());
        holder.currentDayDistance.setText(String.valueOf(distance.get(position).getDistance()));
        stringDistance = String.valueOf(distance.get(position).getDistance());
    }

    @Override
    public int getItemCount() {
        return distance.size();

    }


}























