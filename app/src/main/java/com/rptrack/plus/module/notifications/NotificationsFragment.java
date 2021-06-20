package com.rptrack.plus.module.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.alerts.AlertDatum;
import com.rptrack.plus.DataModel.alerts.AlertResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.activity.AlertSettingsActivity;
import com.rptrack.plus.module.notifications.adapter.NotificationAdapter;
import com.rptrack.plus.module.notifications.adapter.PaginationScrollListener;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private NotificationsViewModel notificationsViewModel;
    RecyclerView notification_list;
    APIUtility apiUtility;
    NotificationAdapter notificationAdapter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    ArrayList<AlertDatum> alertData = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        TextView textTitle = (TextView) root.findViewById(R.id.text_title);
        ImageView back_icon = (ImageView) root.findViewById(R.id.back_icon);
        ImageView refresh_icon = (ImageView) root.findViewById(R.id.refresh_icon);
        back_icon.setImageResource(R.drawable.settings);
        refresh_icon.setVisibility(View.GONE);
        apiUtility = ApplicationActivity.getApiUtility();
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AlertSettingsActivity.class));
            }
        });
        refresh_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        back_icon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
        refresh_icon.setImageResource(R.drawable.operation);
        textTitle.setText(Preferences.getPreference(getActivity(), Constant.USER_NAME));
        notification_list = (RecyclerView) root.findViewById(R.id.notification_list);
        notificationAdapter = new NotificationAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        notification_list.setLayoutManager(linearLayoutManager);
        notification_list.setAdapter(notificationAdapter);
        getNotificationListAttempt(0, null);

        notification_list.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems(int totalItemCount) {
                isLoading = true;
                Log.d("ENDOFPOSIT", "end");
                getNotificationListAttempt(0, alertData.get(totalItemCount - 1).getCreationTime());
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {

    }

    void getNotificationListAttempt(int id, String lastTime) {

        String url = Constant.BASE_URL+"Alerts";
        if (lastTime != null) {
            url = Constant.BASE_URL+"Alerts?lastTime=" + lastTime;
        }

        apiUtility.getAllAlerts(getActivity(), true, url, new APIUtility.APIResponseListener<AlertResponse>() {
            @Override
            public void onReceiveResponse(AlertResponse response) {
                for (AlertDatum alertDatum : response.getResult().getData()) {
                    if (!alertData.contains(alertDatum)) {
                        alertData.add(alertDatum);
                    }
                }
                notificationAdapter.updateData(alertData);
                isLoading = false;
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(getActivity(), errorResponse.getErrorMessage().getStringData());
            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(getActivity(), string);
            }
        });
    }
}