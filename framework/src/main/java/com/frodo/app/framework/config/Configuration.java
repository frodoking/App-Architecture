package com.frodo.app.framework.config;

import com.frodo.app.framework.controller.ChildSystem;

import java.util.List;

/**
 * Configure 全局配置
 *
 * @author frodoking
 * @date 2014年11月26日 下午12:22:50
 */
public interface Configuration extends ChildSystem {

    List<com.frodo.app.framework.config.Environment> readEnvironments();

    void addEnvironment(com.frodo.app.framework.config.Environment environment);

    com.frodo.app.framework.config.Environment getCurrentEnvironment();

    void setCurrentEnvironment(com.frodo.app.framework.config.Environment environment);

    Object serverConfig();

    void setServerConfig(Object serverConfigObject);

    boolean isDebug();
}
