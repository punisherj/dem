package com.bonc.dem.task;

import com.bonc.dem.config.ExcelConfig;
import com.bonc.dem.config.MailConfig;
import com.bonc.dem.service.AsyncTaskService;
import com.bonc.dem.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class DefaultTask {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AsyncTaskService asyncTaskService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private MailConfig mailConfig;
    @Autowired
    private ExcelConfig excelConfig;

    @Scheduled(cron = "${cron}")
    public void task() {
        excelService.record();
        for (String toMail : mailConfig.getToMail()) {
            asyncTaskService.executeAsyncTask(toMail);
        }
    }
}
