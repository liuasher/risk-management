package com.wjl.commom.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Demo class
 *
 * @author mayue
 * @date 2018/3/5
 */
@Data
@Configuration
@PropertySource(ignoreResourceNotFound=true,value="classpath:common-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix = "system.tongdun")
public class TongDunConfiguration {

    /**
     * 合作方标识
     */
    private String partnerCode;
    /**
     * 合作方密钥
     */
    private String partnerKey;
    /**
     * 应用名称 Android
     */
    private String appNameAndroid;
    /**
     * 应用名称
     */
    private String appNameIos;
    /**
     * 提交Url
     */
    private String submitUrl;
    /**
     * 通知Url
     */
    private String notifyUrl;
    /**
     * 查询Url
     */
    private String queryUrl;
}
