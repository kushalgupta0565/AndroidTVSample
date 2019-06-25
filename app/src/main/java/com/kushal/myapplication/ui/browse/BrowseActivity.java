package com.kushal.myapplication.ui.browse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.kushal.myapplication.R;

public class BrowseActivity extends FragmentActivity
{
    public static final String BROWSE_ITEM = "browse_item";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
    }
}
