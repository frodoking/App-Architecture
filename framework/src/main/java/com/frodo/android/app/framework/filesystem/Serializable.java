package com.frodo.android.app.framework.filesystem;

import java.io.IOException;

/**
 * Created by frodo on 2015/6/24.
 */
public interface Serializable {
    void serialize(com.frodo.android.app.framework.filesystem.Output out) throws IOException;

    void deserialize(Input in) throws IOException;
}
