package com.frodo.android.app.simple;

import java.io.File;
import java.util.List;

import com.frodo.android.app.framework.cache.AbstractCache;
import com.frodo.android.app.framework.cache.CacheSystem;
import com.frodo.android.app.framework.filesystem.FileSystem;
import com.frodo.android.app.simple.entities.amdb.Movie;
import com.google.gson.reflect.TypeToken;

/**
 * Created by frodo on 2015/7/23.
 */
public class MovieCache extends AbstractCache<String, List<Movie>> {

    public MovieCache(CacheSystem cacheSystem, Type type) {
        super(cacheSystem, type);
    }

    @Override
    public List<Movie> get(String key) {
        if (isCached(key)) {
            return getCacheSystem().findCacheFromDisk(createAbsoluteKey(key), new TypeToken<List<Movie>>() {
            }.getType());
        }
        return null;
    }

    @Override
    public void put(String key, List<Movie> value) {
        getCacheSystem().put(createAbsoluteKey(key), value, getType());
    }

    @Override
    public boolean isCached(String key) {
        if (getType().equals(Type.DISK)) {
            return getCacheSystem().existCacheInDisk(createAbsoluteKey(key));
        }
        return false;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public void evictAll() {
        getCacheSystem().evict("");
    }

    private String createAbsoluteKey(String relativeKey) {
        final FileSystem fs = getCacheSystem().getController().getFileSystem();
        final String absoluteKey = fs.getFilePath() + File.separator + relativeKey + ".cache.tmp";
        getCacheSystem().getController().getLogCollector().d("MovieCache" ,"Cache path >>>> " + absoluteKey);
        return absoluteKey;
    }
}
