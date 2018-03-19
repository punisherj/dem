package com.bonc.dem.task;

import com.bonc.dem.config.MailConfig;
import com.bonc.dem.service.AsyncTaskService;
import com.bonc.dem.service.ExcelService;
import com.bonc.dem.service.OrderCollectService;
import com.bonc.dem.util.DateUtils;
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
        excelService.record(orderCollectService.getExcelData(DateUtils.parseStrToDate("20170711", DateUtils.DATE_FORMAT_YYYYMMDD),2));
        for (String toMail : mailConfig.getToMail()) {
            asyncTaskService.executeAsyncTask(toMail);
        }
    }
}
