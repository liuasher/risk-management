package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 魔蝎支付宝请求信息
 * @author mayue
 * @date 2018/4/3
 */
@Data
public class MoxieAliPay {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 报告id
     */
    private String reportId;
    /**
     * 状态 0、初始 ，1、已提交， 2、已查询 ，3、查询失败或提交失败
     */
    private Integer status;
    /**
     * 类型：1.登陆授权 2.bill采集 3.report采集
     */
    private Integer type;
    /**
     * //魔蝎taskId
     */
    private String taskId;
    /**
     * 魔蝎支付宝信息
     */
    private String message;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 向魔蝎查询时间
     */
    private Date queryTime;
    /**
     * 是否导入mysql状态 1：失败 2：成功
     */
    private Integer mysqlStatus;
    /**
     * 项目标识
     */
    private String identification;
}
