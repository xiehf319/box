package com.github.box.controller;

import com.github.box.BoxApplication;
import com.github.box.model.MenuTree;
import com.github.box.service.MenuService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FXMLController
public class HomeController extends BaseController implements Initializable {

    @Autowired
    private MenuService menuService;

    @FXML
    private Pane homePane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Pane leftPane;

    @FXML
    private Pane rightPane;

    @FXML
    private TreeView<MenuTree> menuTreeView;

    @FXML
    private Pane mainContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.initSize();
        this.initMenu();
    }

    public void initMenu() {
        MenuTree menu = menuService.getMenuTree();
        if (menu == null) {
            setTitle("");
            return;
        }
        // 初始化
        route(menu);

        TreeItem<MenuTree> node = createNode(menu);
        menuTreeView.setRoot(node);
        node.setExpanded(true);

        // 自定义显示的值
        menuTreeView.setCellFactory(new Callback<TreeView<MenuTree>, TreeCell<MenuTree>>() {

            @Override
            public TreeCell<MenuTree> call(TreeView<MenuTree> p) {
                return new TreeCell<MenuTree>() {
                    @Override
                    protected void updateItem(MenuTree item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                            setCache(true);
                        }
                    }

                };
            }
        });

        // add listener to trigger refresh right information
        menuTreeView.getSelectionModel().selectedItemProperty().addListener(new WeakChangeListener<>((observable, oldValue, newValue) -> {
            if (newValue != null) {
                MenuTree value = newValue.getValue();
                Platform.runLater(() -> route(value));
            }
        }));
    }


    private TreeItem<MenuTree> createNode(MenuTree menu) {
        TreeItem<MenuTree> treeItem = new TreeItem<MenuTree>(menu) {
            private boolean isLeaf;
            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override
            public ObservableList<TreeItem<MenuTree>> getChildren() {
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
                    MenuTree m = getValue();
                    isLeaf = CollectionUtils.isEmpty(m.getChildren());
                }
                return isLeaf;
            }

            private ObservableList<TreeItem<MenuTree>> buildChildren(TreeItem<MenuTree> treeItem) {
                MenuTree parent = treeItem.getValue();
                if (parent != null && !CollectionUtils.isEmpty(parent.getChildren())) {
                    List<MenuTree> children = parent.getChildren();
                    if (children != null) {
                        ObservableList<TreeItem<MenuTree>> childItems = FXCollections.observableArrayList();
                        for (MenuTree child : children) {
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

    private void initSize() {
        // 定义窗口大小
        Screen primary = Screen.getPrimary();
        Rectangle2D bounds = primary.getBounds();
        Stage stage = BoxApplication.getStage();
        stage.setResizable(true);
        double width = bounds.getWidth();
        stage.setWidth(width * 0.8);
        double height = bounds.getHeight();
        stage.setHeight(height * 0.8);
        stage.setX(bounds.getMinX() + width * 0.1);
        stage.setY(bounds.getMinY() + height * 0.1);
        stage.setMinWidth(900);
        stage.setMinHeight(720);
        stage.setMaxWidth(width);
        stage.setMaxHeight(height);

        rightPane.setMinSize(stage.getMinWidth() - 160, stage.getMinHeight() - 30);
        rightPane.setPrefWidth(stage.getWidth() - 160);
        rightPane.setMaxWidth(stage.getWidth() - 160);
        rightPane.setPrefHeight(stage.getHeight() - 30);

        mainContent.prefWidthProperty().bind(rightPane.widthProperty());
        mainContent.prefHeightProperty().bind(rightPane.heightProperty());

        leftPane.setMinSize(160, stage.getMinHeight() - 30);
        leftPane.setPrefHeight(stage.getMinHeight() - 30);
        leftPane.setPrefWidth(160);

        menuBar.setPrefHeight(30);
        menuBar.prefWidthProperty().bind(stage.widthProperty());

        menuTreeView.prefWidthProperty().bind(leftPane.widthProperty());
        menuTreeView.prefHeightProperty().bind(leftPane.heightProperty());

    }

    /**
     * 路由到对应页面
     *
     * @param menu
     */
    private void route(MenuTree menu) {
        if (menu == null) {
            return;
        }
        // 标题命名
        setTitle(menu.getDesc());
        if (StringUtils.isEmpty(menu.getPath())) {
            return;
        }
        // 清除原来的子节点
        mainContent.getChildren().clear();
        URL url = getClass().getResource(menu.getPath());
        FXMLLoader loader = new FXMLLoader(url);
        try {
            loader.setRoot(mainContent);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
