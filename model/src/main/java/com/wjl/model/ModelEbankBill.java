package com.wjl.model;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ModelEbankBill {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 卡号
     */
    private String cardNum;

    /**
     * 交易时间
     */
    private String billId;

    /**
     * 交易时间
     */
    private Date transDate;

    /**
     * 记账时间
     */
    private Date postDate;

    /**
     * 描述
     */
    private String description;

    /**
     * 交易金额
     */
    private BigDecimal amountMoney;

    /**
     * 币种
     */
    private String currencyType;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 消费类型
     */
    private String category;

    /**
     * 交易地点
     */
    private String transAddr;

    /**
     * 交易方式
     */
    private String transMethod;

    /**
     * 交易通道
     */
    private String transChannel;

    /**
     * 对方账号
     */
    private String oppositeCardNo;

    /**
     * 对方银行
     */
    private String oppositeBank;

    /**
     * 备注
     */
    private String remark;

    /**
     * 银行卡类型
     */
    private String cardType;

    /**
     * 认证次数
     */
    private Integer verifyCount;

    /**
     * 查询时间
     */
    private Date queryTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 项目标识
     */
    private String identification;

}