package com.wjl.commom.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zc
 * Date: 2017-11-23
 * Time: 18:41
 */
@Configuration
@PropertySource(ignoreResourceNotFound=true,value="classpath:common-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix = "system.firm")
public class FirmConfiguration {
    private String wjl;

    public String getWjl() {
        return wjl;
    }

    public void setWjl(String wjl) {
        this.wjl = wjl;
    }
}
