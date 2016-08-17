package com.frodo.app.android.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.frodo.app.android.ui.activity.FragmentContainerActivity;
import com.frodo.app.android.ui.activity.RedirectActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frodo on 2015/9/14. fragment Scheduler
 */
public class FragmentScheduler {

    public static String SCHEME = "frodo://redirect";

    private final static Map<String, Class<? extends Fragment>> map = new HashMap<>();

    public static void register(String schema, Class<? extends Fragment> fragmentClass) {
        map.put(schema, fragmentClass);
    }

    public static void findDirectScheme(Context context) {
        ComponentName cn = new ComponentName(context, RedirectActivity.class);
        try {
            ActivityInfo info = context.getPackageManager().getActivityInfo(cn, PackageManager.GET_META_DATA);
            FragmentScheduler.SCHEME = info.metaData.getString("REDIRECT_SCHEME_KEY", "frodo") + "://redirect";
        } catch (PackageManager.NameNotFoundException e) {
            FragmentScheduler.SCHEME = "frodo://redirect";
        }
    }

    private static Class<? extends Fragment> get(String schema) {
        return map.get(schema);
    }

    public static void doDirect(Context context, String schema) {
        doDirect(context, schema, false);
    }

    public static void doDirect(Context context, String schema, boolean isFinishCurrentPage) {
        doDirect(context, schema, null, isFinishCurrentPage);
    }

    public static void doDirect(Context context, String schema, Bundle extra, boolean isFinishCurrentPage) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SCHEME));
        intent.putExtras(extra != null ? extra : new Bundle());
        intent.setData(Uri.parse(schema));
        context.startActivity(intent);
        if (isFinishCurrentPage && context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    public static void doNext(Context context, String schema) {
        doNext(context, schema, null);
    }

    public static void doNext(Context context, String schema, Bundle extra) {
        nextFragment(context, get(schema), extra);
    }

    public static void doNextWithUniqueTag(Context context, String schema) {
        doNextWithUniqueTag(context, schema, null);
    }

    public static void doNextWithUniqueTag(Context context, String schema, Bundle extra) {
        nextFragmentWithUniqueTag(context, get(schema), extra);
    }

    public static void doReplace(Context context, String schema) {
        doReplace(context, schema, null);
    }

    public static void doReplace(Context context, String schema, Bundle extra) {
        replaceFragment(context, get(schema), extra);
    }

    public static void doReplaceWithUniqueTag(Context context, String schema) {
        doReplaceWithUniqueTag(context, schema, null);
    }

    public static void doReplaceWithUniqueTag(Context context, String schema, Bundle extra) {
        replaceFragmentWithUniqueTag(context, get(schema), extra);
    }


    public static void nextFragment(Context context, Class<? extends Fragment> fragmentClass) {
        nextFragment(context, fragmentClass, null);
    }

    public static void replaceFragment(Context context, Class<? extends Fragment> fragmentClass) {
        replaceFragment(context, fragmentClass, null);
    }

    public static void nextFragment(Context context, Class<? extends Fragment> fragmentClass, Bundle extra) {
        if (!(context instanceof FragmentContainerActivity)) throw new AssertionError();
        ((FragmentContainerActivity) context).addFragment(fragmentClass, extra != null ? extra : new Bundle());
    }

    public static void replaceFragment(Context context, Class<? extends Fragment> fragmentClass, Bundle extra) {
        if (!(context instanceof FragmentContainerActivity)) throw new AssertionError();
        ((FragmentContainerActivity) context).replaceFragment(fragmentClass, extra != null ? extra : new Bundle());
    }

    public static void removeFragment(Context context, String tag) {
        if (!(context instanceof FragmentContainerActivity)) throw new AssertionError();
        ((FragmentContainerActivity) context).removeFragmentByTag(tag);
    }

    public static void nextFragmentWithUniqueTag(Context context, Class<? extends Fragment> fragmentClass) {
        nextFragmentWithUniqueTag(context, fragmentClass, null);
    }

    public static void replaceFragmentWithUniqueTag(Context context, Class<? extends Fragment> fragmentClass) {
        replaceFragment(context, fragmentClass, null);
    }

    public static void nextFragmentWithUniqueTag(Context context, Class<? extends Fragment> fragmentClass, Bundle extra) {
        if (!(context instanceof FragmentContainerActivity)) throw new AssertionError();
        ((FragmentContainerActivity) context).addFragment(fragmentClass, fragmentClass.getCanonicalName() + '@' + System.nanoTime(), extra != null ? extra : new Bundle());
    }

    public static void replaceFragmentWithUniqueTag(Context context, Class<? extends Fragment> fragmentClass, Bundle extra) {
        if (!(context instanceof FragmentContainerActivity)) throw new AssertionError();
        ((FragmentContainerActivity) context).replaceFragment(fragmentClass, fragmentClass.getCanonicalName() + '@' + System.nanoTime(), extra != null ? extra : new Bundle());
    }

    public static void removeFragmentsStartsWithUniqueTag(Context context, String tag) {
        if (!(context instanceof FragmentContainerActivity)) throw new AssertionError();
        List<Fragment> fragments = ((FragmentContainerActivity) context).getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.getTag().toLowerCase().startsWith(tag.toLowerCase())) {
                ((FragmentContainerActivity) context).removeFragmentByTag(fragment.getTag());
            }
        }
    }
}
