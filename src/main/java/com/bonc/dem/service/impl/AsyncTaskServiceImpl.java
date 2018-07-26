package com.bonc.dem.service.impl;

import com.bonc.dem.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author xukj
 */
@Service
public class AsyncTaskServiceImpl {

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private MailConfig mailConfig;

    @Async
    public void executeAsyncTask(String toMail, String date) {
        mailService.sendAttachmentsMail(toMail, mailConfig.getSubject(), date);
    }
}
