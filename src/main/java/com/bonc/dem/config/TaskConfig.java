package com.bonc.dem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "task")
@Component
public class TaskConfig {
    private String cron;
}
