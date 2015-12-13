package com.frodo.android.app.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frodo.android.app.core.AndroidUIViewController;
import com.frodo.android.app.core.UIView;
import com.frodo.android.app.framework.controller.AbstractModel.SimpleModel;
import com.frodo.android.app.framework.controller.IModel;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.framework.log.Logger;
import com.frodo.android.app.ui.activity.AbstractBaseActivity;

/**
 * Created by frodo on 2015/1/12. base Fragment, contain UIView and Model
 */
public abstract class AbstractBaseFragment<V extends UIView, M extends IModel> extends Fragment implements AndroidUIViewController<V,M> {
    private MainController controller;
    private V uiView;
    private M model;

    @Override
    public final Context getAndroidContext() {
        return getActivity();
    }

    public abstract V createUIView(Context context, LayoutInflater inflater, ViewGroup container);

    protected M createModel() {
        return (M) new SimpleModel(controller);
    }

    @Override
    public final V getUIView() {
        return uiView;
    }

    @Override
    public final M getModel() {
        return model;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controller = ((AbstractBaseActivity) getActivity()).getMainController();
        Logger.tag(tag()).printLifeCycle("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.tag(tag()).printLifeCycle("onCreate");
        model = createModel();
    }

    /**
     * 初始化界面相关
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.tag(tag()).printLifeCycle("onCreateView");
        this.uiView = createUIView(getActivity(), inflater, container);
        return uiView.getRootView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.tag(tag()).printLifeCycle("onViewCreated");
        getUIView().initView();
        getUIView().registerListener();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.tag(tag()).printLifeCycle("onActivityCreated");
        getModel().initBusiness();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.tag(tag()).printLifeCycle("onActivityResult");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Logger.tag(tag()).printLifeCycle("onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.tag(tag()).printLifeCycle("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.tag(tag()).printLifeCycle("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.tag(tag()).printLifeCycle("onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.tag(tag()).printLifeCycle("onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.tag(tag()).printLifeCycle("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.tag(tag()).printLifeCycle("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.tag(tag()).printLifeCycle("onDestroy");
        getMainController().getLogCollector().watchLeak(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.tag(tag()).printLifeCycle("onDetach");
    }

    public boolean onBackPressed() {
        return false;
    }

    public MainController getMainController() {
        return this.controller;
    }

    public String tag() {
        return getClass().getSimpleName();
    }
}

