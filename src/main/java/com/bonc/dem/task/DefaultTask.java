package com.bonc.dem.task;

import com.bonc.dem.config.MailConfig;
import com.bonc.dem.service.AsyncTaskService;
import com.bonc.dem.service.ExcelService;
import com.bonc.dem.service.OrderCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class DefaultTask {

    @Autowired
    private AsyncTaskService asyncTaskService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private OrderCollectService orderCollectService;

    @Scheduled(cron = "${cron}")
    public void task() {
        String date = "2017-7-11";
        orderCollectService.getExcelData(date,2);
        excelService.makeExcel(date);
        for (String toMail : mailConfig.getToMail()) {
            asyncTaskService.executeAsyncTask(toMail);
        }
    }
}
