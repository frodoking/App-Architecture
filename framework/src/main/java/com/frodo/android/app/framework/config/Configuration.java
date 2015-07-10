package com.frodo.android.app.framework.config;

import java.util.List;

import com.frodo.android.app.framework.controller.ChildSystem;

/**
 * Configure 全局配置
 *
 * @author frodoking
 * @date 2014年11月26日 下午12:22:50
 */
public interface Configuration extends ChildSystem {

    List<com.frodo.android.app.framework.config.Environment> readEnvironments();

    void addEnvironment(com.frodo.android.app.framework.config.Environment environment);

    com.frodo.android.app.framework.config.Environment getCurrentEnvironment();

    void setCurrentEnvironment(com.frodo.android.app.framework.config.Environment environment);

    boolean isDebug();
}
