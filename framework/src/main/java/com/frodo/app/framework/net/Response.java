package com.frodo.app.framework.net;

import com.google.common.base.Preconditions;

import java.util.List;

/**
 * An HTTP response.
 * <p/>
 * When used directly as a data type for an interface method, the response body is buffered to a
 * {@code byte[]}.
 * Created by xuwei19 on 2016/3/2.
 */
public final class Response<ResponseBody> {
    private final String url;
    private final int status;
    private final String reason;
    private final List<Header> headers;
    private final ResponseBody body;

    public Response(String url, int status, String reason, List<Header> headers, ResponseBody body) {
        this.url = Preconditions.checkNotNull(url, "url must not be null.");
        Preconditions.checkState(status >= 200, "Invalid status code: " + status);
        this.reason = Preconditions.checkNotNull(reason, "reason must not be null.");
        this.headers = Preconditions.checkNotNull(headers, "headers must not be null.");

        this.status = status;
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
    public ResponseBody getBody() {
        return body;
    }
}

