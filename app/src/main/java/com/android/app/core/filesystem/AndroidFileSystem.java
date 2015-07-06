package com.android.app.core.filesystem;

import com.android.app.framework.controller.IController;
import com.android.app.framework.filesystem.DefaultFileSystem;

import android.content.Context;
import android.os.Environment;

/**
 * Android 文件管理系统
 * Created by frodo on 2015/6/20.
 */
public class AndroidFileSystem extends DefaultFileSystem {

    public AndroidFileSystem(IController controller) {
        super(controller, sdcardPath(), filePath((Context) controller.getContext()));
    }

    private static String sdcardPath() {
        return Environment.getExternalStorageDirectory().getPath() + "//";
    }

    private static String filePath(Context context) {
        return context.getFilesDir().getPath() + "//";
    }
}
