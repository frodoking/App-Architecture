package com.android.app.core;

import android.content.Context;

import com.android.app.framework.controller.IView;

/**
 * Created by frodo on 2015/4/2.
 */
public interface UIView extends IView{
    Context getActivity();
}
