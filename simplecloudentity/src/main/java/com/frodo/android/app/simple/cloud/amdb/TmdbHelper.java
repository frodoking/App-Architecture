package com.frodo.android.app.simple.cloud.amdb;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class TmdbHelper {
    private static final SimpleDateFormat JSON_STRING_DATE = new SimpleDateFormat("yyy-MM-dd");

    public TmdbHelper() {
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Integer.class, new JsonDeserializer() {
            public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
                    JsonParseException {
                try {
                    return Integer.valueOf(json.getAsInt());
                } catch (NumberFormatException var5) {
                    return null;
                }
            }
        });
        builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return TmdbHelper.JSON_STRING_DATE.parse(json.getAsString());
                } catch (ParseException var5) {
                    return null;
                }
            }
        });
        return builder;
    }
}
