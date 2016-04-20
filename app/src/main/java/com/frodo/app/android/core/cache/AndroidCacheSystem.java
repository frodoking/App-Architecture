package com.frodo.app.android.core.cache;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frodo.app.framework.cache.Cache;
import com.frodo.app.framework.cache.CacheSystem;
import com.frodo.app.framework.controller.AbstractChildSystem;
import com.frodo.app.framework.controller.IController;
import com.frodo.app.framework.filesystem.FileSystem;
import com.frodo.app.framework.orm.Database;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 缓存实现 (sharepreference, db, disk)
 * Created by frodo on 2015/6/20.
 */
public class AndroidCacheSystem extends AbstractChildSystem implements CacheSystem {

    private Context context;
    private String cacheDir;

    private FileSystem fileSystem;

    public AndroidCacheSystem(IController controller, String cacheDir) {
        super(controller);
        this.context = (Context) controller.getMicroContext();
        this.cacheDir = cacheDir;

        this.fileSystem = controller.getFileSystem();
    }

    @Override
    public String getCacheDir() {
        return cacheDir;
    }

    @Override
    public void setCacheDir(String dir) {
        this.cacheDir = dir;
    }

    @Override
    public boolean existCacheByType(String key, Cache.Type type) {
        return false;
    }

    @Override
    public <T> T findCacheByType(String key, Type classType, Cache.Type type) {
        return null;
    }

    @Override
    public <K, V> boolean put(K key, V value, Cache.Type type) {
        if (type.equals(Cache.Type.DISK)) {
            File file = fileSystem.createFile(key.toString());
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = null;
            try {
                jsonString = objectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            fileSystem.writeToFile(file, jsonString);
        }
        return false;
    }

    @Override
    public void evict(String key) {
        fileSystem.clearDirectory(new File(key));
    }

    @Override
    public void evictAll() {

    }

    @Override
    public boolean existCacheInInternal(String key) {
        return false;
    }

    @Override
    public <T> T findCacheFromInternal(String key, Type classType) {
        return null;
    }

    @Override
    public boolean existCacheInDatabase(String sql) {
        return false;
    }

    @Override
    public <T> T findCacheFromDatabase(String sql, Type classType) {
        return null;
    }

    @Override
    public boolean existCacheInDisk(String fileName) {
        return fileSystem.exists(new File(fileName));
    }

    @Override
    public <T> T findCacheFromDisk(String fileName, Object clazz) {
        try {
            if (clazz instanceof TypeReference) {
                return (T) new ObjectMapper().readValue(new File(fileName), (TypeReference) clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
