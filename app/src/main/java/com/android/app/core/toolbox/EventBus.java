package com.android.app.core.toolbox;

import com.squareup.otto.Bus;

/**
 * 将事件总线用单例来封装，提高利用率<br>
 * Bus必须在UI主线程中完成<br>
 * 主要作用是用来在view之间传递的工具<br>
 * 禁止用来在模块之间的传递(这样会破坏代码的通信架构)<br>
 * Created by frodo on 2015/4/20.
 */
public final class EventBus {

    private static class Holder {
        private static EventBus DEFAULT = new EventBus();
    }

    public static EventBus getDefault() {
        return Holder.DEFAULT;
    }

    private Bus bus;

    private EventBus() {
        bus = new Bus();
    }

    public void register(Object object) {
        bus.register(object);
    }

    public void unregister(Object object) {
        bus.unregister(object);
    }

    public void post(Object object) {
        bus.post(object);
    }
}
