package com.android.app.simple;

import android.content.Intent;
import android.widget.ImageView;

import com.android.app.ui.activity.AbstractBaseActivity;
import com.android.app.ui.activity.FragmentContainerActivity2;
import com.squareup.picasso.Picasso;

/**
 * Created by frodo on 2015/4/2.
 */
public class SplashActivity extends AbstractBaseActivity {
    ImageView ad;


    @Override
    public int getLayoutId() {
        return R.layout.layout_splash;
    }

    @Override
    public void initView() {
        ad = (ImageView) findViewById(R.id.ad);
        Picasso.with(this).load("http://b.hiphotos.baidu.com/image/pic/item/d009b3de9c82d15815bba0ce830a19d8bc3e4290.jpg").into(ad);
    }

    @Override
    public void registerListener() {
    }

    @Override
    public void initBusiness() {
        ad.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent achievementIntent = new Intent(SplashActivity.this, FragmentContainerActivity2.class);
                achievementIntent.putExtra("fragment_class_name", MovieFragment.class.getCanonicalName());
                startActivity(achievementIntent);
                finish();
            }
        }, 5000);

    }
}
