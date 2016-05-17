package com.frodo.app.framework.config;

import com.frodo.app.framework.controller.ChildSystem;

import java.util.List;

/**
 * base configuration for application
 *
 * @author frodoking
 */
public interface Configuration extends ChildSystem {

    /**
     * all {@link Environment}
     * @return {@link List<Environment>}
     */
    List<Environment> readEnvironments();

    /**
     * add {@link Environment}
     * @param environment {@link Environment}
     */
    void addEnvironment(Environment environment);

    /**
     * get current {@link Environment}
     * @return  {@link Environment}
     */
    Environment getCurrentEnvironment();

    /**
     * set current {@link Environment}
     * @param environment  {@link Environment}
     */
    void setCurrentEnvironment(Environment environment);

    /**
     * maybe you can get some configuration from server
     * @return {@link Object}
     */
    Object serverConfig();

    /**
     * set server configuration when you fetch data from net.
     * @param serverConfigObject {@link Object}
     */
    void setServerConfig(Object serverConfigObject);

    /**
     * is debug
     * @return current {@link Environment} is debuggable
     */
    boolean isDebug();
}
