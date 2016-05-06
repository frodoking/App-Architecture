package com.frodo.app.android.simple;

import com.frodo.app.android.core.toolbox.FragmentScheduler;
import com.frodo.app.android.ui.activity.FragmentContainerActivity;

/**
 * Created by frodo on 2015/9/15.
 */
public class MainActivity extends FragmentContainerActivity {

    @Override
    public void initView() {
        // do nothing
    }

    @Override
    public void registerListener() {
        // do nothing
    }

    @Override
    public void initBusiness() {
        FragmentScheduler.replaceFragment(this, SplashFragment.class, null);
    }
}
