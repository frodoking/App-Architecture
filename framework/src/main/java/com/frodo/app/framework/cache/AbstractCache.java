package com.frodo.app.framework.cache;

import java.lang.reflect.Method;

/**
 * Abstract Cache
 *
 * @param <K>
 * @param <V> Created by frodo on 2015/7/23.
 */
public abstract class AbstractCache<K, V> implements Cache<K, V> {
    private CacheSystem cacheSystem;
    private Type type;

    public AbstractCache(CacheSystem cacheSystem, Type type) {
        this.cacheSystem = cacheSystem;
        this.type = type;
    }

    @Override
    public final CacheSystem getCacheSystem() {
        return cacheSystem;
    }

    public final Type getType() {
        return type;
    }

    @Override
    public boolean isCached(K key) {
        return getCacheSystem().existCacheByType(key.toString(), getType());
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public V get(K key) {
        Method[] methods = getClass().getDeclaredMethods();
        for(Method method: methods){
            if (method.getName().equals("get")){
                Class clazz = method.getReturnType();
                return getCacheSystem().findCacheByType(key.toString(),clazz, getType());
            }
        }
        return  null;
    }

    @Override
    public void put(K key, V value) {
        getCacheSystem().put(key, value ,getType());
    }
}
