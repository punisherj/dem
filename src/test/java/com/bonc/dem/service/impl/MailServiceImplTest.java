package com.bonc.dem.service.impl;

import com.bonc.dem.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    MailService ms;

    @Test
    public void sendTemplateMail() {
        //ms.sendTemplateMail("2431803430@qq.com","测试");
    }
}