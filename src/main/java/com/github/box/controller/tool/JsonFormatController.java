package com.github.box.controller.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class JsonFormatController implements Initializable {
    @FXML
    private TextArea rawJson;

    @FXML
    private TreeView<String> result;

    @FXML
    private Button format;

    @FXML
    private Button beauty;

    @FXML
    private Label errMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void beauty(ActionEvent event) {
        errMsg.setText("");
        String s = rawJson.textProperty().get();
        if (StringUtils.isEmpty(s)) {
            errMsg.setText("json 不能为空");
            return;
        }
        JSONValidator validator = JSONValidator.from(s);
        try {
            boolean valid = validator.validate();
            if (!valid) {
                errMsg.setText("json格式错误");
                return;
            }
            JSONObject jsonObject = JSONObject.parseObject(s);
            String result = JSON.toJSONString(jsonObject, SerializeConfig.globalInstance, SerializerFeature.PrettyFormat);
            rawJson.textProperty().setValue(result);
        } catch (Exception e) {
            errMsg.setText("json格式发生错误");
        }

    }

    @FXML
    private void format(ActionEvent event) {

    }
}
