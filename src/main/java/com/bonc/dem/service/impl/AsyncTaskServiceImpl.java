package com.bonc.dem.service.impl;

import com.bonc.dem.config.MailConfig;
import com.bonc.dem.service.AsyncTaskService;
import com.bonc.dem.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private MailConfig mailConfig;

    @Async
    public void executeAsyncTask(String toMail) {
        mailService.sendAttachmentsMail(toMail, mailConfig.getSubject() + DateUtils.parseDateToStr(new Date(), DateUtils.DATE_FORMAT_YYYY_MM_DD));
    }
}
