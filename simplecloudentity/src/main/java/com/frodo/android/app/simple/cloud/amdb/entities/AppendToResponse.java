package com.frodo.android.app.simple.cloud.amdb.entities;

import com.frodo.android.app.simple.cloud.amdb.enumerations.AppendToResponseItem;

public class AppendToResponse {
    private final AppendToResponseItem[] items;

    public AppendToResponse(AppendToResponseItem... items) {
        this.items = items;
    }

    public String toString() {
        if (this.items != null && this.items.length > 0) {
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < this.items.length; ++i) {
                sb.append(this.items[i]);
                if (i < this.items.length - 1) {
                    sb.append(',');
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }
}