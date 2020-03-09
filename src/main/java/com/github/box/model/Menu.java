package com.github.box.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Menu {

    private Integer id;

    private String name;

    private String path;

    private String type;

    private String desc;

    private List<Menu> children;

    public Menu(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Menu(Integer id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }
}
