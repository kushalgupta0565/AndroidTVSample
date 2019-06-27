package com.kushal.myapplication.utils.video.playlist;

import android.content.Context;
import android.support.v17.leanback.media.MediaPlayerAdapter;
import android.support.v17.leanback.media.PlaybackGlueHost;
import android.support.v17.leanback.media.PlaybackTransportControlGlue;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v4.media.session.MediaControllerCompat;


import com.kushal.myapplication.utils.LogMgr;

import java.util.concurrent.TimeUnit;

public class PlaylistControlGlue<T extends MediaPlayerAdapter> extends PlaybackTransportControlGlue<T> {

    private static final long FIVE_SECONDS = TimeUnit.SECONDS.toMillis(5);
    public static final String TAG = PlaylistControlGlue.class.getSimpleName();

    private final PlaybackControlsRow.SkipPreviousAction mSkipPreviousAction;
    private final PlaybackControlsRow.SkipNextAction mSkipNextAction;
    private final PlaybackControlsRow.FastForwardAction mFastForwardAction;
    private final PlaybackControlsRow.RewindAction mRewindAction;

    private final MediaControllerCompat.TransportControls mMediaSessionTransportControls;

    public PlaylistControlGlue(Context context, T adapter, MediaControllerCompat mediaController) {
        super(context, adapter);

        mMediaSessionTransportControls = mediaController.getTransportControls();

        mSkipPreviousAction = new PlaybackControlsRow.SkipPreviousAction(context);
        mSkipNextAction = new PlaybackControlsRow.SkipNextAction(context);
        mFastForwardAction = new PlaybackControlsRow.FastForwardAction(context);
        mRewindAction = new PlaybackControlsRow.RewindAction(context);
    }

    @Override
    protected void onCreatePrimaryActions(ArrayObjectAdapter primaryActionsAdapter) {
        super.onCreatePrimaryActions(primaryActionsAdapter);
        primaryActionsAdapter.add(mSkipPreviousAction);
        primaryActionsAdapter.add(mRewindAction);
        primaryActionsAdapter.add(mFastForwardAction);
        primaryActionsAdapter.add(mSkipNextAction);
    }

    @Override
    public void onActionClicked(Action action) {
        if (action == mRewindAction) {
            rewind();
        } else if (action == mFastForwardAction) {
            fastForward();
        } else {
            // Super class handles play/pause and delegates to abstract methods next()/previous().
            super.onActionClicked(action);
        }
    }

    @Override
    public void next() {
        mMediaSessionTransportControls.skipToNext();
    }

    @Override
    public void previous() {
        mMediaSessionTransportControls.skipToPrevious();
    }

    private void rewind() {
        long newPosition = getCurrentPosition() - FIVE_SECONDS;
        newPosition = (newPosition < 0) ? 0 : newPosition;
        mMediaSessionTransportControls.seekTo(newPosition);
    }

    private void fastForward() {
        if (getDuration() > -1) {
            long newPosition = getCurrentPosition() + FIVE_SECONDS;
            newPosition = (newPosition > getDuration()) ? getDuration() : newPosition;
            mMediaSessionTransportControls.seekTo(newPosition);
        }
    }

    @Override
    protected void onAttachedToHost(PlaybackGlueHost host) {
        super.onAttachedToHost(host);
        LogMgr.d(TAG, "onAttachedToHost");
        host.hideControlsOverlay(true);
    }
}
