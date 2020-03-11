package com.github.box;

import com.github.box.model.JsonTree;
import com.github.box.util.JSON2TreeUtil;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/3/11 16:45
 */
public class JSON2TreeTest {

    public static void main(String[] args) {

        String json = "{\n" +
                "\t\"assetCode\":\"FC02110187\",\n" +
                "\t\"verifyCode\":\"03710687\",\n" +
                "\t\"busyStatus\":3,\n" +
                "\t\"boxType\":3,\n" +
                "\t\"storeId\":\"476960925283258368\",\n" +
                "\t\"templateCaseId\":\"5e16d4f5c2065b000a9f3e54\",\n" +
                "\t\"uicUserId\":447291738801872896,\n" +
                "\t\"duration\":1,\n" +
                "\t\"retentionNoticeEnum\":\"EXPIRED_TO_0_MINUTES\",\n" +
                "\t\"adminPhone\":\"18930996891\",\n" +
                "\t\"phone\":\"13634173267\",\n" +
                "\t\"boxPrice\":0,\n" +
                "\t\"pickerPhone\":\"13634173267\",\n" +
                "\t\"cmd\":\"NEW_RENTENTION_NOTIFICATION\",\n" +
                "\t\"edType\":\"3\",\n" +
                "\t\"saveInTime\":\"2020-03-03 20:05:49\"\n" +
                "}";

        JsonTree tree = JSON2TreeUtil.converter(json);

        System.out.println(tree);
    }
}
