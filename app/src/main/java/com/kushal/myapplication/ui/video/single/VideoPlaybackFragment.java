package com.kushal.myapplication.ui.video.single;

import android.net.Uri;
import android.os.Bundle;
import android.support.v17.leanback.app.VideoSupportFragment;
import android.support.v17.leanback.app.VideoSupportFragmentGlueHost;
import android.support.v17.leanback.media.MediaPlayerAdapter;

import com.kushal.myapplication.model.browse.BrowseData;
import com.kushal.myapplication.ui.browse.BrowseActivity;
import com.kushal.myapplication.utils.video.single.PlaybackControlsGlue;


public class VideoPlaybackFragment extends VideoSupportFragment {

    private PlaybackControlsGlue<MediaPlayerAdapter> mPlaybackControlGlue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BrowseData video =
                (BrowseData) getActivity().getIntent().getSerializableExtra(BrowseActivity.BROWSE_ITEM);

        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(VideoPlaybackFragment.this);

        MediaPlayerAdapter playerAdapter = new MediaPlayerAdapter(getActivity());

        mPlaybackControlGlue = new PlaybackControlsGlue<MediaPlayerAdapter>(getActivity(), playerAdapter);
        mPlaybackControlGlue.setHost(glueHost);
        mPlaybackControlGlue.setTitle(video.getContentName());
        mPlaybackControlGlue.setSubtitle(video.getContentDesc());
        mPlaybackControlGlue.playWhenPrepared();
        try {
            playerAdapter.setDataSource(Uri.parse(video.getContentVideoUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlaybackControlGlue != null) {
            mPlaybackControlGlue.pause();
        }
    }
}
