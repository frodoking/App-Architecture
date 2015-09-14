package com.frodo.android.app.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frodo.android.app.core.AndroidUIViewController;
import com.frodo.android.app.core.UIView;
import com.frodo.android.app.framework.controller.IModel;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.ui.activity.AbstractBaseActivity;
import com.frodo.android.app.ui.activity.FragmentContainerActivity2;

/**
 * Created by frodo on 2015/1/12. base Fragment, contain UIView and Model
 */
public abstract class AbstractBaseFragment<V extends UIView, M extends IModel> extends Fragment implements AndroidUIViewController<V,M> {
    public static final String TAG = "tag_fragment_lifecycle";
    private MainController controller;
    private V uiView;
    private M model;

    @Override
    public final Context getAndroidContext() {
        return getActivity();
    }

    public abstract V createUIView(Context context, LayoutInflater inflater, ViewGroup container);

    public abstract M createModel();

    @Override
    public final V getUIView() {
        return uiView;
    }

    @Override
    public final M getModel() {
        return model;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        controller = ((AbstractBaseActivity) activity).getMainController();
        printLeftCycle("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printLeftCycle("onCreate");
        createModel();
    }

    /**
     * 初始化界面相关
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        printLeftCycle("onCreateView");
        this.uiView = createUIView(getActivity(), inflater, container);
        return uiView.getRootView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        printLeftCycle("onViewCreated");
        getUIView().initView();
        getUIView().registerListener();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        printLeftCycle("onActivityCreated");
        getModel().initBusiness();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        printLeftCycle("onActivityResult");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        printLeftCycle("onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        printLeftCycle("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        printLeftCycle("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        printLeftCycle("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        printLeftCycle("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        printLeftCycle("onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        printLeftCycle("onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        printLeftCycle("onDestroy");
        getMainController().getLogCollector().watchLeak(this);
    }



    public boolean onBackPressed() {
        return false;
    }

    public MainController getMainController() {
        return this.controller;
    }

    private void printLeftCycle(String methodName) {
        getMainController().getLogCollector()
                .d(TAG,
                        " >> " + getClass().getSimpleName() + " ====== " + methodName + " ====== << (" + hashCode()
                                + ") + activity (" + getActivity().hashCode() + ")");
    }

    public String tag() {
        return getClass().getSimpleName();
    }

    public final void printLog(String log) {
        getMainController().getLogCollector().i("tag_" + tag(), " >> -----------> " + log + " <------------ <<");
    }
}

