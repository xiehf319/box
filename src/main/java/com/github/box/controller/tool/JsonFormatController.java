package com.github.box.controller.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.box.controller.BaseController;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import org.springframework.util.StringUtils;

import javax.sound.midi.MidiChannel;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class JsonFormatController extends BaseController implements Initializable {

    @FXML
    private Pane jsonFormat;

    @FXML
    private TextArea rawJson;

    @FXML
    private Button preview;

    @FXML
    private Button format;

    @FXML
    private Label errMsg;

    @FXML
    private WebView result;

    @FXML
    private AnchorPane leftPane;

    @FXML
    private AnchorPane middlePane;

    @FXML
    private AnchorPane rightPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            adaptWidth();
            adaptHeight();
        });

        jsonFormat.widthProperty().addListener(new WeakChangeListener<>((observable, oldValue, newValue) -> {
            adaptWidth();
        }));
        jsonFormat.heightProperty().addListener(new WeakChangeListener<>((observable, oldValue, newValue) -> {
            adaptHeight();
        }));
    }

    @FXML
    private void doFormat(ActionEvent event) {
        errMsg.setText("");
        String s = rawJson.textProperty().get();
        if (StringUtils.isEmpty(s)) {
            errMsg.setText("json 不能为空");
            return;
        }
        String result = validJson(s);
        if (result != null) {
            rawJson.textProperty().set(result);
        }
    }

    private String validJson(String s) {
        JSONValidator validator = JSONValidator.from(s);
        try {
            boolean valid = validator.validate();
            if (!valid) {
                errMsg.setText("json格式错误");
                return null;
            }
            JSONObject jsonObject = JSONObject.parseObject(s);
            String result = JSON.toJSONString(jsonObject);
        } catch (Exception e) {
            errMsg.setText("json格式发生错误");
        }
        return null;
    }

    @FXML
    private void doPreview(ActionEvent event) {
        String s = rawJson.textProperty().get();
        if (StringUtils.isEmpty(s)) {
            errMsg.setText("json 不能为空");
            return;
        }
        String result = validJson(s);
        if (result != null) {
            JSONObject jsonObject = JSONObject.parseObject(result);
        }
    }

    private void adaptWidth() {
        leftPane.setPrefWidth(jsonFormat.getWidth() * 0.5);
        middlePane.setPrefWidth(jsonFormat.getWidth() * 0.1);
        rightPane.setPrefWidth(jsonFormat.getWidth() * 0.35);

        AnchorPane.setLeftAnchor(middlePane, leftPane.getPrefWidth());
        AnchorPane.setLeftAnchor(rightPane, leftPane.getPrefWidth() + middlePane.getPrefWidth());
    }

    private void adaptHeight() {
        leftPane.setPrefHeight(jsonFormat.getPrefHeight());
        middlePane.setPrefHeight(jsonFormat.getPrefHeight());
        rightPane.setPrefHeight(jsonFormat.getPrefHeight());
    }
}
