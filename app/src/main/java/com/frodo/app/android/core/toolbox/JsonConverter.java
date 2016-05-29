package com.frodo.app.android.core.toolbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by frodo on 2016/3/2. Convert JSONObject To BeanNode Tree By Jackson.
 */
public class JsonConverter {

    public static String toJson(Object objectValue) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(objectValue);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T convert(String jsonString, Class<T> tClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(jsonString, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T convert(String jsonString, TypeReference type) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
