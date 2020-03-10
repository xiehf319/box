package com.github.box.controller.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.box.controller.BaseController;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import org.springframework.util.StringUtils;

import javax.sound.midi.MidiChannel;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class JsonFormatController extends BaseController implements Initializable {
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
        adapt();
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

    @Override
    public void adapt() {
        double stageWidth = getStageWidth();
        double stageHeight = getStageHeight();
        leftPane.setPrefSize((stageWidth - 160) * 0.6, stageHeight - 30);
        leftPane.setMaxSize((stageWidth - 160) * 0.6, stageHeight - 30);
        leftPane.setMinSize((stageWidth - 160) * 0.6, stageHeight - 30);

        middlePane.setPrefSize(80, stageHeight - 30);
        preview.setPrefSize(80, 20);
        rightPane.setPrefSize(stageWidth - 160 - leftPane.getWidth() - middlePane.getWidth(), stageHeight - 30);

        AnchorPane.setBottomAnchor(leftPane, 0D);
        AnchorPane.setTopAnchor(leftPane, 0D);
        AnchorPane.setLeftAnchor(leftPane, 0D);

        AnchorPane.setBottomAnchor(middlePane, 0D);
        AnchorPane.setTopAnchor(middlePane, 0D);
        AnchorPane.setLeftAnchor(middlePane, leftPane.getWidth());
        AnchorPane.setRightAnchor(middlePane, rightPane.getWidth());

        AnchorPane.setBottomAnchor(rightPane, 0D);
        AnchorPane.setTopAnchor(rightPane, 0D);
        AnchorPane.setLeftAnchor(rightPane, leftPane.getWidth() + middlePane.getWidth());
        AnchorPane.setRightAnchor(rightPane, 0D);

        AnchorPane.setTopAnchor(preview, middlePane.getWidth() / 2 + 10);
        AnchorPane.setBottomAnchor(preview, middlePane.getWidth() / 2 + 10);
        AnchorPane.setLeftAnchor(preview, 0D);
        AnchorPane.setRightAnchor(preview, 0D);
    }
}
