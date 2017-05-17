package com.frodo.app.android.simple;

import com.frodo.app.android.core.toolbox.HashUtils;
import com.frodo.app.android.simple.entity.Movie;
import com.frodo.app.framework.cache.AbstractCache;
import com.frodo.app.framework.cache.CacheSystem;
import com.frodo.app.framework.filesystem.FileSystem;
import com.frodo.app.framework.log.Logger;
import com.google.common.reflect.TypeToken;

import java.io.File;
import java.util.List;

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
	}

	private String createAbsoluteKey(String relativeKey) {
		final String absoluteKey = getCacheKey(relativeKey) + ".cache.tmp";
		Logger.fLog().tag("MovieCache").d("Cache path >>>> " + absoluteKey);
		return absoluteKey;
	}

	private String getCacheKey(String cacheKey) {
		return HashUtils.computeWeakHash(cacheKey.trim()) + String.format("%04x", cacheKey.length());
	}
}
