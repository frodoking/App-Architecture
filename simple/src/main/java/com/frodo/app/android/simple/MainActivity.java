package com.frodo.app.android.simple;

import android.content.res.Configuration;
import android.widget.Toast;

import com.frodo.app.android.ui.FragmentScheduler;
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
