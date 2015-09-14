package com.frodo.android.app.core;

import android.content.Context;

import com.frodo.android.app.framework.controller.IModel;
import com.frodo.android.app.framework.controller.IView;
import com.frodo.android.app.framework.controller.UIViewController;

/**
 * Created by frodo on 2015/9/14.
 */
public interface AndroidUIViewController<V extends IView, M extends IModel> extends UIViewController<V, M> {
    Context getAndroidContext();
}
