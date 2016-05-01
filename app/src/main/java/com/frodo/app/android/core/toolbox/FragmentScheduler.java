package com.frodo.app.android.core.toolbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.frodo.app.android.ui.activity.FragmentContainerActivity;

/**
 * Created by frodo on 2015/9/14. fragment Scheduler
 */
public class FragmentScheduler {

    public static void nextFragment(FragmentContainerActivity fragmentContainer,
                                    Class<? extends Fragment> fragmentClass,
                                    Bundle extra,
                                    boolean isFinishTopFragment) {
        fragmentContainer.addFragment(fragmentClass, extra, isFinishTopFragment);
    }

    public static void replaceFragment(FragmentContainerActivity fragmentContainer,
                                       Class<? extends Fragment> fragmentClass,
                                       Bundle extra) {
        fragmentContainer.replaceFragment(fragmentClass, extra);
    }
}
