package com.wjl.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付宝报告model
 *
 * @author mayue
 * @date 2018/3/20
 */
@Data
public class ModelPayReport {
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
     * 当前该用户蚂蚁花呗的授信额度
     */
    private BigDecimal huabaiLimit;
    /**
     * 基金
     */
    private BigDecimal fund;
    /**
     * 招财宝理财
     */
    private BigDecimal zhaoCaiBao;
    /**
     * 存金宝理财
     */
    private BigDecimal cunJinBao;
    /**
     * 淘宝理财
     */
    private BigDecimal taobaoFinance;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 余额宝
     */
    private BigDecimal yuebao;
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
