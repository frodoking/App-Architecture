package com.frodo.app.framework.broadcast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frodo on 2015/12/29.
 */
public final class GlobalLocalBroadcastManager implements LocalBroadcastManager {

    private final Map<String, List<MessageInterceptor>> map = new HashMap<>();

    @Override
    public void onBroadcast(String group, Object message) {
        if (map.containsKey(group)) {
            for (MessageInterceptor messageInterceptor : map.get(group)) {
                messageInterceptor.intercept(message);
            }
        }
    }

    @Override
    public void onBroadcastAll(Object message) {
        for (String group : map.keySet()) {
            for (MessageInterceptor messageInterceptor : map.get(group)) {
                messageInterceptor.intercept(message);
            }
        }
    }

    @Override
    public void unRegister(String group, MessageInterceptor listener) {
        if (map.containsKey(group)) {
            for (MessageInterceptor messageInterceptor : map.get(group)) {
                if (messageInterceptor.equals(listener)) {
                    map.get(group).remove(listener);
                }
            }
        }
    }

    @Override
    public void unRegisterGroup(String group) {
        if (map.containsKey(group)) {
            map.remove(group);
        }
    }

    @Override
    public void unRegisterAll() {
        map.clear();
    }

    @Override
    public void register(String group, MessageInterceptor listener) {
        if (map.containsKey(group)) {
            if (!map.get(group).contains(listener)) {
                map.get(group).add(listener);
            }
        } else {
            List<MessageInterceptor> list = new ArrayList<>();
            list.add(listener);
            map.put(group, list);
        }
    }
}
