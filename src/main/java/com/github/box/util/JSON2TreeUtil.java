package com.github.box.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.box.model.JsonTree;
import javafx.scene.control.Label;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/3/11 16:06
 */
public class JSON2TreeUtil {

    private JSON2TreeUtil() {

    }

    public static JsonTree converter(String json) {
        Object object = JSON.parse(json);
        JsonTree root = createNode("", object);
        return root;
    }

    private static JsonTree createNode(Object parent, Object object) {
        JsonTree tree = new JsonTree();
        if (parent == null) {
            tree.setKey("");
        } else {
            tree.setKey(parent.toString());
        }
        if (object instanceof JSONArray) {
            tree.setGraphicNode(new Label("[]"));
            JSONArray jsonArray = (JSONArray)object;
            int i = 0;
            for (Object arr : jsonArray) {
                tree.getChildren().add(createNode(i, arr));
                i++;
            }
        } else if (object instanceof JSONObject) {
            tree.setGraphicNode(new Label("{}"));
            JSONObject jsonObject = (JSONObject)object;
            for (String k : jsonObject.keySet()) {
                tree.getChildren().add(createNode(k, jsonObject.get(k)));
            }
        } else {
            tree.setValue(object);
        }
        return tree;
    }


    private static Object format(String json) {
        return JSON.parse(json);
    }
}
