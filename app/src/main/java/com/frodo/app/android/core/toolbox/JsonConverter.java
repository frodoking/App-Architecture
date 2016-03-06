package com.frodo.app.android.core.toolbox;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by frodo on 2016/3/2. Convert JSONObject To BeanNode Tree By Jackson.
 */
public class JsonConverter {

    public static <T> T convert(String jsonString, Class<T> tClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T convert(String jsonString, TypeReference type) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
