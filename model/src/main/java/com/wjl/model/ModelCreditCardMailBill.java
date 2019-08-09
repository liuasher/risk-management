package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author LINJX
 */

@Data
public class ModelCreditCardMailBill {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;


    /**
     * 持卡人
     */
    private String nameOnCard;

    /**
     * 邮箱是用户的邮箱地址，不是发件的邮箱地址
     */
    private String mailSender;

    /**
     * 账单日
     */
    private Date billDate;

    /**
     * 还款日
     */
    private Date paymentDueDate;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 银行编号
     */
    private Integer bankId;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 信用卡额度
     */
    private String creditLimit;

    /**
     * 本期账单金额等于本期应还金额
     */
    private String newBalance;

    /**
     * 上期还款金额
     */
    private String lastPayment;

    /**
     * 可用积分
     */
    private Integer point;

    /**
     * 最近积分失效时间
     */
    private Date pointLoseDate;

    /**
     * 申请时间
     */
    private Date queryTime;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 项目标识符
     */
    private String identification;

    /**
     * 认证次数
     */
    private Integer verifyCount;
}