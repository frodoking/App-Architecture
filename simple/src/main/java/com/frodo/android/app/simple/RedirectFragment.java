package com.frodo.android.app.simple;

import com.frodo.android.app.framework.controller.IPresenter;
import com.frodo.android.app.ui.fragment.AbstractBaseFragment;

import android.widget.TextView;

/**
 * Created by frodo on 2015/7/10.
 */
public class RedirectFragment extends AbstractBaseFragment {
    @Override
    public void onCreatePresenter() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_redirect;
    }

    @Override
    public void initView() {
    }

    @Override
    public void registerListener() {
        TextView tv = (TextView) getView().findViewById(R.id.imei);
        RxJavaApi.test(tv);
    }

    @Override
    public void initBusiness() {

    }

    @Override
    public IPresenter getPresenter() {
        return null;
    }
}
