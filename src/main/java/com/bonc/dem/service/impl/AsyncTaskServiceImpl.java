package com.bonc.dem.service.impl;

import com.bonc.dem.config.MailConfig;
import com.bonc.dem.service.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private MailConfig mailConfig;

    @Async
    public void executeAsyncTask(String toMail, String date) {
        mailService.sendAttachmentsMail(toMail, mailConfig.getSubject(), date);
    }
}
