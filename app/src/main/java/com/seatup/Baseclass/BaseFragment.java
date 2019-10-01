package com.seatup.Baseclass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import com.seatup.Common.GsonUtils;
import com.seatup.Common.LogUtils;
import com.seatup.Common.MarshMallowPermission;
import com.seatup.Common.Preferences;
import com.seatup.R;
import com.seatup.RetrofitApiCall.APIClient;
import com.seatup.RetrofitApiCall.RequestAPI;
import com.seatup.activity.Login;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Ketan on 21/06/16.
 */
public class BaseFragment extends Fragment {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public Preferences pref;
    public GsonUtils gsonUtils;
    public MarshMallowPermission marshMallowPermission;
    public RequestAPI requestAPI;
    public LogUtils log;
    AlertDialog dialog;
    private Toast toast;

    /**
     * get String which you have stored in string resource file
     *
     * @param msg
     * @return
     */
    public String getStringResourceByName(String msg) {
        String packageName = getActivity().getPackageName();
        int resId = getResources().getIdentifier(msg, "string", packageName);
        return getString(resId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        gsonUtils = GsonUtils.getInstance();
        pref = new Preferences(getActivity());
        requestAPI = APIClient.getClient().create(RequestAPI.class);
        log = new LogUtils(getActivity().getClass());
        marshMallowPermission = new MarshMallowPermission(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // Hide Keyboard when it click/focus out of any editText
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

    //if error code 3 means user loggedOut
    public void checkResponseCode() {
        pref.clear();
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * show progress dialog
     */
    public void showDialog() {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_progress, null);
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

    //Hide Keyboard out of any edit text
    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public void showToast(final int text, final boolean isShort) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                toast.setText(getString(text).toString());
                toast.setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void showToast(final String text, final boolean isShort) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                toast.setText(text);
                toast.setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
                toast.show();
            }
        });
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
                SweetAlertDialog sd = new SweetAlertDialog(getActivity());
                sd.setCancelable(true);
                sd.setCanceledOnTouchOutside(true);
                sd.show();
                break;
            case "under":
                new SweetAlertDialog(getActivity())
                        .setContentText("It's pretty, isn't it?")
                        .show();
                break;
            case "error":
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(titleText)
                        .setContentText(ContentText)
                        .show();
                break;
            case "warning":
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(titleText)
                        .setContentText(ContentText)
                        .show();
                break;
            case "success":
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(titleText)
                        .setContentText(ContentText)
                        .show();
                break;
            case "warning_confirm":
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
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
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setCancelText("No,cancel plx!")
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
                new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText(getString(R.string.title_no_internet))
                        .setContentText(getString(R.string.msg_no_internet))
                        .setCustomImage(R.drawable.ic_no_internet)
                        .show();
                break;

        }
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
}