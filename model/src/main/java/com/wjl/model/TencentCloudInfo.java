package com.wjl.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 腾讯云mysql实体类
 * @author LINJX
 */
@Data
public class TencentCloudInfo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 银行卡号
     */
    private String bankCardNumber;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 标识符
     */
    private String identification;

    /**
     * 认证id
     */
    private String verifyId;
}
