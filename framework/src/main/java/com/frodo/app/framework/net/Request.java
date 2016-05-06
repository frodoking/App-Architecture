package com.frodo.app.framework.net;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
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
    private StringBuilder queryParams;

    public Request(String method, String relativeUrl) {
        this(method, relativeUrl, null, null, null);
    }

    public Request(String method, String relativeUrl, Map<String, Object> params) {
        this(method, relativeUrl, params, null, null);
    }

    public Request(String method, String relativeUrl, List<Header> headers) {
        this(method, relativeUrl, null, headers, null);
    }

    public Request(String method, String relativeUrl, Map<String, Object> params, List<Header> headers) {
        this(method, relativeUrl, params, headers, null);
    }

    public Request(String method, String relativeUrl, Map<String, Object> params, List<Header> headers, RequestBody body) {
        if (method == null) {
            throw new NullPointerException("Method must not be null.");
        }
        if (relativeUrl == null) {
            throw new NullPointerException("URL must not be null.");
        }
        this.method = method;
        this.relativeUrl = relativeUrl;

        if (headers == null) {
            this.headers = Collections.emptyList();
        } else {
            this.headers = headers;
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
        StringBuilder url = new StringBuilder(relativeUrl);
        StringBuilder queryParams = this.queryParams;
        if (queryParams != null) {
            url.append(queryParams);
        }
        return url.toString();
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
        if (name == null) {
            throw new IllegalArgumentException("Query param name must not be null.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Query param \"" + name + "\" value must not be null.");
        }
        try {
            StringBuilder queryParams = this.queryParams;
            if (queryParams == null) {
                this.queryParams = queryParams = new StringBuilder();
            }

            if (queryParams.length() > 0) {
                if (queryParams.charAt(queryParams.length() - 1) != '?') {
                    queryParams.append('&');
                }
            } else {
                queryParams.append('?');
            }


            if (encodeName) {
                name = URLEncoder.encode(name, "UTF-8");
            }
            if (encodeValue) {
                value = URLEncoder.encode(value, "UTF-8");
            }
            queryParams.append(name).append('=').append(value);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(
                    "Unable to convert query parameter \"" + name + "\" value to UTF-8: " + value, e);
        }
    }
}

