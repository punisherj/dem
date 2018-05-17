package com.bonc.dem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "mail")
@Component
public class MailConfig {

    private String from;
    private String[] to;
    private String subject;
}
