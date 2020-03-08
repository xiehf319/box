package com.github.tool.normal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Menu {

    private Integer id;

    private String name;

    private String url;

    private List<Menu> children;

    public Menu(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Menu(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
