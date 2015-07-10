package com.frodo.android.app.framework.config;

/**
 * 环境切换功能
 * Created by frodo on 2015/7/6.
 */
public class Environment {
    private int id;
    private String name;
    private String url;
    private boolean debug;

    public Environment(int id, String name, String url, boolean debug) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.debug = debug;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public boolean isDebug() {
        return debug;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Environment) {
            Environment env = (Environment) obj;
            if (env.id == this.id && env.name.equals(this.name) && env.url.equals(this.url)
                    && env.debug == this.debug) {
                return true;
            }
        }
        return super.equals(obj);
    }
}
