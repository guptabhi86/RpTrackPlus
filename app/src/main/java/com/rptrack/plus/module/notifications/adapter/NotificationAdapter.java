package com.rptrack.plus.module.notifications.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.R;
import com.rptrack.plus.alert.AlertTypeFactory;
import com.rptrack.plus.alert.IAlert;

import java.util.ArrayList;
import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> {

    Context mContext;
    List<AlertDatum> alertData = new ArrayList<>();

    public NotificationAdapter(Context mContaxt1) {
        mContext = mContaxt1;
    }

    public SettingItemClicked settingItemClicked;

    public interface SettingItemClicked {
        int Clicked(int Position);
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView messageTitle, textBody,text_time;
        ImageView alertType;

        public Holder(@NonNull View itemView) {
            super(itemView);
            messageTitle = itemView.findViewById(R.id.message_title);
            textBody = itemView.findViewById(R.id.text_body);
            alertType = itemView.findViewById(R.id.notification_type);
            text_time = itemView.findViewById(R.id.text_time);
        }
    }

    @NonNull
    @Override
    public NotificationAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_adapter, parent, false);
        return new NotificationAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.messageTitle.setText(alertData.get(position).getVehicleNo());
        holder.text_time.setText(alertData.get(position).getTimestamp().replace("T", " "));

        IAlert alertType = AlertTypeFactory.getInstance(mContext, alertData.get(position).getAlertTye());
        if (alertType == null)
            return;

        holder.textBody.setText(alertType.getMessage(alertData.get(position)));
        holder.alertType.setImageResource(alertType.getAlertIcon(alertData.get(position)));
    }

    @Override
    public int getItemCount() {
        return alertData.size();
    }

    public void updateData(List<AlertDatum> data) {
        alertData = data;
        notifyDataSetChanged();
    }

}























