package com.frodo.app.android.core.toolbox;

import com.frodo.app.framework.entity.BeanNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by frodo on 2016/3/2.
 */
public class JsonConverter {
    public static BeanNode convert(JSONObject jsonObject) throws JSONException {
        BeanNode beanNode = null;
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.opt(key);
            if (value instanceof  JSONObject) {
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
        }

        return beanNode;
    }

    public static List<BeanNode> convert(JSONArray jsonArray) throws JSONException {
        List<BeanNode> beanNodes = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            beanNodes.add(convert(jsonArray.optJSONObject(i)));
        }
        return beanNodes;
    }
}
