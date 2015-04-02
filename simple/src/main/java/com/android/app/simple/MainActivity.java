package com.android.app.simple;

import com.android.app.ui.activity.FragmentContainerActivity;

/**
 * Created by frodo on 2015/4/2.
 */
public class MainActivity extends FragmentContainerActivity {

    @Override
    public void initView() {
        addFragment(MovieFragment.class);
    }

    @Override
    public void registerListener() {

    }

    @Override
    public void initBusiness() {

    }
}
