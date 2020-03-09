package com.github.box.controller;

import com.github.box.model.Menu;
import com.github.box.service.MenuService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    private TreeView<Menu> menuTreeView;

    @FXML
    private Pane mainContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.adapt();
        this.initMenu();
    }

    public void initMenu() {
        Menu menu = menuService.getMenuTree();
        if (menu == null) {
            setTitle("");
            return;
        }
        // 初始化
        route(menu);

        TreeItem<Menu> node = createNode(menu);
        menuTreeView.setRoot(node);
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

        // add listener to trigger refresh right information
        menuTreeView.getSelectionModel().selectedItemProperty().addListener(new WeakChangeListener<>((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Menu value = newValue.getValue();
                Platform.runLater(() -> route(value));
            }
        }));
    }


    private TreeItem<Menu> createNode(Menu menu) {
        TreeItem<Menu> treeItem = new TreeItem<Menu>(menu) {
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
        treeItem.setExpanded(true);
        return treeItem;
    }

    public void adapt() {
        menuBar.prefWidthProperty().bind(homePane.heightProperty());
        leftPane.prefHeightProperty().bind(homePane.widthProperty());
        rightPane.prefHeightProperty().bind(homePane.heightProperty());
        menuTreeView.prefWidthProperty().bind(leftPane.widthProperty());
        menuTreeView.prefHeightProperty().bind(leftPane.heightProperty());

        mainContent.prefHeightProperty().bind(rightPane.heightProperty());
        mainContent.prefWidthProperty().bind(rightPane.widthProperty());
    }

    /**
     * 路由到对应页面
     *
     * @param menu
     */
    private void route(Menu menu) {
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
