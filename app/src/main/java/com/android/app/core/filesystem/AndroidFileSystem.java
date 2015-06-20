package com.android.app.core.filesystem;

import com.android.app.framework.controller.AbstractChildSystem;
import com.android.app.framework.controller.IController;
import com.android.app.framework.filesystem.FileSystem;

/**
 * Created by frodo on 2015/6/20.
 */
public class AndroidFileSystem  extends AbstractChildSystem implements FileSystem {

    public AndroidFileSystem(IController controller) {
        super(controller);
    }
}
