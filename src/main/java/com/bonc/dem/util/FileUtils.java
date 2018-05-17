package com.bonc.dem.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FileUtils {

    public static File getFileByPath(String path) {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:" + path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static boolean isFileExist(String filePath) {

        File file = new File(filePath);
        if(file.exists()) return true;
        return false;
    }
}
