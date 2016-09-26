package com.frodo.app.android.core.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.frodo.app.android.core.toolbox.JsonConverter;
import com.frodo.app.framework.cache.Cache;
import com.frodo.app.framework.cache.CacheSystem;
import com.frodo.app.framework.controller.AbstractChildSystem;
import com.frodo.app.framework.controller.IController;
import com.frodo.app.framework.filesystem.FileSystem;
import com.frodo.app.framework.orm.Database;

import java.io.File;
import java.lang.reflect.Type;

/**
 * 缓存实现 (sharepreference, db, disk)
 * Created by frodo on 2015/6/20.
 */
public class AndroidCacheSystem extends AbstractChildSystem implements CacheSystem {

    private Context context;
    private String cacheDir;

    private FileSystem fileSystem;
    private SharedPreferences sharedPreferences;
    private Database database;

    public AndroidCacheSystem(IController controller, String cacheDir) {
        super(controller);
        this.context = (Context) controller.getMicroContext().getContext();
        this.cacheDir = cacheDir;
        controller.getFileSystem().createDirectory(cacheDir);

        this.fileSystem = controller.getFileSystem();
        this.database = controller.getDatabase();
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
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
            File file = new File(getCacheDir(), key.toString());
            fileSystem.writeToFile(file, JsonConverter.toJson(value));
            return true;
        } else if (type.equals(Cache.Type.INTERNAL)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key.toString(), value.toString());
            editor.apply();
            return true;
        }
        return false;
    }

    @Override
    public void evict(String key, Cache.Type type) {
        if (type.equals(Cache.Type.DISK)) {
            fileSystem.clearDirectory(new File(key));
        } else if (type.equals(Cache.Type.INTERNAL)) {
            sharedPreferences.edit().remove(key).apply();
        }
    }

    @Override
    public void evictAll(String key) {
        fileSystem.clearDirectory(new File(key));
        sharedPreferences.edit().remove(key).apply();
    }

    @Override
    public void evictAll() {
        fileSystem.clearDirectory(new File(getCacheDir()));
        sharedPreferences.edit().clear().apply();
    }

    @Override
    public boolean existCacheInInternal(String key) {
        return sharedPreferences.contains(key);
    }

    @Override
    public <T> T findCacheFromInternal(String key, Type classType) {
        if (existCacheInInternal(key)) {
            String jsonString = sharedPreferences.getString(key, "");
            return JsonConverter.convert(jsonString, classType);
        } else {
            return null;
        }
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
        return fileSystem.exists(new File(getCacheDir(), fileName));
    }

    @Override
    public <T> T findCacheFromDisk(String fileName, Type classType) {
        File file = new File(getCacheDir(), fileName);
        String content = fileSystem.readFileContent(file);
        return JsonConverter.convert(content, classType);
    }
}
