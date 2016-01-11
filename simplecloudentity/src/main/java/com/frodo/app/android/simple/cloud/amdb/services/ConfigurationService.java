package com.frodo.app.android.simple.cloud.amdb.services;

import retrofit.http.GET;

public interface ConfigurationService {
    @GET("/configuration")
    com.frodo.app.android.simple.cloud.amdb.entities.Configuration configuration();
}
