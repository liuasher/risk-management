package com.wjl.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ZHAOJP
 */
@Data
public class ApiToken {

    /**
     * 主键id
     */
    private Long id;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 接口类型(1:魔蝎运营商 2:魔蝎淘宝 3:魔蝎支付宝 4:魔蝎网银 5:魔蝎信用卡邮箱  6:同盾  7:腾讯云)
     */
    private Integer type;

    /**
     * 项目标识
     */
    private String token;

    /**
     * 接口地址
     */
    private String api;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;



}