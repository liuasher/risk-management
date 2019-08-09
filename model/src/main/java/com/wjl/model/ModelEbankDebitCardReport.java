package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 借记卡报告model
 *
 * @author mayue
 * @date 2018/3/27
 */
@Data
public class ModelEbankDebitCardReport {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 近1年收入（元）
     */
    private BigDecimal incomeAmty;
    /**
     * 近一年工资收入（元）
     */
    private BigDecimal salaryIncomey;
    /**
     * 近一年贷款收入（元）
     */
    private BigDecimal loanIny;
    /**
     * 近一年支出（元）
     */
    private BigDecimal expensey;
    /**
     * 近一年消费支出（元）
     */
    private BigDecimal consumptionExpensey;
    /**
     * 近一年还贷支出（元）
     */
    private BigDecimal loanOuty;
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
