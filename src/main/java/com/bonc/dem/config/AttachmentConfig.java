package com.bonc.dem.config;

import com.bonc.dem.util.DateUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@ConfigurationProperties(prefix = "attachment")
@Component
public class AttachmentConfig {

    private String name;
    private String path;

    public String getName(String date) {
        return name + date.replace("-","").substring(4) + ".xlsx";
    }

    public String getName(Date date) {
        return name + DateUtils.parseDateToStr(date, DateUtils.DATE_FORMAT_YYMMDD) + ".xlsx";
    }

    public String getPath() {
        if (path.length() != path.lastIndexOf('/')) {
            path += "/";
        }
        return path;
    }
}
