package com.github.box.service;

import com.alibaba.fastjson.JSONObject;
import com.github.box.model.MenuTree;
import com.github.box.util.FileUtil;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * 菜单服务
 */
@Service
public class MenuService {

    /**
     * 获取所有菜单
     *
     * @return
     */
    public MenuTree getMenuTree() {
        try {
            URL resource = this.getClass().getResource("/data/menu.json");
            String fileName = resource.getFile();
            String json = FileUtil.readJson(fileName);
            if (json == null) {
                return null;
            }
            MenuTree menu = JSONObject.parseObject(json, MenuTree.class);
            return menu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
