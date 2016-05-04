package com.frodo.app.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.app.R;

/**
 * Created by frodo on 2015/1/27. FragmentContainer
 */
public abstract class FragmentContainerActivity extends AbstractBaseActivity {

    private final int CONTAINER_ID = R.id.container;
    private int enterAnimRes = -1, exitAnimRes = -1, popEnterAnimRes = -1, popExitAnimRes = -1;

    /**
     * if you override this method. you must to define a viewcontainer like this:
     * <FrameLayout
     * android:id="@+id/container"
     * android:layout_width="match_parent"
     * android:layout_height="match_parent"/>
     *
     * @return LayoutId
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    public void setTransitionAnimationsForViewContainer(@AnimRes int enter, @AnimRes int exit,
                                                        @AnimRes int popEnter, @AnimRes int popExit) {
        this.enterAnimRes = enter;
        this.exitAnimRes = exit;
        this.popEnterAnimRes = popEnter;
        this.popExitAnimRes = popExit;
    }

    public final void removeFragmentByTag(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = ensureFragmentTransaction();
            fragmentTransaction.remove(fragment).commit();
            getSupportFragmentManager().popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public final void addFragment(Class<? extends Fragment> fragmentClass, Bundle args) {
        addFragment(fragmentClass, fragmentClass.getCanonicalName(), args);
    }

    public void addFragment(Class<? extends Fragment> fragmentClass, String tag, Bundle args) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            if (args == null) {
                fragment = Fragment.instantiate(this, fragmentClass.getName());
            } else {
                fragment = Fragment.instantiate(this, fragmentClass.getName(), args);
            }
        }

        FragmentTransaction fragmentTransaction = ensureFragmentTransaction();
        if (fragment.isDetached()) {
            fragmentTransaction.attach(fragment);
        } else if (!fragment.isAdded()) {
            fragmentTransaction.replace(CONTAINER_ID, fragment, tag);
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public final void replaceFragment(Class<? extends Fragment> fragmentClass, Bundle args) {
        replaceFragment(fragmentClass, fragmentClass.getCanonicalName(), args);
    }

    public void replaceFragment(Class<? extends Fragment> fragmentClass, String tag, Bundle args) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            if (args == null) {
                fragment = Fragment.instantiate(this, fragmentClass.getName());
            } else {
                fragment = Fragment.instantiate(this, fragmentClass.getName(), args);
            }
        }

        FragmentTransaction fragmentTransaction = ensureFragmentTransaction();
        fragmentTransaction.replace(CONTAINER_ID, fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private FragmentTransaction ensureFragmentTransaction() {
        if (enterAnimRes != -1 && exitAnimRes != -1 && popEnterAnimRes != -1 && popExitAnimRes != -1) {
            return getSupportFragmentManager().beginTransaction().setCustomAnimations(enterAnimRes, exitAnimRes, popEnterAnimRes, popExitAnimRes);
        } else {
            return getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
