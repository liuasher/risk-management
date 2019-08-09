package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 信用卡报告
 *
 * @author mayue
 * @date 2018/3/27
 */
@Data
public class ModelEbankCreditCardReport {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 总信用额（元）
     */
    private BigDecimal creditcardLimit;
    /**
     * 总可用信用额（元）
     */
    private BigDecimal totalCanUseConsumeLimit;
    /**
     * 还款笔数
     */
    private Integer repayNum;
    /**
     * 还款率
     */
    private BigDecimal repayRatio;
    /**
     * 逾期标志
     */
    private Integer delayTag;
    /**
     * 逾期状态
     */
    private Integer delayStatus;
    /**
     * 认证时间
     */
    private Date queryTime;
    /**
     * 认证次数
     */
    private Integer verifyCount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 项目标识
     */
    private String identification;
}
