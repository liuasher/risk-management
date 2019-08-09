package com.wjl.commom.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Data
@Configuration
@PropertySource(ignoreResourceNotFound=true,value="classpath:common-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix = "system.moxie")
public class MoxieConfiguration {
    private String yysUrl;
    private String token;
    private String taobaoBillUrl;
    private String taobaoReportUrl;
    private String creditCardBillUrl;
    private String creditCardReportUrl;
    //网银
    private String ebankBillUrl;
    private String ebankReportUrl;
    private String payBillUrl;
    private String payReportUrl;



}
