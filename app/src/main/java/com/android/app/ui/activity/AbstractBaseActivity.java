package com.android.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.app.AppApplication;
import com.android.app.BuildConfig;
import com.android.app.framework.controller.IController;


/**
 * Created by frodo on 2014/12/26.
 */
public abstract class AbstractBaseActivity extends FragmentActivity {
    private static final String TAG = "tag_activity_lifecycle";
    private IController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printLeftCycle("onCreate");
        mController = ((AppApplication)getApplication()).getController();
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

    public IController getController(){
        return this.mController;
    }

    private void printLeftCycle(String methodName) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, ">> " + getClass().getSimpleName() + "*********>> " + methodName + " <<********* <<");
    }

    public String tag() {
        return getClass().getSimpleName();
    }

    public final void printLog(String log) {
        if (BuildConfig.DEBUG)
            Log.d("tag_"+tag(), " >> -----------> " + log + " <------------ <<");
    }
}

