package com.rptrack.plus.module.notifications.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.alert.AlertType;
import com.rptrack.plus.R;

import java.util.HashMap;


public class AlertTypesAdapter extends RecyclerView.Adapter<AlertTypesAdapter.Holder> {
    Context context;
    HashMap<AlertType, Boolean> alerts;

    public AlertTypesAdapter(Context context, HashMap<AlertType, Boolean> data, SettingChanged settingChanged) {
        this.context = context;
        alerts = data;
        settingItemClicked = settingChanged;
    }

    public SettingChanged settingItemClicked;

    public interface SettingChanged {
        void changed(AlertType alertType, Boolean status);
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView alertHeading;
        ImageView isChecked;
        RelativeLayout rlChecked;

        public Holder(@NonNull View itemView) {
            super(itemView);
            alertHeading = itemView.findViewById(R.id.tv_alert_heading);
            isChecked = itemView.findViewById(R.id.iv_icon_checked);
            rlChecked = itemView.findViewById(R.id.rl_checked_change);
        }
    }

    @NonNull
    @Override
    public AlertTypesAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_type_notification, parent, false);
        return new AlertTypesAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        AlertType alertType = AlertType.castIntToEnum(position + 1);

        boolean isSelected = alerts.get(alertType);
        holder.alertHeading.setText(alertType.getAlertAsString());
        holder.isChecked.setSelected(isSelected);

        holder.rlChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.isChecked.setSelected(!holder.isChecked.isSelected());
                settingItemClicked.changed(alertType, holder.isChecked.isSelected());
            }
        });
    }

    @Override
    public int getItemCount() {
        return alerts.size();
    }
}























