package com.android.app.core.util;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

/**
 * 统一资源管理
 *
 * @author frodoking
 * @date 2014年11月11日 下午5:11:51
 */
public class ResourceManager {

    private static Context mContext;

    public static void newInstance(Context context) {
        ResourceManager.mContext = context;
    }

    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    public static float getDimension(int resId) {
        return getResources().getDimension(resId);
    }

    public static int getDimensionPixelSize(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static float getScreenWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static float getScreenHeight() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float getScreenDensity() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.density;
    }

    public static Resources getResources() {
        return mContext.getResources();
    }

    public static PackageInfo getPackageInfo() {
        PackageManager pm = mContext.getPackageManager();
        try {
            return pm.getPackageInfo(mContext.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMetaValue(String metaKey) {
        Bundle metaData = null;
        String metaValue = null;
        if (metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai =
                    mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                metaValue = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {
        }
        return metaValue;
    }

    public static void globalToast(int strId) {
        globalToast(getString(strId));
    }

    public static void globalToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
