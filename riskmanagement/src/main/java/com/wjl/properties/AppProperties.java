package com.wjl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hqh
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "appConfig")
public class AppProperties {
    private String appName;
    private String appNameCn;
    private String qjqServiceSecret;
    private String ruleEngineSecret;
    /**
     * 本服务调用秘钥
     */
    private String callSecret;
    /**
     * 核心服务调用秘钥
     */
    private String secretOfCoreService;
    /**
     * step0公共规则
     */
    private AuditModelType step0ModelType;
    /**
     * step1新客规则1
     */
    private AuditModelType step1ModelType;
    /**
     * step2新客评分卡1
     */
    private AuditModelType step2ModelType;
}
