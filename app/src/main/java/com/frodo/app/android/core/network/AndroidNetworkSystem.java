package com.frodo.app.android.core.network;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.frodo.app.framework.controller.AbstractChildSystem;
import com.frodo.app.framework.controller.IController;
import com.frodo.app.framework.controller.Interceptor;
import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.log.Logger;
import com.frodo.app.framework.net.Header;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;
import com.frodo.app.framework.net.mime.TypedInput;
import com.frodo.app.framework.net.mime.TypedOutput;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okio.BufferedSink;

/**
 * Network Request
 * Created by frodo on 2015/6/20.
 */
public class AndroidNetworkSystem extends AbstractChildSystem implements NetworkTransport {
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 20 * 1000; // 20s
    public static final int DEFAULT_WRITE_TIMEOUT_MILLIS = 20 * 1000; // 20s
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s

    private Context context;
    private String apiUrl;
    private OkHttpClient client;
    private List<Interceptor> interceptorList = new ArrayList<>();

    private static OkHttpClient generateDefaultOkHttp() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        client.setReadTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        return client;
    }

    public AndroidNetworkSystem(IController controller) {
        super(controller);
        this.context = (Context) controller.getMicroContext();
        client = generateDefaultOkHttp();

        if (getController().getCacheSystem() != null) {
            OkHttpClient client = new OkHttpClient();

            File cacheDir = new File(getController().getCacheSystem().getCacheDir(), UUID.randomUUID().toString());
            Cache cache = new Cache(cacheDir, 1024);
            client.setCache(cache);

            client.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            client.setReadTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            client.setWriteTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean flag = false;
        if ((mWifi != null) && ((mWifi.isAvailable()) || (mMobile.isAvailable()))) {
            if ((mWifi.isConnected()) || (mMobile.isConnected())) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean isGpsEnabled() {
        LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    @Override
    public boolean isWifiEnabled() {
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((getNetworkInfo() != null && getNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    @Override
    public boolean is3rd() {
        if (getNetworkInfo() != null && getNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isWifi() {
        if (getNetworkInfo() != null && getNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    @Override
    public String getAPIUrl() {
        return apiUrl;
    }

    @Override
    public void setAPIUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public Response execute(Request request) throws HttpException {
        try {
            return parseResponse(client.newCall(createRequest(apiUrl, request)).execute());
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    @Override
    public void addInterceptor(Interceptor interceptor) {

    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private NetworkInfo getNetworkInfo() {
        return getConnectivityManager().getActiveNetworkInfo();
    }


    static com.squareup.okhttp.Request createRequest(String apiUrl, Request request) {
        com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder()
                .url(apiUrl + request.getUrl())
                .method(request.getMethod(), createRequestBody(request.getBody()));

        Logger.fLog().i(String.format("RequestURL: [ %s ]", apiUrl + request.getUrl()));

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
