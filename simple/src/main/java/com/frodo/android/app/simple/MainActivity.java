package com.frodo.android.app.simple;

import com.frodo.android.app.core.toolbox.FragmentScheduler;
import com.frodo.android.app.ui.activity.FragmentContainerActivity;

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
        FragmentScheduler.nextFragment(this, SplashFragment.class, null, true);
    }
}
