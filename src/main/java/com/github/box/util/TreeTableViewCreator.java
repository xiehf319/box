package com.github.box.util;

import com.alibaba.fastjson.JSONObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/3/9 16:38
 */
public class TreeTableViewCreator {

    public TreeItem<String> getData(JSONObject jsonObject, int columnLength, TreeItem parentItem) {

        return null;
    }

    public Parent createTable(String[] columnNames, JSONObject jsonObject) {

        final TreeTableView<String> treeTableView = new TreeTableView<>(getData(jsonObject, columnNames.length, null));

        TreeTableColumn<JSONObject, String> keyColumn = new TreeTableColumn<>("");
//        keyColumn.setCellValueFactory((
//                        TreeTableColumn.CellDataFeatures<JSONObject, String> param) -> new ReadOnlyObjectWrapper(
//                        param.getValue().getValue().get("")
//                )
//        ));

        return treeTableView;
    }
}
