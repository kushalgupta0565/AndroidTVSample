package com.kushal.myapplication.utils;

import android.util.Log;

public class LogMgr {

    public static final String TAG = "TV_APP";

    public static void d(String tag, String msg) {
        Log.d(TAG + " --> " + tag, msg);
    }
}
