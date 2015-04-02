package com.android.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.android.app.R;
import com.android.app.ui.fragment.AbstractBaseFragment;

/**
 * Created by frodo on 2015/4/2.
 */
public class FragmentContainerActivity2 extends AbstractBaseActivity {

    private AbstractBaseFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String fragmentName = intent.getStringExtra("fragment_class_name");
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        if (savedInstanceState != null) {
            mCurrentFragment = (AbstractBaseFragment) fm.findFragmentByTag(fragmentName);
        }

        if (mCurrentFragment == null) {
            mCurrentFragment = (AbstractBaseFragment) Fragment.instantiate(this, fragmentName, bundle);
            ft.replace(R.id.container, mCurrentFragment, fragmentName);
        } else {
            ft.attach(mCurrentFragment);
        }
        ft.commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    public void initView() {
    }

    @Override
    public void registerListener() {
    }

    @Override
    public void initBusiness() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mCurrentFragment.onBackPressed())
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
