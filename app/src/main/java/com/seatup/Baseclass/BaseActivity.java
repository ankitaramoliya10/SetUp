package com.seatup.Baseclass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seatup.Common.Constants;
import com.seatup.Common.GsonUtils;
import com.seatup.Common.LogUtils;
import com.seatup.Common.MarshMallowPermission;
import com.seatup.Common.Preferences;
import com.seatup.R;
import com.seatup.RetrofitApiCall.APIClient;
import com.seatup.RetrofitApiCall.RequestAPI;
import com.seatup.activity.Login;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class BaseActivity extends AppCompatActivity {

    public static final int REQUEST_CHECK_SETTINGS = 10;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public RequestAPI requestAPI;
    public Preferences pref;
    public GsonUtils gsonUtils;
    public MarshMallowPermission marshMallowPermission;
    public LogUtils log;
    AlertDialog dialog;
    private Toast toast;
    private int i = -1;
//    private GoogleApiClient mGoogleApiClient;

    /* Initiate Google API Client  */
//    private void initGoogleAPIClient() {
//        //Without Google API Client Auto Location Dialog will not work
//        mGoogleApiClient = new GoogleApiClient.Builder(BaseActivity.this)
//                .addApi(LocationServices.API)
//                .build();
//        mGoogleApiClient.connect();
//    }


    /**
     * Click on "OK" means turn on Location automatic
     */
//    public void onGPSAlertDialog() {
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priority of Location request to high
//        locationRequest.setInterval(30 * 1000);
//        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off
//
//        PendingResult<LocationSettingsResult> result =
//                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @TargetApi(Build.VERSION_CODES.DONUT)
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                final LocationSettingsStates state = result.getLocationSettingsStates();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        // All location settings are satisfied. The client can initialize location
//                        // requests here.
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        // Location settings are not satisfied. But could be fixed by showing the user
//                        // a dialog.
//                        try {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            status.startResolutionForResult(BaseActivity.this, REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException e) {
//                            e.printStackTrace();
//                            // Ignore the error.
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        // Location settings are not satisfied. However, we have no way to fix the
//                        // settings so we won't show the dialog.
//                        break;
//                }
//            }
//        });
//    }

    //Hide Keyboard when it click/focus out of any edit text
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard();
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        Log.e("Settings", "Result OK");
                        Constants.isGpsOn = true;
                        break;
                    case RESULT_CANCELED:
                        Log.e("Settings", "Result Cancel");
                        Constants.isGpsOn = false;
                        showToast("You're no longer able to near by Search ", true);
                        break;
                }
                break;
        }
    }

    /**
     * @param dialogName
     * @param titleText
     * @param ContentText
     */
    public void setSweetDialog(String dialogName, String titleText, String ContentText) {
        switch (dialogName) {
            case "basic":
                // default title "Here's a message!"
                SweetAlertDialog sd = new SweetAlertDialog(this);
                sd.setCancelable(true);
                sd.setCanceledOnTouchOutside(true);
                sd.show();
                break;
            case "under":
                new SweetAlertDialog(this)
                        .setContentText("It's pretty, isn't it?")
                        .show();
                break;
            case "error":
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(titleText)
                        .setContentText(ContentText)
                        .show();
                break;
            case "warning":
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(titleText)
                        .setContentText(ContentText)
                        .show();
                break;
            case "success":
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(titleText)
                        .setContentText(ContentText)
                        .show();
                break;
            case "warning_confirm":
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance
                                sDialog.setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
                break;
            case "warning_cancel":
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setCancelText("No,cancel plz!")
                        .setConfirmText("Yes,delete it!")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance, keep widget user state, reset them if you need
                                sDialog.setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
                break;
            case "internet":
                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText(getString(R.string.title_no_internet))
                        .setContentText(getString(R.string.msg_no_internet))
                        .setCustomImage(R.drawable.ic_no_internet)
                        .show();
                break;
            case "progress_dialog":
                final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("Loading");
                pDialog.show();
                pDialog.setCancelable(false);
                new CountDownTimer(800 * 7, 800) {
                    public void onTick(long millisUntilFinished) {
                        // you can change the progress bar color by ProgressHelper every 800 millis
                        i++;
                        switch (i) {
                            case 0:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                                break;
                            case 1:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                                break;
                            case 2:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                            case 3:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                                break;
                            case 4:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                                break;
                            case 5:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                                break;
                            case 6:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                        }
                    }

                    public void onFinish() {
                        i = -1;
                        pDialog.setTitleText("Success!")
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                }.start();
                break;
        }
    }

    /**
     * get String which you have stored in string resource file
     *
     * @param msg
     * @return
     */
    public String getStringResourceByName(String msg) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(msg, "string", packageName);
        return getString(resId);
    }

    public void exitActivityAnimation() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }

    public void startActivityAnimation() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

    }

    /**
     * show progress dialog
     */
    public void showDialog() {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_progress, null);
        dialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.show();

    }

    /**
     * dismiss progress dialog
     */
    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    /**
     * Starting Point of Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        requestAPI = APIClient.getClient().create(RequestAPI.class);
        log = new LogUtils(this.getClass());
        pref = new Preferences(getActivity());
        gsonUtils = GsonUtils.getInstance();
        marshMallowPermission = new MarshMallowPermission(getActivity());
//        initGoogleAPIClient();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * User loggedIn/LoggedOut
     *
     * @return
     */
    public boolean isLoggedIn() {
        return pref.getBoolean(Constants.IS_LOGIN, false);
    }

    //Hide Keyboard out of any edit text
    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View focusedView = getActivity().getCurrentFocus();
            /*
             * If no view is focused, an NPE will be thrown
             *
             * Maxim Dmitriev
             */
            if (focusedView != null) {
                imm.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
//            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(final int text, final boolean isShort) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                toast.setText(getString(text).toString());
                toast.setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void showToast(final String text, final boolean isShort) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                toast.setText(text);
                toast.setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public BaseActivity getActivity() {
        return this;
    }

    // When internet is gone
    public void showNoInternetDialog() {
        try {
            final Dialog introDialog = new Dialog(getActivity());
            introDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            introDialog.setContentView(R.layout.dialog_internet);
            introDialog.setCancelable(false);
            introDialog.setCanceledOnTouchOutside(false);
            Window window = introDialog.getWindow();
            window.setBackgroundDrawableResource(android.R.color.transparent);
            TextView dialogTitle = (TextView) window.findViewById(R.id.textviewTitle);
            TextView dialogMessage = (TextView) window.findViewById(R.id.textviewMessage);
            Button okButton = (Button) window.findViewById(R.id.buttonAccept);
            dialogTitle.setText(getString(R.string.title_no_internet));
            dialogMessage.setText(getString(R.string.msg_no_internet));
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    introDialog.dismiss();
                    getActivity().finish();

                }
            });
            introDialog.show();
        } catch (Exception e) {
            Log.e("error-->>", e.getMessage());
        }
    }

    //if error code 3 means user loggedOut
    public void checkResponseCode() {
        pref.clear();
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        startActivityAnimation();
        finish();
    }
}