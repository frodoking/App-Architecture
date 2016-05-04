package com.frodo.app.android.simple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.frodo.android.app.simple.R;
import com.frodo.app.android.core.UIView;
import com.frodo.app.android.core.toolbox.FragmentScheduler;
import com.frodo.app.android.ui.activity.FragmentContainerActivity;
import com.frodo.app.android.ui.fragment.AbstractBaseFragment;

/**
 * Created by frodo on 2015/9/15.
 */
public class SplashFragment extends AbstractBaseFragment {
    @Override
    public UIView createUIView(Context context, LayoutInflater inflater, ViewGroup container) {
        return new UIView(this, inflater, container, R.layout.layout_splash) {
            ImageView ad;

            @Override
            public void initView() {
                ad = (ImageView) getRootView().findViewById(R.id.ad);
                Glide.with(getPresenter().getAndroidContext())
                        .load("http://b.hiphotos.baidu.com/image/pic/item/d009b3de9c82d15815bba0ce830a19d8bc3e4290.jpg")
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .centerCrop()
                        .into(ad);
            }

            @Override
            public void registerListener() {
                ad.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentScheduler.removeFragment((FragmentContainerActivity) getActivity(), SplashFragment.this.getClass().getCanonicalName());
                        FragmentScheduler.nextFragment((FragmentContainerActivity) getActivity(), MovieFragment.class, null);
                    }
                }, 5000);
            }
        };
    }
}
