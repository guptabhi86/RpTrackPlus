package com.rptrack.plus.module.reportUi.reportAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.TripReport.TripPoint;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.utilities.CommonUtils;

import java.util.List;


public class TripReportAdapter extends RecyclerView.Adapter<TripReportAdapter.Holder> {

    Context mContext;
    private List<TripPoint> points;
    APIUtility apiUtility = ApplicationActivity.getApiUtility();
    public TripReportAdapter(Context mContaxt1, List<TripPoint> points1) {
        mContext = mContaxt1;
        points = points1;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView date_time, address, start_date, end_date, stoppage_duration,address_end,avg_speed,max_speed,trip_distance;
        View color_view;
        LinearLayout parrentLayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            date_time = (TextView) itemView.findViewById(R.id.date_time);
            address = (TextView) itemView.findViewById(R.id.address);
            address_end = (TextView) itemView.findViewById(R.id.address_end);
            start_date = (TextView) itemView.findViewById(R.id.start_date);
            end_date = (TextView) itemView.findViewById(R.id.end_date);
            avg_speed = (TextView) itemView.findViewById(R.id.avg_speed);
            max_speed = (TextView) itemView.findViewById(R.id.max_speed);
            trip_distance = (TextView) itemView.findViewById(R.id.trip_distance);
            stoppage_duration = (TextView) itemView.findViewById(R.id.stoppage_duration);
            color_view = (View) itemView.findViewById(R.id.color_view);
            parrentLayout = (LinearLayout)itemView.findViewById(R.id.parrentLayout);
            }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_report_adapter, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
       // holder.color_view.setBackgroundColor(Color.RED);
        holder.date_time.setText(dateCustomise(position));
        holder.start_date.setText(points.get(position).getAppStartDate().getCustomerTime());
        holder.end_date.setText(points.get(position).getAppEndDate().getCustomerTime());
        holder.stoppage_duration.setText(points.get(position).getDuration());
        holder.avg_speed.setText(String.valueOf(points.get(position).getAvgSpeed()));
        holder.max_speed.setText(String.valueOf(points.get(position).getMaxSpeed()));
        holder.trip_distance.setText(String.valueOf(points.get(position).getDistance()));
        CommonUtils.setAddressOnTextView(mContext,points.get(position).getStart().getLat(), points.get(position).getStart().getLng(), holder.address);
        CommonUtils.setAddressOnTextView(mContext,points.get(position).getEnd().getLat(), points.get(position).getEnd().getLng(), holder.address_end);
        holder.parrentLayout.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {

     }
 });

    }

    @Override
    public int getItemCount() {
        return points.size();
    }



    private String dateCustomise(int position) {
        TripPoint point = points.get(position);
        if (point.getAppStartDate().getOnlyCustomerDate().equals(point.getAppEndDate().getOnlyCustomerDate())) {
            return point.getAppStartDate().getOnlyCustomerDate();
        }
        return point.getAppStartDate().getOnlyCustomerDate() + "        :       " + point.getAppEndDate().getOnlyCustomerDate();
    }


}























