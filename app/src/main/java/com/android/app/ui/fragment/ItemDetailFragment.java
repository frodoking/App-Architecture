package com.android.app.ui.fragment;

import android.widget.TextView;

import com.android.app.R;
import com.android.app.entity.Item;

import de.greenrobot.event.EventBus;

/**
 * Created by frodoking on 2014/12/19.
 */
public class ItemDetailFragment extends AbstractBaseFragment {

    private TextView tvDetail;

    @Override
    public void init() {
        // register
        EventBus.getDefault().register(this);
        super.init();
    }

    /**
     * List点击时会发送些事件，接收到事件后更新详情
     */
    public void onEventMainThread(Item item) {
        if (item != null)
            tvDetail.setText(item.content);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_item_detail;
    }

    @Override
    public void initView() {
        tvDetail = (TextView) getView().findViewById(R.id.item_detail);
    }

    @Override
    public void registerListener() {

    }

    @Override
    public void initBusiness() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }
}

