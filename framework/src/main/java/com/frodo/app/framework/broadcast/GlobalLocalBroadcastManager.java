package com.frodo.app.framework.broadcast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frodo on 2015/12/29.
 */
public final class GlobalLocalBroadcastManager implements LocalBroadcastManager {

    private final Map<String, List<MessageInterceptor>> mMap = new HashMap<>();

    @Override
    public void onBroadcast(String group, Object message) {
        if (mMap.containsKey(group)) {
            for (MessageInterceptor messageInterceptor : mMap.get(group)) {
                messageInterceptor.intercept(message);
            }
        }
    }

    @Override
    public void onBroadcastAll(Object message) {
        for (String group : mMap.keySet()) {
            for (MessageInterceptor messageInterceptor : mMap.get(group)) {
                messageInterceptor.intercept(message);
            }
        }
    }

    @Override
    public void unRegister(String group, MessageInterceptor listener) {
        if (mMap.containsKey(group)) {
            for (MessageInterceptor messageInterceptor : mMap.get(group)) {
                if (messageInterceptor.equals(listener)) {
                    mMap.get(group).remove(listener);
                }
            }
        }
    }

    @Override
    public void unRegisterGroup(String group) {
        if (mMap.containsKey(group)) {
            mMap.remove(group);
        }
    }

    @Override
    public void unRegisterAll() {
        mMap.clear();
    }

    @Override
    public void register(String group, MessageInterceptor listener) {
        if (mMap.containsKey(group)) {
            if (!mMap.get(group).contains(listener)) {
                mMap.get(group).add(listener);
            }
        } else {
            List<MessageInterceptor> list = new ArrayList<>();
            list.add(listener);
            mMap.put(group, list);
        }
    }
}
