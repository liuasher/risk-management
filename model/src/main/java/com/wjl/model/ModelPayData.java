package com.wjl.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付信息model
 *
 * @author mayue
 * @date 2018/3/20
 */
@Data
public class ModelPayData {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 授信Id
     */
    private Long creditId;
    /**
     * 手机号码
     */
    private String phoneNumber;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 交易单号
     */
    private String tradeNumber;
    /**
     * 表示交易支出或收入
     */
    private String incomeorexpense;
    /**
     * 交易时间
     */
    private Date tradeTime;
    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;
    /**
     * 交易状态
     */
    private String tradeStatus;
    /**
     * 交易对方
     */
    private String counterparty;
    /**
     * 资金状态
     */
    private String capitalStatus;
    /**
     * 项目标识
     */
    private String identification;
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
}
