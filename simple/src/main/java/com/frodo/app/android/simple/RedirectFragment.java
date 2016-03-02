package com.frodo.app.android.simple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frodo.android.app.simple.R;
import com.frodo.app.android.core.UIView;
import com.frodo.app.android.ui.fragment.AbstractBaseFragment;

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
                tv.setText("this is Redirect Page (Only for test.)");
            }

            @Override
            public void registerListener() {
            }
        };
    }
}
