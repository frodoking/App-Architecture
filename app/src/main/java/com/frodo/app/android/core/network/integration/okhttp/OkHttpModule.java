package com.frodo.app.android.core.network.integration.okhttp;

import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.log.Logger;
import com.frodo.app.framework.net.Header;
import com.frodo.app.framework.net.HttpModule;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;
import com.frodo.app.framework.net.mime.TypedInput;
import com.frodo.app.framework.net.mime.TypedOutput;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okio.BufferedSink;

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
        client.setConnectTimeout(options.connectTimeout, TimeUnit.MILLISECONDS);
        client.setReadTimeout(options.readTimeout, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(options.writeTimeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public Response execute(Request request) throws HttpException {
        try {
            return parseResponse(client.newCall(createRequest(this.options.apiUrl, request)).execute());
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    static com.squareup.okhttp.Request createRequest(String apiUrl, Request request) {
        com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder()
                .url(apiUrl + request.getUrl())
                .method(request.getMethod(), createRequestBody(request.getBody()));

        Logger.fLog().tag("RequestURL").i(String.format("[ %s ]", apiUrl + request.getUrl()));

        List<Header> headers = request.getHeaders();
        for (int i = 0, size = headers.size(); i < size; i++) {
            Header header = headers.get(i);
            String value = header.getValue();
            if (value == null) value = "";
            builder.addHeader(header.getName(), value);
        }

        return builder.build();
    }

    static Response parseResponse(com.squareup.okhttp.Response response) throws IOException {
        return new Response(response.request().urlString(), response.code(), response.message(),
                createHeaders(response.headers()), createResponseBody(response.body()));
    }

    private static RequestBody createRequestBody(final TypedOutput body) {
        if (body == null) {
            return null;
        }
        final MediaType mediaType = MediaType.parse(body.mimeType());
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                body.writeTo(sink.outputStream());
            }

            @Override
            public long contentLength() {
                return body.length();
            }
        };
    }

    private static TypedInput createResponseBody(final ResponseBody body) throws IOException {
        if (body.contentLength() == 0) {
            return null;
        }
        return new TypedInput() {
            @Override
            public String mimeType() {
                MediaType mediaType = body.contentType();
                return mediaType == null ? null : mediaType.toString();
            }

            @Override
            public long length() throws IOException {
                return body.contentLength();
            }

            @Override
            public InputStream in() throws IOException {
                return body.byteStream();
            }
        };
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
