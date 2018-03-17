package com.bonc.dem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:config/mail.properties")
@Component
public class MailConfig {

    @Value("${from.addr}")
    private String fromMail;

    @Value("${to.addr}")
    private String[] toMail;

    @Value("${mail.subject}")
    private String subject;

    @Value("${mail.content}")
    private String content;

    @Value("${file.path}")
    private String filePath;

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        fromMail = fromMail;
    }

    public String[] getToMail() {
        return toMail;
    }

    public void setToMail(String[] toMail) {
        this.toMail = toMail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
