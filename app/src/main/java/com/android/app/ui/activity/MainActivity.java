package com.android.app.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.app.R;
import com.android.app.ui.FragmentStack;
import com.android.app.ui.fragment.AbstractBaseFragment;

/**
 * Created by frodo on 2015/1/27.
 */
public class MainActivity extends AbstractBaseActivity {

    private FragmentStack mStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStack = FragmentStack.forContainer(this, R.id.main_container,
                new FragmentStack.Callback() {
                    @Override
                    public void onStackChanged(int stackSize, Fragment topFragment) {
                    }
                });
        mStack.setDefaultAnimation(R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left);

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
    public int getLayoutId() {
        return R.layout.layout_main;
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
    protected void onSaveInstanceState(Bundle outState) {
        mStack.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (isForPop())
            return;

        super.onBackPressed();
    }

    /**
     * 添加当前时间作为tag 是因为相同tag会重复 导致加载不成功
     *
     * @param fragment
     */
    public void addFragment(Class<? extends Fragment> fragment) {
        mStack.push(fragment, String.valueOf(System.currentTimeMillis()));
        mStack.commit();
    }

    public void addFragment(Class<? extends Fragment> fragment, Bundle args) {
        addFragment(fragment, String.valueOf(System.currentTimeMillis()), args);
    }

    public void addFragment(Class<? extends Fragment> fragment, String tag) {
        addFragment(fragment, tag, null);
    }

    public void addFragment(Class<? extends Fragment> fragment, String tag, Bundle args) {
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
