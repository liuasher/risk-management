package com.wjl.commom.model;

import lombok.Data;

/**
 * 同盾请求体，包含同盾需要的参数
 *
 * @author mayue
 * @date 2018/4/3
 */
@Data
public class TongDunRequestArgs {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 项目标识
     */
    private String identification;
    /**
     * 同盾black_box
     */
    private String blackBox;
    /**
     * IP地址
     */
    private String ipAddress;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 银行卡号
     */
    private String cardNumber;
}
