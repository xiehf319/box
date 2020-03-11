package com.github.box.model;

import javafx.scene.Node;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/3/11 16:07
 */
@Data
public class JsonTree{

    private Object value;

    private Object key;

    private Node graphicNode;

    private List<JsonTree> children = new ArrayList<>();

    public JsonTree() {
    }

    public JsonTree(Object key, Object value, Node graphicNode) {
        this.key = key;
        this.value = value;
        this.graphicNode = graphicNode;
    }

}
