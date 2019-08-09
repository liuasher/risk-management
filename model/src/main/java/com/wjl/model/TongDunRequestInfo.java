package com.wjl.model;

import lombok.Data;

import java.util.Date;

/**
 * 同盾请求数据表
 *
 * @author mayue
 * @date 2018/3/7
 */
@Data
public class TongDunRequestInfo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 认证ID
     */
    private String verifyId;
    /**
     * 同盾black_box
     */
    private String blackBox;
    /**
     * 客户申请id地址
     */
    private String ip;
    /**
     * 状态：0：初始；1：已提交；2：已查询； 3:提交或查询失败
     */
    private Integer submitStatus;
    /**
     * 同盾reportId;submit成功后返回
     */
    private String reportId;
    /**
     * 同盾提交时间
     */
    private Date submitTime;
    /**
     * 同盾查询时间
     */
    private Date queryTime;
    /**
     * 是否调用成功,success:调用失败；false:调用成功
     */
    private String success;
    /**
     * 调用失败时的错误码
     */
    private String reasonCode;
    /**
     * 错误详情描述
     */
    private String reasonDesc;
    /**
     * 项目标识码
     */
    private String identification;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
