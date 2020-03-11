package com.github.box.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MenuTree{

    private Integer id;

    private String name;

    private String path;

    private String type;

    private String desc;

    private List<MenuTree> children = new ArrayList<>();

    public MenuTree(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public MenuTree(Integer id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }
}
