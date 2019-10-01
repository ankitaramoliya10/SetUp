package com.seatup.activity;

import android.content.Intent;
import android.os.Bundle;

import com.seatup.Baseclass.BaseActivity;
import com.seatup.R;
import com.seatup.widgets.DButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashScreen extends BaseActivity {

    @BindView(R.id.btnStartNow)
    DButton btnStartNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnStartNow)
    public void onViewClicked() {
        Intent intent = new Intent(SplashScreen.this, Login.class);
        startActivity(intent);
        startActivityAnimation();
    }
}
