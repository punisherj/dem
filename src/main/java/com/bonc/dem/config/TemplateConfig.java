package com.bonc.dem.config;

import com.bonc.dem.util.FileUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Data
@ConfigurationProperties(prefix = "template")
@Component
public class TemplateConfig {
    private String name;

    public String getPath() {
        return "templates/" + this.getName();
    }

    public File getTemplateFile() {
        return FileUtils.getFileByPath(this.getPath());
    }
}
