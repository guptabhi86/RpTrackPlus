package com.rptrack.plus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.BuildConfig;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rptrack.plus.ApplicationActivity;
import com.rptrack.plus.DataModel.error_response.ErrorResponse;
import com.rptrack.plus.DataModel.login.LoginBaseResponse;
import com.rptrack.plus.DataModel.login.LoginRequest;
import com.rptrack.plus.R;
import com.rptrack.plus.Retrofit.APIUtility;
import com.rptrack.plus.utilities.CommonUtils;
import com.rptrack.plus.utilities.Constant;
import com.rptrack.plus.utilities.Preferences;

import static com.rptrack.plus.utilities.CommonUtils.isValidPassword;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passEditText;
    TextView forgetPassword, versionName;
    ImageView close_icon, img_show_pass;
    TextView submit;
    //  CheckBox rememberMe;
    APIUtility apiUtility;
    RelativeLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        apiUtility = ApplicationActivity.getApiUtility();
        emailEditText = (EditText) findViewById(R.id.login_user_name);
        passEditText = (EditText) findViewById(R.id.login_password);
        submit = (TextView) findViewById(R.id.login_btn);
        versionName = (TextView) findViewById(R.id.version_name);

        try {
            PackageInfo pInfo = LoginActivity.this.getPackageManager().getPackageInfo(LoginActivity.this.getPackageName(), 0);
            String version = pInfo.versionName;
            versionName.setText("Version Name: " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // rememberMe = (CheckBox) findViewById(R.id.remember_me);
        // img_show_pass=(ImageView)findViewById(R.id.img_show_pass);
        submit.setOnClickListener(clickListener);

        if (!Preferences.getPreference(LoginActivity.this, Constant.ACCESS_TOKEN).isEmpty()) {
            Intent intventryIntent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intventryIntent);
            finish();
        }
        if (!Preferences.getPreference(LoginActivity.this, Constant.USER_NAME).isEmpty()) {
            emailEditText.setText(Preferences.getPreference(LoginActivity.this, Constant.USER_NAME));
            passEditText.setText(Preferences.getPreference(LoginActivity.this, Constant.LOGIN_PASSWORD));
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Validation();
        }
    };


    void Validation() {
        emailEditText.setError(null);
        passEditText.setError(null);
        String text_email = emailEditText.getText().toString();
        String text_password = passEditText.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(text_email)) {
            emailEditText.setError("Enter Login Detail");
            focusView = emailEditText;
            cancel = true;
        } else if (TextUtils.isEmpty(text_password) || !isValidPassword(text_password)) {
            passEditText.setError("Invalid Password");
            focusView = passEditText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            String regId = Preferences.getPreference(LoginActivity.this, Constant.FCM_REGID);
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d("FCMToken", refreshedToken + "h");
            LoginRequestService(text_email, text_password, refreshedToken);
        }
    }


    void LoginRequestService(final String userName, final String password, String regId) {
        String uniqueID = Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID);
        String buildVersin = String.valueOf(BuildConfig.VERSION_NAME);
        String versionName = "NotAvailble";
        try {
            PackageInfo pInfo = LoginActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String phoneModel = String.valueOf(Build.MODEL);
        LoginRequest loginRequest = new LoginRequest(userName, password, regId, phoneModel, "1", versionName, buildVersin);
        apiUtility.LoginRequestAttemp(LoginActivity.this, true, loginRequest, new APIUtility.APIResponseListener<LoginBaseResponse>() {
            @Override
            public void onReceiveResponse(LoginBaseResponse response) {

               /* if (rememberMe.isChecked()) {
                    Preferences.setPreference(LoginActivity.this, Constant.LOGIN_ID, userName);
                    Preferences.setPreference(LoginActivity.this, Constant.LOGIN_PASSWORD, password);
                } else {
                    Preferences.removePreference(LoginActivity.this, Constant.LOGIN_ID);
                    Preferences.removePreference(LoginActivity.this, Constant.LOGIN_PASSWORD);
                }
*/
                Intent intventryIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                //intventryIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Preferences.setPreference(LoginActivity.this, Constant.ACCESS_TOKEN, response.getResult().getToken());
                Preferences.setPreference(LoginActivity.this, Constant.USER_NAME, userName);
                startActivity(intventryIntent);

                finish();
            }

            @Override
            public void onErrorResponse(ErrorResponse errorResponse) {
                CommonUtils.alert(LoginActivity.this, errorResponse.getErrorMessage().getStringData());
                //Toast.makeText(LoginActivity.this,errorResponse.getErrorMessage().getStringMessage(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailureResponse(String string) {
                CommonUtils.alert(LoginActivity.this, string);
            }

        });

    }

}