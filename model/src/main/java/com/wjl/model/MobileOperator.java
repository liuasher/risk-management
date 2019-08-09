package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hqh
 */
@Data
public class MobileOperator {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * Mongo主键
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
     * 魔蝎taskId
     */
    private String taskId;
    /**
     * 报告信息
     */
    private String message;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 向魔蝎查询时间
     */
    private Date queryTime;
    /**
     * 是否成功导入Mysql 1:失败 2:成功 3:魔蝎多余回调（MONGO中未保存相关报告）
     */
    private Integer mysqlStatus;
    /**
     * 项目标识码
     */
    private String identification;
}
