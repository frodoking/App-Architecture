package com.frodo.app.framework.net;


import com.frodo.app.framework.toolbox.TextUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates all of the information necessary to make an HTTP request.
 * Created by frodo on 2016/3/2.
 */
public final class Request<RequestBody> {
    private final String method;
    private String relativeUrl;
    private final List<Header> headers;
    private final RequestBody body;
    private Map<String, Object> queryParams;

    public Request(String method, String relativeUrl, Map<String, Object> params, List<Header> headers, RequestBody body) {
        this.method = Preconditions.checkNotNull(method, "Method must not be null.");
        this.relativeUrl = Preconditions.checkNotNull(relativeUrl, "URL must not be null.");

        if (headers == null) {
            this.headers = Lists.newArrayList();
        } else {
            this.headers = headers;
        }

        if (params == null) {
            this.queryParams = Maps.newHashMap();
        } else {
            this.queryParams = params;
        }

        this.body = body;
    }

    /**
     * HTTP method verb.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Target URL.
     */
    public String getUrl() {
        String paramString = createParamString(this.queryParams);
        if (TextUtils.isEmpty(paramString)) {
            return relativeUrl;
        } else {
            return relativeUrl + "?" + paramString;
        }
    }

    /**
     * Returns an list of headers, never {@code null}.
     */
    public List<Header> getHeaders() {
        return headers;
    }

    /**
     * Returns the request body or {@code null}.
     */
    public RequestBody getBody() {
        return body;
    }

    public void addQueryParam(String name, String value) {
        addQueryParam(name, value, false, true);
    }

    private void addQueryParam(String name, String value, boolean encodeName, boolean encodeValue) {
        Preconditions.checkNotNull(name, "Query param name must not be null.");
        Preconditions.checkNotNull(value, "Query param \"" + name + "\" value must not be null.");
        try {
            if (encodeName) {
                name = URLEncoder.encode(name, "UTF-8");
            }
            if (encodeValue) {
                value = URLEncoder.encode(value, "UTF-8");
            }

            queryParams.put(name, value);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(
                    "Unable to convert query parameter \"" + name + "\" value to UTF-8: " + value, e);
        }
    }

    private static String createParamString(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (String key : params.keySet()) {
                sb.append(key).append('=').append(params.get(key).toString()).append("&");
            }
            return sb.substring(0, sb.length() - 1);
        }
    }

    public static class Builder<RequestBody> {
        private String method;
        private String relativeUrl;
        private List<Header> headers;
        private RequestBody body;
        private Map<String, Object> queryParams;

        public Request build() {
            return new Request<RequestBody>(method, relativeUrl, queryParams, headers, body);
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder relativeUrl(String relativeUrl) {
            this.relativeUrl = relativeUrl;
            return this;
        }

        public Builder headers(List<Header> headers) {
            this.headers = headers;
            return this;
        }

        public Builder body(RequestBody body) {
            this.body = body;
            return this;
        }

        public Builder params(Map<String, Object> queryParams) {
            this.queryParams = queryParams;
            return this;
        }
    }
}

