package com.frodo.app.android.core.toolbox;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.frodo.app.framework.net.HttpModule;

/**
 * Created by frodo on 2016/3/6.
 */
public final class ManifestParser {
    private static final String HTTP_MODULE_VALUE = "HttpModule";

    private final Context context;

    public ManifestParser(Context context) {
        this.context = context;
    }

    public HttpModule parse() {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (HTTP_MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                        return parseModule(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse GlideModules", e);
        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static HttpModule parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find GlideModule implementation", e);
        }

        Object module;
        try {
            module = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to instantiate GlideModule implementation for " + clazz,
                    e);
        }

        if (!(module instanceof HttpModule)) {
            throw new RuntimeException("Expected instanceof HttpModule, but found: " + module);
        }
        return (HttpModule) module;
    }
}
