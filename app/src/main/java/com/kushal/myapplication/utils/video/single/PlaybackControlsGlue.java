package com.kushal.myapplication.utils.video.single;

import android.app.Activity;
import android.os.Handler;
import android.support.v17.leanback.media.MediaPlayerAdapter;
import android.support.v17.leanback.media.PlaybackTransportControlGlue;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.widget.Toast;

public class PlaybackControlsGlue<T extends MediaPlayerAdapter> extends PlaybackTransportControlGlue<MediaPlayerAdapter> {

    private PlaybackControlsRow.RepeatAction mRepeatAction;

    public PlaybackControlsGlue(Activity context, T adapter) {
        super(context, adapter);
        mRepeatAction = new PlaybackControlsRow.RepeatAction(context);
        setControlsOverlayAutoHideEnabled(true);
    }

    @Override
    protected void onCreatePrimaryActions(ArrayObjectAdapter adapter) {
        super.onCreatePrimaryActions(adapter);
        adapter.add(mRepeatAction);
    }

    @Override
    public void onActionClicked(Action action) {

        if (action == mRepeatAction) {
            repeatAction((PlaybackControlsRow.RepeatAction) action);
            return;
        }
        super.onActionClicked(action);
    }

    private void repeatAction(PlaybackControlsRow.RepeatAction action) {
        Toast.makeText(getContext(), action.toString(), Toast.LENGTH_SHORT).show();
        action.nextIndex();
        ArrayObjectAdapter primaryActionsAdapter = (ArrayObjectAdapter) getControlsRow().getPrimaryActionsAdapter();
        int repeatIndex = primaryActionsAdapter.indexOf(action);
        primaryActionsAdapter.notifyArrayItemRangeChanged(repeatIndex, 1);

    }

    Handler mHandler = new Handler();

    @Override
    protected void onPlayCompleted() {
        super.onPlayCompleted();
        mHandler.post(() -> {
            if (mRepeatAction.getIndex() != PlaybackControlsRow.RepeatAction.NONE) {
                play();
                PlaybackControlsGlue.this.getHost().hideControlsOverlay(true);
            }
        });
    }
}
