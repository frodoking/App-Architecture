package com.frodo.app.framework.config;

/**
 * 环境切换功能
 * Created by frodo on 2015/7/6.
 */
public class Environment {
    private int mId;
    private String mName;
    private String mUrl;
    private String mApiKey;
    private boolean isDebug;

    public Environment(int id, String name, String url, String apiKey, boolean debug) {
        this.mId = id;
        this.mName = name;
        this.mUrl = url;
        this.mApiKey = apiKey;
        this.isDebug = debug;
    }

    public String getmName() {
        return mName;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmApiKey() {
        return mApiKey;
    }

    public boolean isDebug() {
        return isDebug;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Environment) {
            Environment env = (Environment) obj;
            if (env.mId == this.mId && env.mName.equals(this.mName) && env.mUrl.equals(this.mUrl) && env.mApiKey
                    .equals(this.mApiKey)
                    && env.isDebug == this.isDebug) {
                return true;
            }
        }
        return super.equals(obj);
    }
}
