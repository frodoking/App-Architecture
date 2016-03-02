package com.frodo.app.framework.net;

import com.frodo.app.framework.net.mime.TypedInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An HTTP response.
 * <p/>
 * When used directly as a data type for an interface method, the response body is buffered to a
 * {@code byte[]}.
 * Created by xuwei19 on 2016/3/2.
 */
public final class Response {
    private final String url;
    private final int status;
    private final String reason;
    private final List<Header> headers;
    private final TypedInput body;

    public Response(String url, int status, String reason, List<Header> headers, TypedInput body) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        if (status < 200) {
            throw new IllegalArgumentException("Invalid status code: " + status);
        }
        if (reason == null) {
            throw new IllegalArgumentException("reason == null");
        }
        if (headers == null) {
            throw new IllegalArgumentException("headers == null");
        }

        this.url = url;
        this.status = status;
        this.reason = reason;
        this.headers = Collections.unmodifiableList(new ArrayList<>(headers));
        this.body = body;
    }

    /**
     * Request URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Status line code.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Status line reason phrase.
     */
    public String getReason() {
        return reason;
    }

    /**
     * An unmodifiable collection of headers.
     */
    public List<Header> getHeaders() {
        return headers;
    }

    /**
     * Response body. May be {@code null}.
     */
    public TypedInput getBody() {
        return body;
    }
}

