package com.kushal.myapplication.ui.video.playlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.kushal.myapplication.R;


public class VideoPlaylistActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playlist);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}
