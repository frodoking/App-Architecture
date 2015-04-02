package com.android.app.simple;

import com.jakewharton.trakt.Trakt;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by frodo on 2015/4/2.
 */
public class PhilmTrakt extends Trakt {
    private static final String TAG = "PhilmTrakt";

    private static PhilmTrakt philmTrakt;

    public static PhilmTrakt getDefault() {
        if (philmTrakt == null) {
            synchronized (PhilmTrakt.class) {
                philmTrakt = new PhilmTrakt();
            }
        }

        return philmTrakt;
    }

    private final File mCacheLocation;

    public PhilmTrakt() {
        this(null);
    }

    public PhilmTrakt(File cacheLocation) {
        mCacheLocation = cacheLocation;
    }

    @Override
    protected RestAdapter.Builder newRestAdapterBuilder() {
        RestAdapter.Builder b = super.newRestAdapterBuilder();

        if (mCacheLocation != null) {
            OkHttpClient client = new OkHttpClient();

            File cacheDir = new File(mCacheLocation, UUID.randomUUID().toString());
            Cache cache = new Cache(cacheDir, 1024);
            client.setCache(cache);

            client.setConnectTimeout(Constants.CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            client.setReadTimeout(Constants.READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

            b.setClient(new OkClient(client));
        }

        return b;
    }

}
