package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hqh
 */
@Data
public class DataModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 授信id
     */
    private Long creditId;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 申请前登录次数
     */
    private Integer ypCnt;

    /**
     * 互通号码比例
     */
    private Float eatchCallRate;

    /**
     * 手机使用时长
     */
    private Integer phoneUseTime;

    /**
     * 被叫次数极差
     */
    private Integer callInCntRsd;


    /**
     * 通讯录数量
     */
    private Integer contactNum;

    /**
     * 手机静默时长基数
     */
    private Integer dmTelNouseBasedays;


    /**
     * 被叫时长标准差
     */
    private Double callInTimeStd;


    /**
     * 手机号归属地与居住地匹配程度;0:省不匹配，1：省匹配市不匹配，2：省市都匹配，null：有一个值为空
     */
    private Integer phone2houseplace;
    /**
     * 评分卡最终得分
     */
    private  Double score;
    /**
     * 项目标识码
     */
    private String identification;

}