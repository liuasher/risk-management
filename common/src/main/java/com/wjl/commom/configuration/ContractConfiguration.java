package com.wjl.commom.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound=true,value="classpath:common-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix = "system.contract")
public class ContractConfiguration {
    private static Template template = null;
    private static Export export = null;

    public static class Template {
        private String dir;
        private String contractFileName;
        private String serviceFileName;

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getContractFileName() {
            return contractFileName;
        }

        public void setContractFileName(String contractFileName) {
            this.contractFileName = contractFileName;
        }

        public String getServiceFileName() {
            return serviceFileName;
        }

        public void setServiceFileName(String serviceFileName) {
            this.serviceFileName = serviceFileName;
        }
    }

    public static class Export {
        private String dir;
        private String contractFileName;
        private String serviceFileName;

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getContractFileName() {
            return contractFileName;
        }

        public void setContractFileName(String contractFileName) {
            this.contractFileName = contractFileName;
        }

        public String getServiceFileName() {
            return serviceFileName;
        }

        public void setServiceFileName(String serviceFileName) {
            this.serviceFileName = serviceFileName;
        }
    }

    public Template getTemplate() {
        if (template == null) {
            template = new Template();
        }

        return template;
    }

    public Export getExport() {
        if (export == null) {
            export = new Export();
        }

        return export;
    }

    public String getTemplateContractFileFullPath() {
        return String.format("%s%s", getTemplate().getDir(), getTemplate().getContractFileName());
    }

    public String getTemplateServiceFileFullPath() {
        return String.format("%s%s", getTemplate().getDir(), getTemplate().getServiceFileName());
    }

    public String getExportContractFileFullPath(String uniqueParam) {
        return String.format("%s%s", getExport().getDir(), String.format(getExport().getContractFileName(), uniqueParam));
    }

    public String getExportServiceFileFullPath(String uniqueParam) {
        return String.format("%s%s", getExport().getDir(), String.format(getExport().getServiceFileName(), uniqueParam));
    }
}
