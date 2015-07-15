package com.frodo.android.app.simple;

import com.frodo.android.app.ui.activity.AbstractBaseActivity;
import com.squareup.picasso.Picasso;

import android.widget.ImageView;

/**
 * Created by frodo on 2015/4/2.
 */
public class SplashActivity extends AbstractBaseActivity {
    private ImageView ad;

    @Override
    public int getLayoutId() {
        return R.layout.layout_splash;
    }

    @Override
    public void initView() {
        ad = (ImageView) findViewById(R.id.ad);
        Picasso.with(this)
                .load("http://b.hiphotos.baidu.com/image/pic/item/d009b3de9c82d15815bba0ce830a19d8bc3e4290.jpg")
                .into(ad);
    }

    @Override
    public void registerListener() {
    }

    @Override
    public void initBusiness() {
        ad.postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectToFragment(MovieFragment.class, null, true);
            }
        }, 5000);
    }
}
