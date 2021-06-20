package com.rptrack.plus.module.reportUi.reportAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.StoppageResponse.Point;
import com.rptrack.plus.DataModel.address.AddressResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;

import java.util.List;


public class StoppageReportAdapter extends RecyclerView.Adapter<StoppageReportAdapter.Holder> {

    Context mContext;

    private List<Point> points;
    APIUtility apiUtility = ApplicationActivity.getApiUtility();
    boolean isIdle=false;

    public StoppageReportAdapter(Context mContaxt1, List<Point> points1, boolean isIdle1) {
        mContext = mContaxt1;
        points = points1;
        isIdle=isIdle1;

    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView date, address, start_date, end_date, stoppage_duration;
        View color_view;

        public Holder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            address = (TextView) itemView.findViewById(R.id.address);
            start_date = (TextView) itemView.findViewById(R.id.start_date);
            end_date = (TextView) itemView.findViewById(R.id.end_date);
            stoppage_duration = (TextView) itemView.findViewById(R.id.stoppage_duration);
            color_view = (View) itemView.findViewById(R.id.color_view);

        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stoppage_report_adapter, parent, false);
        return new Holder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        if (isIdle){
            holder.color_view.setBackgroundColor(Color.YELLOW);
        }else {
            holder.color_view.setBackgroundColor(Color.RED);
        }
        holder.date.setText(dateCustomise(position));
        holder.start_date.setText(points.get(position).getAppStartDate().getCustomerTime());
        holder.end_date.setText(points.get(position).getAppEndDate().getCustomerTime());
        holder.stoppage_duration.setText(points.get(position).getDuration());
        getAddressFromLatLong(points.get(position).getLoc().getLat(), points.get(position).getLoc().getLng(), holder.address);
    }

    @Override
    public int getItemCount() {
        return points.size();

    }

    void getAddressFromLatLong(double lat, double lng, final TextView address1) {
        address1.setText("Address Loading...");
        address1.setTextColor(mContext.getResources().getColor(R.color.black));

        String urlStrAddon = "na/" + "na/" + lat + "/" + lng;
        String url = "http://newcoreapi.gpsapphub.com/api/Geocode/Geocode/" + urlStrAddon;
        apiUtility.getAddressFromLatLan(mContext, false, url, new APIUtility.APIResponseListener<AddressResponse>() {
            @Override
            public void onReceiveResponse(AddressResponse response) {
                address1.setText(response.getAddress());
            }

            @Override
            public void onErrorResponse(ErrorResponse errorMessage) {
                address1.setText("No Address Found");
            }

            @Override
            public void onFailureResponse(String string) {
                address1.setText(string);
            }
        });
    }

    private String dateCustomise(int position) {
        Point point = points.get(position);
        if (point.getAppStartDate().getOnlyCustomerDate().equals(point.getAppEndDate().getOnlyCustomerDate())) {
            return point.getAppStartDate().getOnlyCustomerDate();
        }
        return point.getAppStartDate().getOnlyCustomerDate() + "        :       " + point.getAppEndDate().getOnlyCustomerDate();
    }


}























