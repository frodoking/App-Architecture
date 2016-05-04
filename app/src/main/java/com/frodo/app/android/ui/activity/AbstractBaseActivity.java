package com.frodo.app.android.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.frodo.app.android.MicroApplication;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.log.Logger;

/**
 * Created by frodo on 2014/12/26. print whole life cycle
 */
public abstract class AbstractBaseActivity extends AppCompatActivity {
    private static final String LIFECYCLE = "_LifeCycle_A";
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = ((MicroApplication) getApplication()).getMainController();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onCreate");
        init();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onAttachFragment");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onContentChanged");
    }

    /**
     * from onStop()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onSaveInstanceState");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onPostCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onResume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onPostResume");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onAttachedToWindow");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Logger.fLog().tag(tag() + LIFECYCLE).i("onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Logger.fLog().tag(tag() + LIFECYCLE).i("onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.fLog().tag(tag() + LIFECYCLE).i("onDestroy");
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
        Logger.fLog().tag(tag() + LIFECYCLE).i("onNewIntent");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.fLog().tag(tag() + LIFECYCLE).i("onActivityResult");
    }

    public MainController getMainController() {
        return this.controller;
    }

    public String tag() {
        return getClass().getSimpleName();
    }
}

