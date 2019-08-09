package com.wjl.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 苹果手机实体类
 * @author LINJX
 */
@Data
public class Iphone {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * icloud账户名称
     */
    private String icloudName;

    /**
     * icloud账户密码
     */
    private String icloudPassword;

    /**
     * 手机序列号
     */
    private String serialNumber;

    /**
     * 手机imei
     */
    private String imei;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 识别码
     */
    private String identification;

}
