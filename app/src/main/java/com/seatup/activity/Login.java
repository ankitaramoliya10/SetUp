package com.seatup.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.seatup.Baseclass.BaseActivity;
import com.seatup.Common.Utils;
import com.seatup.R;
import com.seatup.commonPojo.LoginRequest;
import com.seatup.commonPojo.LoginResponse;
import com.seatup.widgets.DEditText;
import com.seatup.widgets.DTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends BaseActivity {

    @BindView(R.id.etEmailId)
    DEditText etEmailId;
    @BindView(R.id.etPassword)
    DEditText etPassword;
    @BindView(R.id.tvForgotPassword)
    DTextView tvForgotPassword;
    @BindView(R.id.ivSignIn)
    ImageView ivSignIn;
    @BindView(R.id.ivGoogleSignIn)
    ImageView ivGoogleSignIn;
    @BindView(R.id.ivFacebookSignIn)
    ImageView ivFacebookSignIn;
    @BindView(R.id.laySignUp)
    LinearLayout laySignUp;
    private TelephonyManager telephonyManager;
    private String IMEI_Number_Holder;
    private String emailId, password;
    private Context context;
    private Callback<LoginResponse> loginResponseCallback = new Callback<LoginResponse>() {
        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            //do Success functionality
        }

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {
            //do Failure functionality
            call.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = Login.this;

        if (marshMallowPermission.checkPermissionForReadPhoneState()) {
            marshMallowPermission.requestPermissionForReadPhoneState();
        } else {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            IMEI_Number_Holder = telephonyManager.getDeviceId();
            log.LOGI("IMEI_Number_Holder" + IMEI_Number_Holder);

        }
        String str = Build.MODEL;
        log.LOGI("str" + str);

//        setAppInBackground();
    }

    private void setAppInBackground() {

        //Getting back App in back
        PackageManager pb = getPackageManager();
        ComponentName componentNameBack = new ComponentName(context, Login.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
        pb.setComponentEnabledSetting(componentNameBack, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        //Getting back App in front
        PackageManager pf = getPackageManager();
        ComponentName componentNameFront = new ComponentName(context, Login.class);
        pf.setComponentEnabledSetting(componentNameFront, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    @OnClick(R.id.ivSignIn)
    public void onViewClicked() {
        callApi();
    }

    private void callApi() {
        emailId = etEmailId.getString();
        password = etPassword.getString();
        if (isValidate()) {
            hideKeyboard();
            if (Utils.isInternetConnected(context)) {
                showDialog();
                LoginRequest request = new LoginRequest();
                request.setUserEmailId(emailId);
                request.setSubUserName(password);
                requestAPI.postLoginRequest(request).enqueue(loginResponseCallback);
            } else {
                showNoInternetDialog();
            }
        }
    }

    private boolean isValidate() {
        if (etEmailId.getString().equalsIgnoreCase("")) {
            etEmailId.setError(getString(R.string.email_id_required));
            etEmailId.setFocusable(true);
            return false;
        }
        if (etPassword.getString().equalsIgnoreCase("")) {
            etPassword.setError(getString(R.string.required_password));
            etPassword.setFocusable(true);
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exitActivityAnimation();
    }
}
