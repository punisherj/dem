package com.bonc.dem.service.impl;

import com.bonc.dem.service.ExcelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelServiceImplTest {

    @Autowired
    ExcelService excelService;
    @Test
    public void record() {
        //excelService.record();
    }
}