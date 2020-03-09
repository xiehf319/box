package com.github.box.controller;

import com.github.box.BoxApplication;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/3/9 10:23
 */
public abstract class BaseController {

    public void setTitle(String name) {
        BoxApplication.getStage().setTitle(name);
    }
}
