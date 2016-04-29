package com.frodo.app.framework.net;

import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.exception.HttpException;

/**
 * Created by frodo on 2016/3/3.
 */
public interface HttpModule {
    void apply(MainController controller, Options options);

    Response execute(Request request) throws HttpException;

    class Options {
        public String apiUrl;
        public String cacheDir;
        public int connectTimeout;
        public int readTimeout;
        public int writeTimeout;

        public Options(String apiUrl, String cacheDir, int connectTimeout, int readTimeout, int writeTimeout) {
            this.apiUrl = apiUrl;
            this.cacheDir = cacheDir;
            this.connectTimeout = connectTimeout;
            this.readTimeout = readTimeout;
            this.writeTimeout = writeTimeout;
        }
    }
}
