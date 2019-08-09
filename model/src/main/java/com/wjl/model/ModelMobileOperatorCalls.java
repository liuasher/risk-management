package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 运营商通话明细
 * @date
 * @author LINJX
 */
@Data
public class ModelMobileOperatorCalls {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 项目标识
     */
    private String identification;

    /**
     * 数据获取时间
     */
    private Date updateTime;

    /**
     * 本机号码
     */
    private String cellPhone;

    /**
     * 通话地点
     */
    private String place;

    /**
     * 对方号码
     */
    private String otherCellPhone;

    /**
     * 本次通话话费（元）
     */
    private Double subtotal;

    /**
     * 通话时间
     */
    private Date startTime;

    /**
     * 呼叫类型
     */
    private String initType;

    /**
     * 通话类型
     */
    private String callType;

    /**
     * 通话时长（秒）
     */
    private Integer useTime;

    /**
     * 认证次数
     */
    private Integer verifyCount;

    /**
     * 申请时间
     */
    private Date queryTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *
     */
    private Integer calloutTime;

    /**
     *
     */
    private Integer callinTime;

    /**
     *
     */
    private Integer rank;

    /**
     *
     */
    private String month;

    /**
     *
     */
    private String hour;

    /**
     *
     */
    private String period;

    /**
     *
     */
    private Integer cnt;
}