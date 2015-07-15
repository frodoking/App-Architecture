package com.frodo.android.app.ui.activity;

import com.frodo.android.app.AppApplication;
import com.frodo.android.app.framework.controller.MainController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by frodo on 2014/12/26.
 */
public abstract class AbstractBaseActivity extends FragmentActivity {
    private static final String TAG = "tag_activity_lifecycle";
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = ((AppApplication) getApplication()).getMainController();
        printLeftCycle("onCreate");
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        printLeftCycle("onSaveInstanceState");
    }

    public void init() {
        setContentView(getLayoutId());
        initView();
        registerListener();
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void registerListener();

    public abstract void initBusiness();

    @Override
    protected void onStart() {
        super.onStart();
        printLeftCycle("onStart");
        initBusiness();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        printLeftCycle("onRestart");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        printLeftCycle("onNewIntent");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        printLeftCycle("onActivityResult");
    }

    @Override
    protected void onResume() {
        super.onResume();
        printLeftCycle("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        printLeftCycle("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        printLeftCycle("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printLeftCycle("onDestroy");
    }

    /**
     * Ìø×ªµ½Fragment
     * @param clazz
     * @param extra
     * @param isFinished
     */
    public final void redirectToFragment(Class<? extends Fragment> clazz, Bundle extra, boolean isFinished) {
        Intent intent = new Intent(this, FragmentContainerActivity2.class);
        if (extra == null) {
            extra = new Bundle();
            if (clazz != null) {
                extra.putString("fragment_class_name", clazz.getCanonicalName());
            }
        }
        intent.putExtra("extra", extra);
        startActivity(intent);
        if (isFinished) {
            finish();
        }
    }

    public MainController getMainController() {
        return this.controller;
    }

    private void printLeftCycle(String methodName) {
        getMainController().getLogCollector()
                .logInfo(TAG, ">> " + getClass().getSimpleName() + "*********>> " + methodName + " <<********* <<");
    }

    public String tag() {
        return getClass().getSimpleName();
    }

    public final void printLog(String log) {
        getMainController().getLogCollector().logInfo("tag_" + tag(), " >> -----------> " + log + " <------------ <<");
    }
}

