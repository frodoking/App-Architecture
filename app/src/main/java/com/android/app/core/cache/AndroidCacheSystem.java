package com.android.app.core.cache;

import com.android.app.framework.cache.Cache;
import com.android.app.framework.controller.AbstractChildSystem;
import com.android.app.framework.controller.IController;
import com.android.app.framework.filesystem.FileSystem;
import com.android.app.framework.orm.Database;

import android.content.Context;

/**
 * ª∫¥Ê µœ÷ (sharepreference, db, fs)
 * Created by frodo on 2015/6/20.
 */
public class AndroidCacheSystem extends AbstractChildSystem implements Cache {

    private Context context;
    private String cacheDir;

    private AndroidCacheSharedPreferences sharedPreferences;
    private FileSystem fileSystem;
    private Database database;

    public AndroidCacheSystem(IController controller, String cacheDir) {
        super(controller);
        this.context = (Context) controller.getContext();
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
    public boolean existCacheInDatabase(String sql) {
        return false;
    }

    @Override
    public <T> T findCacheFromDatabase(String sql) {
        return null;
    }

    @Override
    public boolean existCacheInApplication(String key) {
        return false;
    }

    @Override
    public <T> T findCacheFromApplication(String key) {
        return null;
    }

    @Override
    public boolean existCacheInLocal(String fileName) {
        return false;
    }

    @Override
    public <F> F findCacheFromLocal(String fileName) {
        return null;
    }
}
