package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
/**
 * @author ZHAOJP
 */
@Data
public class RiskRulesHit {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 授信id
     */
    private Long creditId;
    /**
     * 项目标识
     */
    private String identification;
    /**
     * 策略指标代码
     */
    private String indexCode;
    /**
     * 击中指标的值
     */
    private String value;

}
