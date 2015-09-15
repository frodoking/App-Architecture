package com.frodo.android.app.simple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frodo.android.app.core.UIView;
import com.frodo.android.app.framework.controller.AbstractModel;
import com.frodo.android.app.framework.controller.IModel;
import com.frodo.android.app.ui.fragment.AbstractBaseFragment;

/**
 * Created by frodo on 2015/7/10.
 */
public class RedirectFragment extends AbstractBaseFragment {

    @Override
    public UIView createUIView(Context context, LayoutInflater inflater, ViewGroup container) {
        return new UIView(null, inflater, container, R.layout.layout_redirect) {
            @Override
            public void initView() {
                TextView tv = (TextView) getRootView().findViewById(R.id.imei);
                RxJavaApi.test(tv);
            }

            @Override
            public void registerListener() {
            }
        };
    }

    @Override
    public IModel createModel() {
        return new AbstractModel(getMainController()) {
            @Override
            public void initBusiness() {
                // do nothing
            }
        };
    }
}
