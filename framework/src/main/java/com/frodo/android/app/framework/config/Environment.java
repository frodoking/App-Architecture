package com.frodo.android.app.framework.config;

/**
 * 环境切换功能
 * Created by frodo on 2015/7/6.
 */
public class Environment {
    private int id;
    private String name;
    private String url;
    private String apiKey;
    private boolean debug;

    public Environment(int id, String name, String url, String apiKey, boolean debug) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.apiKey = apiKey;
        this.debug = debug;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean isDebug() {
        return debug;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Environment) {
            Environment env = (Environment) obj;
            if (env.id == this.id && env.name.equals(this.name) && env.url.equals(this.url) && env.apiKey
                    .equals(this.apiKey)
                    && env.debug == this.debug) {
                return true;
            }
        }
        return super.equals(obj);
    }
}
