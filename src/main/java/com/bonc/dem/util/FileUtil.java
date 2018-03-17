package com.bonc.dem.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FileUtil {

    public static boolean isFileExists(File file) {

        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static boolean isAttachmentEmpty() {
        File file = getAttachDir();
        if (file.list().length > 0) {
            return false;
        }
        return true;
    }

    public static boolean isDirEmpty(String path) {
        File file = getResourceDir(path);
        if (file.list().length > 0) {
            return false;
        }
        return true;
    }

    public static File getAttachDir() {
        return getResourceDir("attachment");
    }

    public static File getResourceDir(String path) {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:" + path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void deleteAllAttachMent() {
        File[] fileArrays = getAttachDir().listFiles();
        for (File attachment : fileArrays) {
            attachment.delete();
        }
    }

    public static boolean isAttachmentExist(String fileName) {
        // 获得指定文件对象
        File file = getAttachDir();
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        for (int i = 0; i < array.length; i++) {
            if(array[i].getName().contains(fileName)){
                return true;
            }
        }
        return false;
    }
}
