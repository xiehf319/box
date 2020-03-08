package com.github.tool.normal.controller;

import com.github.tool.normal.NormalApplication;
import com.github.tool.normal.view.HomeView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text usernameMsg;

    @FXML
    private Text loginMsg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameField.textProperty().addListener(this::usernameChange);
        usernameField.focusedProperty().addListener(this::usernameFocusChange);
    }

    private void usernameFocusChange(Observable observable, Boolean oldValue, Boolean newValue) {
        // 如果选中数据框，显示提示文字
        if (newValue) {
            usernameMsg.textProperty().setValue("");
        }
        if (oldValue && !newValue) {
            if (StringUtils.isEmpty(usernameField.textProperty().get())) {
                usernameMsg.textProperty().setValue("用户名不能为空");
            }
        }
    }

    private void usernameChange(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    }

    @FXML
    public void login(ActionEvent event) {
        Stage stage = NormalApplication.getStage();
        stage.close();
        NormalApplication.showView(HomeView.class);
    }

    @FXML
    public void reset(ActionEvent event) {
        usernameField.clear();
        passwordField.clear();
    }
}
