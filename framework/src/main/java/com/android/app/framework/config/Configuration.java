package com.android.app.framework.config;

import com.android.app.framework.controller.ChildSystem;

/**
 * Configure 全局配置
 *
 * @author frodoking
 * @date 2014年11月26日 下午12:22:50
 */
public interface Configuration extends ChildSystem {
    String getHost();

    String getVersionName();

    int getVersionCode();

    boolean isDebug();
}
