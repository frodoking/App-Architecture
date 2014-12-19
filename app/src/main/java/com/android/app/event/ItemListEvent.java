package com.android.app.event;

import com.android.app.entity.Item;

import java.util.List;

/**
 * 列表加载事件
 * Created by frodoking on 2014/12/19.
 */
public class ItemListEvent extends AbstractEvent<List<Item>>{

    public ItemListEvent(List<Item> items) {
        super(items);
    }
}
