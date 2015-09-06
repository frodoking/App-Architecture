package com.frodo.android.app.framework.config;

import com.frodo.android.app.framework.controller.ChildSystem;

import java.util.List;

/**
 * Configure 全局配置
 *
 * @author frodoking
 * @date 2014年11月26日 下午12:22:50
 */
public interface Configuration extends ChildSystem {

    List<Environment> readEnvironments();

    void addEnvironment(Environment environment);

    Environment getCurrentEnvironment();

    void setCurrentEnvironment(Environment environment);

    Object serverConfig();

    void setServerConfig(Object serverConfigObject);

    boolean isDebug();
}
