package com.frodo.app.framework.net.mime;

import java.io.IOException;
import java.io.InputStream;

/**
 * Binary data with an associated mime type.
 * <p/>
 * Created by frodo on 2016/3/2.
 */
public interface TypedInput {

    /**
     * Returns the mime type.
     */
    String mimeType();

    /**
     * Length in bytes. Returns {@code -1} if length is unknown.
     */
    long length() throws IOException;

    /**
     * Read bytes as stream. Unless otherwise specified, this method may only be called once. It is
     * the responsibility of the caller to close the stream.
     */
    InputStream in() throws IOException;
}
