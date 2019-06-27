package com.kushal.myapplication.callback;

import android.support.annotation.NonNull;
import android.support.v4.media.MediaDescriptionCompat;

public interface PlaylistCallback {

    MediaDescriptionCompat getCurrentItem();

    @NonNull
    MediaDescriptionCompat skipToNextItem();

    @NonNull
    MediaDescriptionCompat skipToPreviousItem();

}
