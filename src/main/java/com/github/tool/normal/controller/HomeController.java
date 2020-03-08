package com.github.tool.normal.controller;

import com.github.tool.normal.NormalApplication;
import com.github.tool.normal.model.Menu;
import com.github.tool.normal.service.MenuService;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class HomeController implements Initializable {

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
    private TreeView<Menu> menuTreeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NormalApplication.getStage().setTitle("主页");
        this.adapt();
        this.initMenu();
    }

    public void initMenu() {
        Menu root = new Menu(0, "ALL");
        List<Menu> menuList = menuService.getMenuList();
        root.setChildren(menuList);
        TreeItem<Menu> node = createNode(root);
        node.setExpanded(true);

        // 自定义显示的值
        menuTreeView.setCellFactory(new Callback<TreeView<Menu>, TreeCell<Menu>>() {

            @Override
            public TreeCell<Menu> call(TreeView<Menu> p) {
                return new TreeCell<Menu>() {
                    @Override
                    protected void updateItem(Menu item, boolean empty) {
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
        menuTreeView.setRoot(node);

        // add listener to trigger refresh right information
        menuTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Menu value = newValue.getValue();
                route(value.getName(), value.getUrl());
            }
        });
    }


    private TreeItem<Menu> createNode(Menu menu) {
        return new TreeItem<Menu>(menu) {
            private boolean isLeaf;
            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override
            public ObservableList<TreeItem<Menu>> getChildren() {
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
                    Menu m = getValue();
                    isLeaf = CollectionUtils.isEmpty(m.getChildren());
                }
                return isLeaf;
            }

            private ObservableList<TreeItem<Menu>> buildChildren(TreeItem<Menu> treeItem) {
                Menu parent = treeItem.getValue();
                if (parent != null && !CollectionUtils.isEmpty(parent.getChildren())) {
                    List<Menu> children = parent.getChildren();
                    if (children != null) {
                        ObservableList<TreeItem<Menu>> childItems = FXCollections.observableArrayList();
                        for (Menu child : children) {
                            childItems.add(createNode(child));
                        }
                        return childItems;
                    }
                }
                return FXCollections.emptyObservableList();
            }
        };
    }

    public void adapt() {
        menuBar.prefWidthProperty().bind(homePane.widthProperty());
        leftPane.prefHeightProperty().bind(homePane.heightProperty());
        rightPane.prefHeightProperty().bind(homePane.heightProperty());
        menuTreeView.prefWidthProperty().bind(leftPane.widthProperty());
        menuTreeView.prefHeightProperty().bind(leftPane.prefHeightProperty());
    }

    /**
     * 路由到对应页面
     *
     * @param name
     * @param path
     */
    private void route(String name, String path) {
        if (path == null) {
            return;
        }
        // 标题命名
        Stage stage = NormalApplication.getStage();
        stage.setTitle(name);

        FilteredList<Node> mainContentList = rightPane.getChildren().filtered(node -> node.getId().equals("mainContent"));
        Node mainContent = mainContentList.get(0);
        URL url = getClass().getResource(path);
        FXMLLoader loader = new FXMLLoader(url);
        try {
            loader.setRoot(mainContent);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
