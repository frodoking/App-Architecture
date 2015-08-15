package com.frodo.android.app.ui.fragment;

import com.frodo.android.app.core.toolbox.AndroidLeakcanary;
import com.frodo.android.app.framework.controller.IView;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.ui.activity.AbstractBaseActivity;
import com.frodo.android.app.ui.activity.FragmentContainerActivity2;
import com.squareup.leakcanary.RefWatcher;

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

    /**
     * 跳转到下一个Fragment
     *
     * @param clazz
     * @param extra
     * @param isFinished
     */
    public final void redirectNextFragment(Class<?> clazz, Bundle extra, boolean isFinished) {
        Intent intent = new Intent(getActivity(), FragmentContainerActivity2.class);
        if (extra == null) {
            extra = new Bundle();
            if (clazz != null) {
                extra.putString("fragment_class_name", clazz.getCanonicalName());
            }
        }
        intent.putExtra("extra", extra);
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

