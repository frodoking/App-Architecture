package com.frodo.android.app.core.toolbox;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;

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

    private static Resources getResources() {
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

    public static String getRaw(int rawResourceId) {
        String res = "";
        InputStream in = null;
        try {
            //得到资源中的Raw数据流
            in = getResources().openRawResource(rawResourceId);

            //得到数据的大小
            int length = in.available();
            byte[] buffer = new byte[length];

            //读取数据
            in.read(buffer);
            //依test.txt的编码类型选择合适的编码，如果不调整会乱码
            res = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    public static String getAsset(String fileName) {
        String res = "";
        InputStream in = null;
        try {
            //得到资源中的asset数据流
            in = getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];

            in.read(buffer);
            res = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }
}
