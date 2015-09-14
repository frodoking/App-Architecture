package com.frodo.android.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.frodo.android.app.AppApplication;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.framework.log.Logger;

/**
 * Created by frodo on 2014/12/26.
 */
public abstract class AbstractBaseActivity extends FragmentActivity {
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = ((AppApplication) getApplication()).getMainController();
        Logger.tag(tag()).printLeftCycle("onCreate");
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.tag(tag()).printLeftCycle("onSaveInstanceState");
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
        Logger.tag(tag()).printLeftCycle("onStart");
        initBusiness();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.tag(tag()).printLeftCycle("onRestart");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.tag(tag()).printLeftCycle("onNewIntent");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.tag(tag()).printLeftCycle("onActivityResult");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.tag(tag()).printLeftCycle("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.tag(tag()).printLeftCycle("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.tag(tag()).printLeftCycle("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.tag(tag()).printLeftCycle("onDestroy");
    }

    public MainController getMainController() {
        return this.controller;
    }

    public String tag() {
        return getClass().getSimpleName();
    }
}

