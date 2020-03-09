package com.github.box.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.github.box.model.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/3/9 17:28
 */
public class Json2TreeUtil {

    /**
     * todo
     * @param object
     * @return
     */
    public static Tree converter(Object object){
        Tree root = new Tree("ROOT", "");
        root.setChildren(getChildren("ROOT", object));
        return root;
    }

    public static List<Tree> getChildren(String key, Object object) {
        List<Tree> children = new ArrayList<>();
        if (object instanceof JSONObject) {
            Tree tree = new Tree(key, "{}");
            children.add(tree);
            JSONObject jsonObject = (JSONObject)object;
            tree.setChildren(jsonObject.keySet().stream().map(k -> getChild(k, jsonObject.get(k))).collect(Collectors.toList()));
        } else if (object instanceof JSONArray) {
            Tree tree = new Tree(key, "[]");
            children.add(tree);
            JSONArray array = (JSONArray) object;
            array.forEach(arr -> {
                tree.setChildren(getChildren(key, arr));
            });
        }
        return children;
    }

    private static Tree getChild(String key, Object object) {
        Tree tree = new Tree(key);
        if (object instanceof JSONObject) {
            tree = new Tree(key, "{}");
            JSONObject jsonObject = (JSONObject)object;
            tree.setChildren(getChildren(key, object));
        } else if (object instanceof JSONArray) {
            tree = new Tree(key, "[]");
            JSONArray array = (JSONArray) object;
            tree.setChildren(getChildren(key, array));
        } else {
            tree.setValue(object);
        }
        return tree;
    }
}
