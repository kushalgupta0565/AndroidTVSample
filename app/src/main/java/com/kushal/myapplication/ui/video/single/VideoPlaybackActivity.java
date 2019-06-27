package com.kushal.myapplication.ui.video.single;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.kushal.myapplication.R;


public class VideoPlaybackActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
