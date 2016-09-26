package com.frodo.app.android.core.network.integration.okhttp;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.log.Logger;
import com.frodo.app.framework.net.Header;
import com.frodo.app.framework.net.HttpModule;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * okhttp module
 * Created by frodo on 2016/3/6.
 */
public class OkHttpModule implements HttpModule {

    private static volatile OkHttpClient internalClient;

    private static OkHttpClient getInternalClient() {
        if (internalClient == null) {
            synchronized (OkHttpModule.class) {
                if (internalClient == null) {
                    internalClient = new OkHttpClient();
                }
            }
        }
        return internalClient;
    }

    private OkHttpClient client;
    private Options options;

    public OkHttpModule() {
        client = getInternalClient();
    }

    @Override
    public void apply(MainController controller, Options options) {
        this.options = options;
        OkHttpClient.Builder builder = client.newBuilder();
        builder.connectTimeout(options.connectTimeout, TimeUnit.MILLISECONDS);
        builder.readTimeout(options.connectTimeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(options.connectTimeout, TimeUnit.MILLISECONDS);
        if (controller.getConfig().isDebug()) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        client = builder.build();
    }

    @Override
    public Response execute(Request request) throws HttpException {
        try {
            return parseResponse(client.newCall(createRequest(this.options.apiUrl, request)).execute());
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    private static okhttp3.Request createRequest(String apiUrl, Request<RequestBody> request) {
        String url;
        if (request.getUrl().startsWith("http")) {
            url = request.getUrl();
        } else {
            url = apiUrl + request.getUrl();
        }
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
                .url(url)
                .method(request.getMethod(), request.getBody());

        Logger.fLog().tag("RequestURL").i(String.format("[ %s ]", url));

        List<Header> headers = request.getHeaders();
        for (int i = 0, size = headers.size(); i < size; i++) {
            Header header = headers.get(i);
            String value = header.getValue();
            if (value == null) value = "";
            builder.addHeader(header.getName(), value);
        }

        return builder.build();
    }

    private static Response<ResponseBody> parseResponse(okhttp3.Response response) throws IOException {
        return new Response<>(response.request().url().toString(), response.code(), response.message(),
                createHeaders(response.headers()), response.body());
    }

    private static List<Header> createHeaders(Headers headers) {
        int size = headers.size();
        List<Header> headerList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            headerList.add(new Header(headers.name(i), headers.value(i)));
        }
        return headerList;
    }
}
