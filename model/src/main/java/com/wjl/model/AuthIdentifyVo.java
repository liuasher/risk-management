package com.wjl.model;

import lombok.Data;

/**
 * @Description :实名认证返回类
 * @author : 赵聪
 * @date : 2018/2/28 13:53
 */
@Data
public class AuthIdentifyVo {
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 身份证
     */
    private String idcard;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 查询时间
     */
    private Long queryTime;
    /**
     * 返回码
     */
    private Integer code;


}
