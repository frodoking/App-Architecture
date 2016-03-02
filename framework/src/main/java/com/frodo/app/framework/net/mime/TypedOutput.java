package com.frodo.app.framework.net.mime;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Binary data with an associated mime type.
 *
 * Created by frodo on 2016/3/2.
 */
public interface TypedOutput {
    /** Original filename.
     *
     * Used only for multipart requests, may be null. */
    String fileName();

    /** Returns the mime type. */
    String mimeType();

    /** Length in bytes or -1 if unknown. */
    long length();

    /** Writes these bytes to the given output stream. */
    void writeTo(OutputStream out) throws IOException;
}
