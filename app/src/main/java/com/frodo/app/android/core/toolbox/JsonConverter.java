package com.frodo.app.android.core.toolbox;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frodo.app.framework.entity.BeanNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by frodo on 2016/3/2. Convert JSONObject To BeanNode Tree By Jackson.
 */
public class JsonConverter {
    public static BeanNode convert(JSONObject jsonObject) throws JSONException {
        BeanNode rootNode = new BeanNode(jsonObject.length());

        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.opt(key);

            BeanNode beanNode;
            if (value instanceof JSONObject) {
                beanNode = new BeanNode(1);
                JSONObject jsonObjectTmp = (JSONObject) value;
                beanNode.addChildNode(convert(jsonObjectTmp));
                beanNode.value = beanNode.getChildArray();
            } else if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                beanNode = new BeanNode(array.length());
                beanNode.value = convert(array);
            } else {
                beanNode = new BeanNode();
                beanNode.value = value;
            }

            beanNode.name = key;

            rootNode.addChildNode(beanNode);
        }

        return rootNode;
    }

    public static List<BeanNode> convert(JSONArray jsonArray) throws JSONException {
        List<BeanNode> beanNodes = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            Object obj = jsonArray.opt(i);
            BeanNode node;
            if (obj instanceof JSONObject) {
                node = convert((JSONObject) obj);
            } else {
                node = new BeanNode();
                node.name = "@null@";
                node.value = obj;
            }

            beanNodes.add(node);
        }
        return beanNodes;
    }

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
