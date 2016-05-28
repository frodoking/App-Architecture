package com.frodo.app.android.simple;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.frodo.android.app.simple.R;
import com.frodo.app.android.core.UIView;
import com.frodo.app.android.ui.FragmentScheduler;
import com.frodo.app.android.ui.activity.FragmentContainerActivity;
import com.frodo.app.android.ui.fragment.StatedFragment;
import com.frodo.app.framework.controller.IModel;

/**
 * Created by frodo on 2015/9/15.
 */
public class SplashFragment extends StatedFragment<UIView, IModel> {
    @Override
    public UIView createUIView(Context context, LayoutInflater inflater, ViewGroup container) {
        return new UIView(this, inflater, container, R.layout.layout_splash) {
            SimpleDraweeView ad;

            @Override
            public void initView() {
                ad = (SimpleDraweeView) getRootView().findViewById(R.id.ad);
                ad.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                Uri imageUri = Uri.parse("http://b.hiphotos.baidu.com/image/pic/item/d009b3de9c82d15815bba0ce830a19d8bc3e4290.jpg");
                ad.setImageURI(imageUri);
            }

            @Override
            public void registerListener() {
                ad.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        FragmentScheduler.doDirect(getAndroidContext(), FragmentScheduler.SCHEMA + "/movie", true);
                        FragmentScheduler.replaceFragment((FragmentContainerActivity) getAndroidContext(), MovieFragment.class);
                    }
                }, 5000);
            }
        };
    }
}
