package com.rptrack.plus.module.profile;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.DashboardResponse.DashboardResponse;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.DataModel.logout.LogoutBaseResponse;
import com.rptrack.plus.DataModel.logout.LogoutRequest;
import com.rptrack.plus.FirebaseMessaging.DatabaseHelper;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.activity.LoginActivity;
import com.rptrack.plus.module.geofence.GeofenceListActivity;
import com.rptrack.plus.module.monitor.MonitorViewModel;
import com.rptrack.plus.module.profile.adapter.ProfileAdapter;
import com.rptrack.plus.module.reportUi.ReportActivity;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements ProfileAdapter.ClickedListener {


    private ProfileViewModel mViewModel;
    APIUtility apiUtility;
    RecyclerView profileRecycler;
    RelativeLayout logoutLayouot;
    DashboardResponse dashboardResponse;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView textTitle = (TextView) root.findViewById(R.id.text_title);
        TextView versionName = (TextView) root.findViewById(R.id.version_name);
        TextView login_user_name = (TextView) root.findViewById(R.id.login_user_name);
        login_user_name.setText(Preferences.getPreference(getActivity(), Constant.LOGIN_ID));
        textTitle.setText("ME");
        ImageView refreshIco = root.findViewById(R.id.refresh_icon);
        ImageView backIcon = root.findViewById(R.id.back_icon);
        backIcon.setVisibility(View.GONE);
        refreshIco.setVisibility(View.GONE);
        profileRecycler = (RecyclerView) root.findViewById(R.id.profile_item_list);
        apiUtility = ApplicationActivity.getApiUtility();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        ArrayList<TItleIcon> arrayList = new ArrayList<>();
 /*       arrayList.add(new TItleIcon("Schedule for report", R.drawable.icon_setting_task));*/
        arrayList.add(new TItleIcon("Generated reports", R.drawable.icon_setting_report));
        arrayList.add(new TItleIcon("Geo-fence", R.drawable.icon_setting_fence));
      /*  arrayList.add(new TItleIcon("POI", R.drawable.icon_setting_poi));
        arrayList.add(new TItleIcon("Message", R.drawable.icon_message));
        arrayList.add(new TItleIcon("General", R.drawable.icon_setting_general));*/

        ProfileAdapter profileAdapter = new ProfileAdapter(getActivity(), arrayList,this);
        profileRecycler.setLayoutManager(linearLayoutManager);
        profileRecycler.setAdapter(profileAdapter);
        logoutLayouot = root.findViewById(R.id.logout_layout);
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            String version = pInfo.versionName;
            versionName.setText("Version Name: " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        logoutLayouot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAttempt();
            }
        });


        new ViewModelProvider(this.getActivity()).get(MonitorViewModel.class).getData().observe(getViewLifecycleOwner(), dashboardResponse -> {
            this.dashboardResponse = dashboardResponse;
        });

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    public void logoutAttempt() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.app_name)
                .setMessage("Are you sure !  want to logout ?")
                .setCancelable(false)
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("Yes", (dialog, which) -> {
                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    loggingOut(refreshedToken);
                    dialog.dismiss();
                })
                .setIcon(getResources().getDrawable(R.drawable.logo))
                .show();

    }

    void loggingOut(String fcmToken) {
        apiUtility.LogoutRequest(getActivity(), true, new LogoutRequest(fcmToken), new APIUtility.APIResponseListener<LogoutBaseResponse>() {
            @Override
            public void onReceiveResponse(LogoutBaseResponse response) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                Preferences.removePreference(getActivity(), Constant.ACCESS_TOKEN);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Preferences.removePreference(getActivity(), Constant.DEVICE_ID);
                startActivity(intent);
                ActivityCompat.finishAffinity(getActivity());
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                databaseHelper.deleteAllRow();
                getActivity().finish();

            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(getActivity(), errorResponse.getErrorMessage().getStringMessage());

            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(getActivity(), string);
            }
        });

    }

    @Override
    public void clicekPosition(int position) {
        if (position==0){
            Intent reportInt=new Intent (getActivity(), ReportActivity.class);
            reportInt.putExtra(Constant.INTENT_SERIALIZABLE,dashboardResponse);
            startActivity(reportInt);
        }else {

                Intent geofencelist = new Intent(getActivity(), GeofenceListActivity.class);
                startActivity(geofencelist);

        }
    }
}