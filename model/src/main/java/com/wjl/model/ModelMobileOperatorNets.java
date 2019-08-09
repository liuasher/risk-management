package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 运营商流量明细
 * Created by LINJX on 2018/3/27.
 */
@Data
public class ModelMobileOperatorNets {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 项目标识
     */
    private String identification;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 数据获取时间
     */
    private Date updateTime;

    /**
     * 本机号码
     */
    private String cellPhone;

    /**
     * 使用地点
     */
    private String place;

    /**
     * 本次流量话费（元）
     */
    private Double subtotal;

    /**
     * 使用时间
     */
    private Date startTime;

    /**
     * 网络详情（例如3G网络）
     */
    private String netType;

    /**
     *
     */
    private Integer subflow;

    /**
     *
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


}