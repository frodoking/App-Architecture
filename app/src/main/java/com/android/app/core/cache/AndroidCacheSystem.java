package com.android.app.core.cache;

import com.android.app.framework.cache.Cache;
import com.android.app.framework.controller.AbstractChildSystem;
import com.android.app.framework.controller.IController;

import android.content.Context;

/**
 * ª∫¥Ê µœ÷
 * Created by frodo on 2015/6/20.
 */
public class AndroidCacheSystem extends AbstractChildSystem implements Cache {

    private Context context;
    private String cacheDir;

    public AndroidCacheSystem(IController controller, String cacheDir) {
        super(controller);
        this.context = (Context) controller.getContext();
        this.cacheDir = cacheDir;
    }
}
