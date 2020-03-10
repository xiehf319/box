package com.github.box.controller;

import com.github.box.BoxApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

    public double getScreenWidth() {
        Screen primary = Screen.getPrimary();
        Rectangle2D bounds = primary.getBounds();
        return bounds.getWidth();
    }
    public double getStageWidth() {
        return BoxApplication.getStage().getWidth();
    }

    public double getStageHeight() {
        return BoxApplication.getStage().getHeight();
    }

    public double getScreenHeight() {
        Screen primary = Screen.getPrimary();
        Rectangle2D bounds = primary.getBounds();
        return bounds.getHeight();
    }

}
