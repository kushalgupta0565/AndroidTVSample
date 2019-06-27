package com.kushal.myapplication.utils.video.playlist;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaDescriptionCompat;


import com.kushal.myapplication.callback.PlaylistCallback;
import com.kushal.myapplication.model.browse.BrowseData;

import java.util.List;

public class VideoListAdapter<T> implements PlaylistCallback {

    private final List<T> items;
    private int activeIndex;

    public VideoListAdapter(List<T> items, int activeIndex) {
        this.items = items;
        this.activeIndex = activeIndex;
    }

    private MediaDescriptionCompat bulidMediaDescriptionCompat(T item) {
        BrowseData data = (BrowseData) item;
        MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
        return builder.setTitle(data.getContentName())
                .setDescription(data.getContentDesc())
                .setMediaUri(Uri.parse(data.getContentVideoUrl()))
                .build();
    }

    @Override
    public MediaDescriptionCompat getCurrentItem() {
        return bulidMediaDescriptionCompat(items.get(activeIndex));
    }

    @NonNull
    @Override
    public MediaDescriptionCompat skipToNextItem() {
        if (activeIndex < items.size() - 1) {
            this.activeIndex++;
        } else if (activeIndex == items.size()-1) {
            activeIndex =0;
        }
        return getCurrentItem();
    }

    @NonNull
    @Override
    public MediaDescriptionCompat skipToPreviousItem() {
        if (activeIndex > 0) {
            this.activeIndex--;
        } else {
            activeIndex = items.size()-1;
        }
        return getCurrentItem();
    }
}