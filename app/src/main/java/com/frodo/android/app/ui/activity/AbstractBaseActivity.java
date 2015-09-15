package com.frodo.android.app.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.frodo.android.app.AppApplication;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.framework.log.Logger;

/**
 * Created by frodo on 2014/12/26. print whole life cycle
 */
public abstract class AbstractBaseActivity extends FragmentActivity {
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = ((AppApplication) getApplication()).getMainController();
        Logger.tag(tag()).printLifeCycle("onCreate");
        init();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Logger.tag(tag()).printLifeCycle("onAttachFragment");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Logger.tag(tag()).printLifeCycle("onContentChanged");
    }

    /**
     * from onStop()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.tag(tag()).printLifeCycle("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.tag(tag()).printLifeCycle("onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.tag(tag()).printLifeCycle("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.tag(tag()).printLifeCycle("onSaveInstanceState");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Logger.tag(tag()).printLifeCycle("onPostCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.tag(tag()).printLifeCycle("onResume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Logger.tag(tag()).printLifeCycle("onPostResume");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Logger.tag(tag()).printLifeCycle("onAttachedToWindow");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Logger.tag(tag()).printLifeCycle("onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Logger.tag(tag()).printLifeCycle("onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.tag(tag()).printLifeCycle("onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Logger.tag(tag()).printLifeCycle("onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.tag(tag()).printLifeCycle("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.tag(tag()).printLifeCycle("onDestroy");
    }

    public void init() {
        setContentView(getLayoutId());
        initView();
        registerListener();
        initBusiness();
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void registerListener();

    public abstract void initBusiness();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.tag(tag()).printLifeCycle("onNewIntent");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.tag(tag()).printLifeCycle("onActivityResult");
    }

    public MainController getMainController() {
        return this.controller;
    }

    public String tag() {
        return getClass().getSimpleName();
    }
}

