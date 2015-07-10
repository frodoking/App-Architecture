/*
 * Copyright 2014 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.frodo.android.app.simple;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.uwetrottmann.tmdb.Tmdb;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by frodo on 2015/4/2.
 */
@Deprecated
public class PhilmTmdb extends Tmdb {

    private static final String TAG = "PhilmTmdb";
    private static PhilmTmdb philmTmdb;
    private final File mCacheLocation;

    public PhilmTmdb() {
        this(null);
    }

    public PhilmTmdb(File cacheLocation) {
        mCacheLocation = cacheLocation;
    }

    public static PhilmTmdb getDefault() {
        if (philmTmdb == null) {
            synchronized(PhilmTmdb.class) {
                philmTmdb = new PhilmTmdb();
                philmTmdb.setApiKey(Constants.TMDB_API_KEY);
                philmTmdb.setIsDebug(Constants.DEBUG_NETWORK);
            }
        }

        return philmTmdb;
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
