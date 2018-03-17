package com.bonc.dem.controller;

import com.bonc.dem.config.TaskConfig;
import com.bonc.dem.task.DynamicScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperateController {

    @Autowired
    DynamicScheduledTask dynamicScheduledTask;

    @Autowired
    private TaskConfig taskconfig;

    @RequestMapping("/")
    public String index() {
        return "服务启动";
    }

    @RequestMapping("/corn")
    public String setCorn() {
        dynamicScheduledTask.setCron(taskconfig.getCron());
        return "已经修改定时任务执行时间";
    }
}
