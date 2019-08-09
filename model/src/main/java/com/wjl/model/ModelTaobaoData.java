package com.wjl.model;

import lombok.Data;

import java.util.Date;

@Data
public class ModelTaobaoData {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 电话号码
     */
    private String phoneNumber;

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

    /**
     * 认证次数
     */
    private Integer verifyCount;
}
