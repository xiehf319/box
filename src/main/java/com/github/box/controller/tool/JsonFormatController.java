package com.github.box.controller.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class JsonFormatController implements Initializable {
//    @FXML
//    private TextArea rawJson;
//
//    @FXML
//    private Button format;
//
//    @FXML
//    private Button beauty;
//
//    @FXML
//    private Label errMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adapt();
    }

//    @FXML
//    private void beauty(ActionEvent event) {
//        errMsg.setText("");
//        String s = rawJson.textProperty().get();
//        if (StringUtils.isEmpty(s)) {
//            errMsg.setText("json 不能为空");
//            return;
//        }
//        String result = validJson(s);
//        if (result != null) {
//            rawJson.textProperty().set(result);
//        }
//    }

//    private String validJson(String s) {
//        JSONValidator validator = JSONValidator.from(s);
//        try {
//            boolean valid = validator.validate();
//            if (!valid) {
//                errMsg.setText("json格式错误");
//                return null;
//            }
//            JSONObject jsonObject = JSONObject.parseObject(s);
//            String result = JSON.toJSONString(jsonObject, SerializeConfig.globalInstance, SerializerFeature.PrettyFormat);
//        } catch (Exception e) {
//            errMsg.setText("json格式发生错误");
//        }
//        return null;
//    }
//
//    @FXML
//    private void format(ActionEvent event) {
//        String s = rawJson.textProperty().get();
//        if (StringUtils.isEmpty(s)) {
//            errMsg.setText("json 不能为空");
//            return;
//        }
//        String result = validJson(s);
//        if (result != null) {
//            JSONObject jsonObject = JSONObject.parseObject(result);
//        }
//    }
//
    private void adapt() {

    }
}
