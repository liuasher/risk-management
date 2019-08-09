package com.wjl.model;

import lombok.Data;

import java.util.Date;

/**
 * 魔蝎淘宝报告关键字段保存至mysql
 * Created by LINJX on 2018/3/20.
 */
@Data
public class ModelTaobaoReport {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 淘宝中该用户的真实姓名
     */
    private String taobaoName;

    /**
     * 淘宝中该用户绑定的手机号码
     */
    private String taobaoPhoneNumber;

    /**
     * 当前该用户花呗的授信额度
     */
    private String huaiBeiLimit;

    /**
     * 当前该用户花呗的可用授信额度
     */
    private String huaiBeiCanUseLimit;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 申请时间
     */
    private Date queryTime;

    /**
     * 项目标识
     */
    private String identification;

    /**
     * 认证次数
     */
    private Integer verifyCount;
}
