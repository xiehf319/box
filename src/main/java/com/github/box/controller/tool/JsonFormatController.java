package com.github.box.controller.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONValidator;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.box.controller.BaseController;
import com.github.box.model.JsonTree;
import com.github.box.util.JSON2TreeUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.List;
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
    private TreeView<JsonTree> jsonFormatResultView;

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

        // 自定义显示的值
        jsonFormatResultView.setCellFactory(new Callback<TreeView<JsonTree>, TreeCell<JsonTree>>() {

            @Override
            public TreeCell<JsonTree> call(TreeView<JsonTree> p) {
                return new TreeCell<JsonTree>() {
                    @Override
                    protected void updateItem(JsonTree item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null || item.getKey() == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Object key = item.getKey();
                            String k = key == null ? "" : key.toString();
                            Object value = item.getValue();
                            String v = value == null ? "" : value.toString();
                            if (k.length() == 0 && v.length() == 0) {
                                setText("");
                            } else {
                                if (value instanceof String) {
                                    setText(k + ": \"" + v + "\"");
                                } else {
                                    setText(k + ": " + v);
                                }
                            }
                            setGraphic(item.getGraphicNode());
                        }
                    }

                };
            }
        });


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
            Object jsonObject = JSON.parse(s);
            String jsonString = JSON.toJSONString(jsonObject, SerializeConfig.globalInstance, SerializerFeature.PrettyFormat);
            rawJson.textProperty().set(jsonString);
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
            return s;
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
        String jsonString = validJson(s);
        if (jsonString != null) {

            JsonTree tree = JSON2TreeUtil.converter(jsonString);
            TreeItem<JsonTree> root = createNode(tree);
            jsonFormatResultView.setRoot(root);
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

    private TreeItem<JsonTree> createNode(JsonTree tree) {
        TreeItem<JsonTree> treeItem = new TreeItem<JsonTree>(tree) {
            private boolean isLeaf;
            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override
            public ObservableList<TreeItem<JsonTree>> getChildren() {
                if (isFirstTimeChildren) {
                    isFirstTimeChildren = false;
                    super.getChildren().setAll(buildChildren(this));
                }
                return super.getChildren();
            }

            @Override
            public boolean isLeaf() {
                if (isFirstTimeLeaf) {
                    isFirstTimeLeaf = false;
                    JsonTree m = getValue();
                    isLeaf = CollectionUtils.isEmpty(m.getChildren());
                }
                return isLeaf;
            }

            private ObservableList<TreeItem<JsonTree>> buildChildren(TreeItem<JsonTree> treeItem) {
                JsonTree parent = treeItem.getValue();
                if (parent != null && !CollectionUtils.isEmpty(parent.getChildren())) {
                    List<JsonTree> children = parent.getChildren();
                    if (children != null) {
                        ObservableList<TreeItem<JsonTree>> childItems = FXCollections.observableArrayList();
                        for (JsonTree child : children) {
                            childItems.add(createNode(child));
                        }
                        return childItems;
                    }
                }
                return FXCollections.emptyObservableList();
            }
        };
        treeItem.setExpanded(true);
        return treeItem;
    }
}
