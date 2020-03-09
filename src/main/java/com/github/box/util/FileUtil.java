package com.github.box.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/3/9 10:04
 */
public class FileUtil {

    public static String readJson(File file) {
        StringBuffer stringBuffer = new StringBuffer(1024);
        try {
            FileReader reader = new FileReader(file);
            char[] buffer = new char[1024];
            while(reader.read(buffer) > 0) {
                stringBuffer.append(buffer);
            }
            return stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readJson(String fileName) {
        return readJson(new File(fileName));
    }
}
