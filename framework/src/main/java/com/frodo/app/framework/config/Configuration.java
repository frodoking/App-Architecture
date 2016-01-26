package com.frodo.app.framework.config;

import com.frodo.app.framework.controller.ChildSystem;

import java.util.List;

/**
 * Configure 全局配置
 *
 * @author frodoking
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
