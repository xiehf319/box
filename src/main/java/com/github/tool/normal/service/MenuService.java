package com.github.tool.normal.service;

import com.github.tool.normal.model.Menu;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    public List<Menu> getMenuList() {
        Menu menu = new Menu(1, "工具");
        Menu childMenu1 = new Menu(11, "JSON格式化", "/view/tool/JsonFormatTool.fxml");
        Menu childMenu2 = new Menu(11, "MD5", "/view/tool/Md5.fxml");
        List<Menu> children = new ArrayList<>();
        children.add(childMenu1);
        children.add(childMenu2);
        menu.setChildren(children);
        List<Menu> menus = new ArrayList<>();
        menus.add(menu);
        return menus;
    }
}
