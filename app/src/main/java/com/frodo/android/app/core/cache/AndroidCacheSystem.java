package com.frodo.android.app.core.cache;

import android.content.Context;

import com.frodo.android.app.framework.cache.Cache;
import com.frodo.android.app.framework.cache.CacheSystem;
import com.frodo.android.app.framework.controller.AbstractChildSystem;
import com.frodo.android.app.framework.controller.IController;
import com.frodo.android.app.framework.filesystem.FileSystem;
import com.frodo.android.app.framework.orm.Database;
import com.google.gson.Gson;

import java.io.File;
import java.lang.reflect.Type;

/**
 * 缓存实现 (sharepreference, db, disk)
 * Created by frodo on 2015/6/20.
 */
public class AndroidCacheSystem extends AbstractChildSystem implements CacheSystem {

    private Context context;
    private String cacheDir;

    private AndroidCacheSharedPreferences sharedPreferences;
    private FileSystem fileSystem;
    private Database database;

    public AndroidCacheSystem(IController controller, String cacheDir) {
        super(controller);
        this.context = (Context) controller.getAppContext();
        this.cacheDir = cacheDir;

        this.sharedPreferences = new AndroidCacheSharedPreferences(context);
        this.fileSystem = controller.getFileSystem();
        this.database = controller.getDatabase();
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
            fileSystem.writeToFile(file, new Gson().toJson(value));
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
    public <T> T findCacheFromInternal(String key,  Type classType) {
        return null;
    }

    @Override
    public boolean existCacheInDatabase(String sql) {
        return false;
    }

    @Override
    public <T> T findCacheFromDatabase(String sql,  Type classType) {
        return null;
    }

    @Override
    public boolean existCacheInDisk(String fileName) {
        return fileSystem.exists(new File(fileName));
    }

    @Override
    public <T> T  findCacheFromDisk(String fileName,  Type classType) {
        final String cacheString = fileSystem.readFileContent(new File(fileName));
        return new Gson().fromJson(cacheString,classType);
    }

}
