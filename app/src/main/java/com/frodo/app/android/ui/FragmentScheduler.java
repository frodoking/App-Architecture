package com.frodo.app.android.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.frodo.app.android.ui.activity.FragmentContainerActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by frodo on 2015/9/14. fragment Scheduler
 */
public class FragmentScheduler {

    public static final String SCHEMA = "app://redirect";

    private final static Map<String, Class<? extends Fragment>> map = new HashMap<>();

    public static void register(String schema, Class<? extends Fragment> fragmentClass) {
        map.put(schema, fragmentClass);
    }

    public static Class<? extends Fragment> get(String schema) {
        return map.get(schema);
    }

    public static void doDirect(Context context, String schema) {
        doDirect(context, schema, false);
    }

    public static void doDirect(Context context, String schema, boolean isFinishCurrentPage) {
        doDirect(context, schema, null, isFinishCurrentPage);
    }

    public static void doDirect(Context context, String schema, Bundle extra, boolean isFinishCurrentPage) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SCHEMA));
        intent.putExtras(extra != null ? extra : new Bundle());
        intent.setData(Uri.parse(schema));
        context.startActivity(intent);
        if (isFinishCurrentPage && context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    public static void nextFragment(FragmentContainerActivity fragmentContainer,
                                    Class<? extends Fragment> fragmentClass) {
        nextFragment(fragmentContainer, fragmentClass, null);
    }

    public static void replaceFragment(FragmentContainerActivity fragmentContainer,
                                       Class<? extends Fragment> fragmentClass) {
        replaceFragment(fragmentContainer, fragmentClass, null);
    }

    public static void nextFragment(FragmentContainerActivity fragmentContainer,
                                    Class<? extends Fragment> fragmentClass,
                                    Bundle extra) {
        fragmentContainer.addFragment(fragmentClass, extra != null ? extra : new Bundle());
    }

    public static void replaceFragment(FragmentContainerActivity fragmentContainer,
                                       Class<? extends Fragment> fragmentClass,
                                       Bundle extra) {
        fragmentContainer.replaceFragment(fragmentClass, extra != null ? extra : new Bundle());
    }

    public static void removeFragment(FragmentContainerActivity fragmentContainer, String tag) {
        fragmentContainer.removeFragmentByTag(tag);
    }
}
