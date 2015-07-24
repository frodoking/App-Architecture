package com.frodo.android.app.simple.cloud.amdb.services;

import com.frodo.android.app.simple.cloud.amdb.entities.Configuration;

import retrofit.http.GET;

public interface ConfigurationService {
    @GET("/configuration")
    Configuration configuration();
}
