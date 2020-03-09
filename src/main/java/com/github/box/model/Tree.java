package com.github.box.model;

import lombok.Data;

import java.util.List;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/3/9 17:16
 */
@Data
public class Tree {

    private String key;

    private Object value;

    private List<Tree> children;

    public Tree(String key) {
        this.key = key;
    }

    public Tree(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
