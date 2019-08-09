package com.wjl.commom.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound=true,value="classpath:common-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix = "system.asset")
public class AssetConfiguration {

    private static Export export = null;

    public static class Export {
        private String dir;
        private String fileName;

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }

    public Export getExport() {
        if (export == null) {
            export = new Export();
        }

        return export;
    }

    public String getExportFileFullPath(String uniqueParam) {
        return String.format("%s%s", getExport().getDir(), String.format(getExport().getFileName(), uniqueParam));
    }

}
