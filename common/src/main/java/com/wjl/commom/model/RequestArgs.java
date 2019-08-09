package com.wjl.commom.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestArgs {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 用户手机号码
     */
    private String mobile;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * ip地址
     */
    private String ip;

    /**
     *项目标识码
     */
    private String identification;

    /**
     * 姓名
     */
    private String name;

    /**
     * icloud账户名
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
     * 任务id
     */
    private String taskId;

    /**
     * 认证id
     */
    private String verifyId;
    /**
     * 魔蝎BillId
     */
    private String billId;
    /**
     * 魔蝎ReportId
     */
    private String reportId;
}
