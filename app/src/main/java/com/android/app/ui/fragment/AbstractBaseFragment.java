package com.android.app.ui.fragment;

import com.android.app.framework.controller.IView;
import com.android.app.framework.controller.MainController;
import com.android.app.ui.activity.AbstractBaseActivity;
import com.android.app.ui.activity.FragmentContainerActivity2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by frodo on 2015/1/12.
 */
public abstract class AbstractBaseFragment extends Fragment implements IView {
    public static final String TAG = "tag_fragment_lifecycle";
    private MainController controller;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        controller = ((AbstractBaseActivity) activity).getMainController();
        printLeftCycle("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreatePresenter();
        printLeftCycle("onCreate");
    }

    public abstract void onCreatePresenter();

    /**
     * 初始化界面相关
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        printLeftCycle("onCreateView");
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        printLeftCycle("onViewCreated");
        init();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        printLeftCycle("onActivityCreated");
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

    public void init() {
        initView();
        registerListener();
        initBusiness();
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void registerListener();

    public abstract void initBusiness();

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
    }

    public final void redirect(Class<?> clazz, boolean isFinished) {
        Intent intent = new Intent(getActivity(), FragmentContainerActivity2.class);
        intent.putExtra("fragment_class_name", clazz.getCanonicalName());
        startActivity(intent);
        if (isFinished) {
            getActivity().finish();
        }
    }

    public boolean onBackPressed() {
        return false;
    }

    public MainController getMainController() {
        return this.controller;
    }

    private void printLeftCycle(String methodName) {
        getMainController().getLogCollector()
                .logInfo(TAG, " >> " + getClass().getSimpleName() + " ====== " + methodName + " ====== << (" + hashCode()
                        + ") + activity (" + getActivity().hashCode() + ")");
    }

    public String tag() {
        return getClass().getSimpleName();
    }

    public final void printLog(String log) {
        getMainController().getLogCollector().logInfo("tag_" + tag(), " >> -----------> " + log + " <------------ <<");
    }
}

