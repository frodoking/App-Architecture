package com.android.app.event;

import com.android.app.entity.Item;

import java.util.List;

/**
 * 列表加载事件
 * Created by xuwei19 on 2014/12/19.
 */
public class ItemListEvent {
    private List<Item> items;

    public ItemListEvent(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
