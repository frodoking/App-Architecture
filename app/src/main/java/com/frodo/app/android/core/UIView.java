package com.frodo.app.android.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frodo.app.framework.controller.IView;

/**
 * Created by frodo on 2015/4/2.
 */
public abstract class UIView implements IView {
    private AndroidUIViewController presenter;
    private View rootView;
    private boolean isShown;
    public UIView(AndroidUIViewController presenter, LayoutInflater inflater, ViewGroup container, int layoutResId){
        this.presenter = presenter;
        rootView = inflater.inflate(layoutResId, container, false);
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public final AndroidUIViewController getPresenter() {
        return presenter;
    }

    public abstract void initView();
    public abstract void registerListener();

    @Override
    public final boolean isOnShown() {
        return isShown;
    }

    @Override
    public final void show(boolean show) {
        isShown = show;
    }
}
