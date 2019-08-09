package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 运营商汇总信息
 * @author LINJX
 * @date 2018-3-27
 * @version 1.0
 */
@Data
public class ModelMobileOperatorCellBehavior {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 报告编号
     */
    private String rptId;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 项目标识码
     */
    private String identification;

    /**
     * 运营商
     */
    private String cellOperator;
    /**
     * 运营商（中文）
     */
    private String cellOperatorZh;
    /**
     * 号码
     */
    private String cellPhoneNum;
    /**
     * 归属地
     */
    private String cellLoc;
    /**
     * 月份
     */
    private String cellMth;
    /**
     * 呼叫次数
     */
    private Integer callCnt;
    /**
     * 主叫次数
     */
    private Integer callOutCnt;
    /**
     * 主叫时间
     */
    private Float callOutTime;
    /**
     * 被叫次数
     */
    private Integer callInCnt;
    /**
     * 被叫时间
     */
    private Float callInTime;
    /**
     * 流量
     */
    private Float netFlow;
    /**
     * 短信数目
     */
    private Integer smsCnt;
    /**
     * 话费消费
     * 修改为实际费用（即juxinli_raw_members_transactions_transactions中的实际资费）
     */
    private Float totalAmount;

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

    private Float callTime;
}