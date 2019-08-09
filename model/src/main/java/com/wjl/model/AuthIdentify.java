package com.wjl.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ZHAOJP
 */
@Data
public class AuthIdentify {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 项目标识
     */
    private String identification;
    /**
     * 返回码(0:认证成功（收费） 1:认证信息不一致（收费） 2:认证信息不存在 9:其他异常 111：请求失败)
     */
    private Integer code;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 更新时间
     */
    private Long updateTime;

}