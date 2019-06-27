package com.kushal.myapplication.ui.video.playlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.app.VideoSupportFragment;
import android.support.v17.leanback.app.VideoSupportFragmentGlueHost;
import android.support.v17.leanback.media.MediaPlayerAdapter;
import android.support.v17.leanback.media.PlaybackGlue;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.kushal.myapplication.model.browse.BrowseData;
import com.kushal.myapplication.utils.LogMgr;
import com.kushal.myapplication.utils.video.playlist.MediaControls;
import com.kushal.myapplication.utils.video.playlist.PlaylistControlGlue;
import com.kushal.myapplication.utils.video.playlist.VideoListAdapter;

import java.util.ArrayList;

public class VideoPlaylistFragment extends VideoSupportFragment implements MediaControls.IMediaButtonListener {
    private static final String TAG = "VideoPlaylistFragment";
    public static final String PLAYLIST_KEY = "playlist_key";

    private MediaSessionCompat mSession;
    private PlaylistControlGlue<MediaPlayerAdapter> mPlaybackControlGlue;
    private VideoListAdapter<BrowseData> listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playPlaylist();
    }

    private void playPlaylist() {
        // Step 1
        setUpListAdapter();
        // Step 2
        setUpMediaSession();
        // Step 3
        initializePlaybackControlsGlue();
        // Step 4
        setUpMediaControls();
        // Step 5
        playMedia(listAdapter.getCurrentItem());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlaybackControlGlue != null) {
            mPlaybackControlGlue.pause();
        }
    }

    private void setUpListAdapter() {
        final ArrayList<BrowseData> dataList =
                (ArrayList<BrowseData>) getActivity().getIntent().getSerializableExtra(PLAYLIST_KEY);
        listAdapter = new VideoListAdapter<>(dataList, 0);
    }

    private void setUpMediaSession() {
        mSession = new MediaSessionCompat(getContext(), TAG);
        mSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                        | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mSession.setActive(true);
        MediaControllerCompat.setMediaController((Activity) getContext(), mSession.getController());
    }

    private void initializePlaybackControlsGlue() {
        mPlaybackControlGlue = new PlaylistControlGlue<>(getActivity()
                , new MediaPlayerAdapter(getContext())
                , mSession.getController());
        mPlaybackControlGlue.setHost(new VideoSupportFragmentGlueHost(VideoPlaylistFragment.this));
        mPlaybackControlGlue.addPlayerCallback(new PlaybackControls());
    }

    private void setUpMediaControls() {
        mSession.setCallback(new MediaControls(mPlaybackControlGlue, listAdapter, this));
    }

    private void playMedia(MediaDescriptionCompat media) {
        mPlaybackControlGlue.setTitle(media.getTitle());
        mPlaybackControlGlue.setSubtitle(media.getDescription());
        mPlaybackControlGlue.getPlayerAdapter().setDataSource(media.getMediaUri());
        mPlaybackControlGlue.playWhenPrepared();
    }

    @Override
    public void onMediaButtonClicked(MediaDescriptionCompat media) {
        playMedia(media);
    }

    private class PlaybackControls extends PlaybackGlue.PlayerCallback {
        @Override
        public void onPlayCompleted(PlaybackGlue glue) {
            super.onPlayCompleted(glue);
            LogMgr.d(TAG, "onPlayCompleted: ");
            playMedia(listAdapter.skipToNextItem());
        }
    }
}
