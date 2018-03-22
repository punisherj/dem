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
    public void task() throws Exception {
        String date = "2017-07-11";
        Integer count = orderCollectService.getExcelData(date, 2);
        if (null != count && 0 != count.intValue()) {
            excelService.makeExcel(date);
            for (String toMail : mailConfig.getToMail()) {
                asyncTaskService.executeAsyncTask(toMail, date);
            }
        }else{
            throw new Exception("无法捕获该天的数据");
        }
    }
}
