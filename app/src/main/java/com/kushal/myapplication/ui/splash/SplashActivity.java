package com.kushal.myapplication.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.kushal.myapplication.R;
import com.kushal.myapplication.ui.browse.BrowseActivity;

public class SplashActivity extends FragmentActivity {

    public static final long SPLASH_TIME = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, BrowseActivity.class));
                finish();
            }
        }, SPLASH_TIME);
    }
}
