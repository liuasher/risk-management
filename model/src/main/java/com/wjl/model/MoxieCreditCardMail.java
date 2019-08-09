package com.wjl.model;

import lombok.Data;

/**
 * @author LINJX
 */

@Data
public class MoxieCreditCardMail {
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
     * 邮箱的唯一标识符
     */
    private String emailId;
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
     * 魔蝎网银报告信息
     */
    private String message;
    /**
     * 更新时间
     */
    private Long updateTime;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 向魔蝎查询时间
     */
    private Long queryTime;

    /**
     * 是否成功导入Mysql 1:失败 2:成功
     */
    private Integer mysqlStatus;
    /**
     * 项目标识码
     */
    private String identification;
}
