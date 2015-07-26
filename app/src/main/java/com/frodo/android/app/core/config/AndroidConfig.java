package com.frodo.android.app.core.config;

import java.util.ArrayList;
import java.util.List;

import com.frodo.android.app.framework.config.Configuration;
import com.frodo.android.app.framework.config.Environment;
import com.frodo.android.app.framework.controller.AbstractChildSystem;
import com.frodo.android.app.framework.controller.IController;
import com.google.common.base.Preconditions;

/**
 * Created by frodo on 2015/6/20.
 */
public class AndroidConfig extends AbstractChildSystem implements Configuration {

    private List<Environment> environments = new ArrayList<>();
    private Environment environment;
    private Object serverConfig;

    public AndroidConfig(IController controller, Environment environment) {
        super(controller);
        this.environment = Preconditions.checkNotNull(environment, "Environment cannot be null");
    }

    @Override
    public final List<Environment> readEnvironments() {
        return this.environments;
    }

    @Override
    public final void addEnvironment(Environment environment) {
        for (Environment tmp : environments) {
            if (tmp.equals(environment)) {
                return;
            }
        }
        environments.add(environment);
    }

    @Override
    public final Environment getCurrentEnvironment() {
        return this.environment;
    }

    @Override
    public final void setCurrentEnvironment(Environment environment) {
        this.environment = environment;
        addEnvironment(environment);
    }

    @Override
    public final Object serverConfig() {
        return this.serverConfig;
    }

    @Override
    public final void setServerConfig(Object serverConfigObject) {
        this.serverConfig = serverConfigObject;
    }

    @Override
    public final boolean isDebug() {
        return this.environment.isDebug();
    }
}
