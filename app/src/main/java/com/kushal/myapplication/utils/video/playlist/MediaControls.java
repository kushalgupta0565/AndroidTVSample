package com.kushal.myapplication.utils.video.playlist;

import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.kushal.myapplication.utils.LogMgr;


public class MediaControls extends MediaSessionCompat.Callback {

    public static final String TAG = MediaControls.class.getSimpleName();
    PlaylistControlGlue<?> glue;
    VideoListAdapter<?> listAdapter;
    IMediaButtonListener callback;

    public MediaControls(PlaylistControlGlue<?> glue, VideoListAdapter<?> listAdapter, IMediaButtonListener callback) {
        this.glue = glue;
        this.listAdapter = listAdapter;
        this.callback = callback;
    }

    @Override
    public void onPlay() {
        super.onPlay();
        LogMgr.d(TAG, "onPlay: ");
        glue.play();
    }

    @Override
    public void onPause() {
        super.onPause();
        LogMgr.d(TAG, "onPause: ");
        glue.pause();
    }

    @Override
    public void onSkipToNext() {
        super.onSkipToNext();
        LogMgr.d(TAG, "onSkipToNext: ");
        callback.onMediaButtonClicked(listAdapter.skipToNextItem());
    }

    @Override
    public void onSkipToPrevious() {
        super.onSkipToPrevious();
        LogMgr.d(TAG, "onSkipToPrevious: ");
        callback.onMediaButtonClicked(listAdapter.skipToPreviousItem());
    }

    @Override
    public void onSeekTo(long pos) {
        super.onSeekTo(pos);
        LogMgr.d(TAG, "onSeekTo: ");
        glue.seekTo(pos);
    }

    public interface IMediaButtonListener {
        void onMediaButtonClicked(MediaDescriptionCompat media);
    }
}