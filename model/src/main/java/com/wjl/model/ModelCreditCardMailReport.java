package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ModelCreditCardMailReport {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 银行数
     */
    private Integer bankNums;

    /**
     * 活跃卡数
     */
    private Integer activeCards;

    /**
     * 客户族群标志
     */
    private String customerGroupTag;

    /**
     * 总信用额
     */
    private BigDecimal totalCreditLimit;

    /**
     * 单一银行最高授信额度
     */
    private BigDecimal maxTotalCreditLimit;

    /**
     * 分期手续费
     */
    private BigDecimal totalInstallmentFeeAmount;

    /**
     * 近3月月均消费金额
     */
    private BigDecimal averageConsume3m;

    /**
     * 近6月月均消费金额
     */
    private BigDecimal averageConsume6m;

    /**
     * 近12月月均消费金额
     */
    private BigDecimal averageConsume12m;

    /**
     * 近3月月均消费笔数
     */
    private Integer averageSellCount3m;

    /**
     * 近6月月均消费笔数
     */
    private Integer averageSellCount6m;

    /**
     * 近12月月均消费笔数
     */
    private Integer averageSellCount12m;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 申请时间
     */
    private Date queryTime;

    /**
     * 项目标识码
     */
    private String identification;

    /**
     * 认证次数
     */
    private Integer verifyCount;
}
