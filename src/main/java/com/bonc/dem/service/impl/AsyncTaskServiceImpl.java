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
    public void executeAsyncTask(String toMail) {
        mailService.sendAttachmentsMail(toMail, mailConfig.getSubject(), mailConfig.getContent());
    }

    ///**
    // * 异常调用返回Future
    // *
    // * @param i
    // * @return
    // * @throws InterruptedException
    // */
    //@Async
    //public Future<String> asyncInvokeReturnFuture(int i) throws InterruptedException {
    //    System.out.println("input is " + i);
    //    Thread.sleep(1000 * new Random().nextInt(i));
    //
    //    Future<String> future = new AsyncResult<String>("success:" + i);// Future接收返回值，这里是String类型，可以指明其他类型
    //
    //    return future;
    //}
}
