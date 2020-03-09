package com.github.box.controller.tool;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/3/9 10:36
 */
public class WelcomeController implements Initializable {

    @FXML
    private Label cpuLabel;

    @FXML
    private Label memoryLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cpuLabel.setText("100%");
        memoryLabel.setText("500MB");
    }

}
