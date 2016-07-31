package com.frodo.app.android.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frodo.app.android.core.AndroidUIViewController;
import com.frodo.app.android.core.UIView;
import com.frodo.app.android.ui.activity.AbstractBaseActivity;
import com.frodo.app.framework.controller.AbstractModel.SimpleModel;
import com.frodo.app.framework.controller.IModel;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.controller.ModelFactory;
import com.frodo.app.framework.log.Logger;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by frodo on 2015/1/12. base Fragment, contain UIView and Model
 */
public abstract class AbstractBaseFragment<V extends UIView, M extends IModel> extends Fragment implements AndroidUIViewController<V, M> {
    private static final String LIFECYCLE = "_LifeCycle_F";
    private V uiView;
    private M model;

    @Override
    public final Context getAndroidContext() {
        return getActivity();
    }

    public abstract V createUIView(Context context, LayoutInflater inflater, ViewGroup container);

    @SuppressWarnings("unchecked")
    protected M createModel() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ModelFactory modelFactory = getMainController().getModelFactory();
        if (genericSuperclass instanceof ParameterizedType) {
            for (Type type : ((ParameterizedType) genericSuperclass).getActualTypeArguments()) {
                if (type instanceof Class) {
                    Class<M> clazz = (Class<M>) type;
                    if (IModel.class.isAssignableFrom(clazz) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                        String key = ((Class) type).getSimpleName();
                        Logger.fLog().tag("Model").i("isAssignableFrom " + key);
                        return modelFactory.getOrCreateIfAbsent(key, (Class<M>) type, getMainController());
                    }
                }
            }
        }

        return (M) modelFactory.getOrCreateIfAbsent(IModel.MODEL_DEFAULT, SimpleModel.class, getMainController());
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
        Logger.fLog().tag(tag() + LIFECYCLE).i("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.fLog().tag(tag() + LIFECYCLE).i("onCreateView");
        this.uiView = createUIView(getActivity(), inflater, container);
        return uiView.getRootView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onViewCreated");
        getUIView().initView();
        getUIView().registerListener();
        getUIView().show(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onActivityCreated");
        model = createModel();
        getModel().initBusiness();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onActivityResult");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getUIView().show(false);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onDestroy");
        if (getMainController().getConfig().isDebug()) {
            getMainController().getLogCollector().watchLeak(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onDetach");
    }

    public MainController getMainController() {
        return ((AbstractBaseActivity) getActivity()).getMainController();
    }

    public String tag() {
        return getClass().getSimpleName();
    }
}

