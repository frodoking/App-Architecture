package com.frodo.android.app.core.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences
 * Created by frodo on 2015/6/24.
 */
public class AndroidCacheSharedPreferences {
    private SharedPreferences sharedPreferences;

    public AndroidCacheSharedPreferences(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
