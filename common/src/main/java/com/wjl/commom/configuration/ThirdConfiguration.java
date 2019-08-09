package com.wjl.commom.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound=true,value="classpath:common-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix = "third.MoXie")
public class ThirdConfiguration {
    private  String AuthIdentifyServiceUrl;

    public String getAuthIdentifyServiceUrl() {
        return AuthIdentifyServiceUrl;
    }

    public void setAuthIdentifyServiceUrl(String authIdentifyServiceUrl) {
        AuthIdentifyServiceUrl = authIdentifyServiceUrl;
    }
}
