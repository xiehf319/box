package com.github.box.service;

import com.alibaba.fastjson.JSONObject;
import com.github.box.model.Menu;
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
    public Menu getMenuTree() {
        try {
            URL resource = this.getClass().getResource("/data/menu.json");
            String fileName = resource.getFile();
            String json = FileUtil.readJson(fileName);
            if (json == null) {
                return null;
            }
            Menu menu = JSONObject.parseObject(json, Menu.class);
            return menu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
