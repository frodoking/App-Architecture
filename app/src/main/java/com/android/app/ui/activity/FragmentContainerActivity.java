package com.android.app.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.app.R;
import com.android.app.ui.FragmentStack;
import com.android.app.ui.fragment.AbstractBaseFragment;

/**
 * Created by frodo on 2015/1/27.
 */
public abstract class FragmentContainerActivity extends AbstractBaseActivity {

    private FragmentStack mStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mStack = FragmentStack.forContainer(this, R.id.container,
                new FragmentStack.Callback() {
                    @Override
                    public void onStackChanged(int stackSize, Fragment topFragment) {
                    }
                });
        mStack.setDefaultAnimation(R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left);

        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mStack.replace(mStack.peek().getClass(),
                    String.valueOf(System.currentTimeMillis()),
                    mStack.peek().getArguments());
            mStack.commit();
        } else {
            mStack.restoreState(savedInstanceState);
        }
    }

    @Override
    public void init() {

    }

    @Override
    public final int getLayoutId() {
        return R.layout.fragment_container;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mStack.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public final void onBackPressed() {
        if (isForPop())
            return;

        super.onBackPressed();
    }

    public final void addFragment(Class<? extends Fragment> fragment) {
        addFragment(fragment, String.valueOf(System.currentTimeMillis()), null);
    }

    public final void addFragment(Class<? extends Fragment> fragment, Bundle args) {
        addFragment(fragment, String.valueOf(System.currentTimeMillis()), args);
    }

    public final void addFragment(Class<? extends Fragment> fragment, String tag) {
        addFragment(fragment, tag, null);
    }

    private void addFragment(Class<? extends Fragment> fragment, String tag, Bundle args) {
        mStack.push(fragment, tag, args);
        mStack.commit();
    }

    private AbstractBaseFragment getTopFragment() {
        return (AbstractBaseFragment) mStack.peek();
    }

    private boolean isForPop() {
        if (!isRootFragment()) {
            getTopFragment().onBackPressed();
            return mStack.pop(true);
        }

        return false;
    }

    private boolean isRootFragment() {
        return mStack.size() == 1;
    }
}
