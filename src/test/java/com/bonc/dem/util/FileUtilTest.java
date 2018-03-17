package com.bonc.dem.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FileUtilTest {

    //@Test
    public void isFileExists() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:templates/testexcel.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(FileUtil.isFileExists(file));
    }

    //@Test
    public void isAttachmentEmpty() {
        Assert.assertTrue(!FileUtil.isAttachmentEmpty());
    }

    //@Test
    public void getAttachDir() {
        System.out.println(FileUtil.getAttachDir().getPath());
    }

    //@Test
    public void deleteAllAttachMent() {
        FileUtil.deleteAllAttachMent();
    }

    //@Test
    public void getResourceDir() {
        //System.out.println(FileUtil.getResourceDir("attachment/"));
        String str = String.format("%s\\%s", FileUtil.getResourceDir("attachment"), "avc.ss");
        System.out.println(str);
    }

    @Test
    public void isAttachmentExist() {
        Assert.assertTrue(FileUtil.isAttachmentExist("1订单情况"));
    }
}