package com.frodo.app.framework.cache;

/**
 * Created by frodo on 2015/7/23.
 */
public abstract class AbstractCache<K, V> implements com.frodo.app.framework.cache.Cache<K, V> {
    private com.frodo.app.framework.cache.CacheSystem cacheSystem;
    private Type type;

    public AbstractCache(com.frodo.app.framework.cache.CacheSystem cacheSystem, Type type) {
        this.cacheSystem = cacheSystem;
        this.type = type;
    }

    @Override
    public final CacheSystem getCacheSystem() {
        return cacheSystem;
    }

    @Override
    public final Type getType() {
        return type;
    }
}
