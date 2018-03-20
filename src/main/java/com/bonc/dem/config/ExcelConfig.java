package com.bonc.dem.config;

import com.bonc.dem.util.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@PropertySource("classpath:config/excel.properties")
@Component
public class ExcelConfig {

    @Value("${templates.name}")
    private String templatesName;

    @Value("${attachment.name}")
    private String attachmentName;

    @Value("${attachment.path}")
    private String attachmentPath;

    public String getTemplatesName() {
        return templatesName;
    }

    public void setTemplatesName(String templatesName) {
        this.templatesName = templatesName;
    }

    public String getAttachmentName(Date date) {
        //return attachmentName;
        return String.format(attachmentName, DateUtils.parseDateToStr(date, DateUtils.DATE_FORMAT_YYMMDD));
    }
    public String getAttachmentName() {
        return attachmentName.replace("%s.xlsx","");
    }

    public void setAttachmentName(String attachment) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentPath() {
        if (attachmentPath.length() != attachmentPath.lastIndexOf('/')) {
            attachmentPath += "/";
        }
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
}
